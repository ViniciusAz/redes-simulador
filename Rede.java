import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Rede {
    public List<Node> nodos;
    public List<Router> roteadores;
    public List<Pacote> pacotes;
    public Node origem, destino;
    public Object atual;
    public String mensagem;

    public Rede(String arquivo, String noOrigem, String noDestino, String mensagem) throws FileNotFoundException {
        Leitura io = new Leitura(arquivo);
        nodos = io.getNodos();
        roteadores = io.getRoteadores();
        origem = getN(noOrigem);
        destino = getN(noDestino);
        atual = getN(noOrigem);
        pacotes = new ArrayList<Pacote>();
        this.mensagem = mensagem;
        online();
    }

    //Met. que manipulam Listas de Roteadores e Nodos (add/get/size/remove/etc)
    public void add(Router roteador) { roteadores.add(roteador); }
    public void add(Node nodo) { nodos.add(nodo); }
    
    public Router getR(int i) { return roteadores.get(i); }
    public Node getN(int i) { return nodos.get(i); }

    public Router getR(String id) {
        for (Router r : roteadores)
            if(r.getId().equals(id)) return r;
        return null;
    }
    public Node getN(String id) {
        for (Node n : nodos)
            if(n.getId().equals(id)) return n;
        return null;
    }

    public void online() {
        // System.out.println("DEBUG @ ");
        // System.out.println("DEBUG @ " + ((Node) atual).getId());
        while(atual instanceof Node && ((Node) atual).getId() != destino.getId()
            || atual instanceof Router && ((Router) atual).getId() != destino.getId()) {
            if(atual instanceof Node) {
                //processo todo
                if(((Node) atual).getRede() == destino.getRede()) {
                    // verifica se o destino ta na ARP
                    // System.out.println("DEBUG @ ta na msm rede");
                    if(((Node) atual).temArp(destino.getIp())) {
                        // System.out.println("DEBUG @ tem arp");
                        ajustaPacote((Node) atual);
                        for (Pacote p : pacotes) { printIcmpEchoRequest(destino, p); }
                        atual = destino;
                        // System.out.println("DEBUG @ atual virou destino");
                    } else /* se nao for mesma rede, faz arp rq/reply */ {
                        // System.out.println("DEBUG @ vai add arp");
                        printArpRequest(destino.printIp());
                        // add entradas na arp
                        destino.add(((Node) atual).getIp(), ((Node) atual).getMac());
                        ((Node) atual).add(((Node) destino).getIp(), destino.getMac());
                        printArpReply(destino);
                        // System.out.println("DEBUG @ tem arp? " + ((Node) atual).temArp(destino.getIp()));
                        
                    }
                } else {
                    // //n ta NA REDE TEM Q IR PRO gateway ROUTER
                    //     if(atual.temArp(atual.getGateway())) {
                    //         icmp > router 
                    //     atual = router
                    // } else /**/ {
                    //         x
                    // }
                    //     ICMP
                    //     ELSE
                    //     ARP RQ > gateway padrao
                }

            } else /*atual é router*/{
                // //processo todo
                // if(((Router) atual).getRede() == destino.getRede()) {
                //     // verifica se o destino ta na ARP
                //     if(((Router) atual).temArp(destino.getIp())) {
                //         ajustaPacote();
                //         for (Pacote p : pacotes) { printIcmpEchoRequest(destino, p); }
                //         ((Router) atual) = destino;
                //     } else /* se nao for mesma rede, faz arp rq/reply */ {
                //         printArpRequest();
                //         destino.add(((Router) atual).getIp(), ((Router) atual).getMac());
                //         ((Router) atual).add(((Router) atual).getIp(), destino.getMac());
                //         printArpReply(destino);
                //     }
                // } else {
                //     // //n ta NA REDE TEM Q IR PRO gateway ROUTER
                //     //     if(atual.temArp(atual.getGateway())) {
                //     //         icmp > router 
                //     //     atual = router
                //     // } else /**/ {
                //     //         x
                //     // }
                //     //     ICMP
                //     //     ELSE
                //     //     ARP RQ > gateway padrao
                // }
            }
        }//while
            
    }

    public void ajustaPacote(Node atual) {
        List<Pacote> novoPct = new ArrayList<Pacote>();
        List<String> novaMsg = new ArrayList<String>();
        int mtu = atual.getMtu();

        if(pacotes.isEmpty()) {
            novaMsg = Arrays.asList(mensagem.split("(?<=\\G.{" + mtu + "})"));
            for (int i = 0; i < novaMsg.size(); i++) {
                if(i == novaMsg.size()-1) novoPct.add(new Pacote(novaMsg.get(i), 8, 0, i*mtu));
                else novoPct.add(new Pacote(novaMsg.get(i), 8, 1, i*mtu));
            }
        } else {
            for (Pacote p : pacotes) {
                novaMsg = Arrays.asList(p.getMensagem().split("(?<=\\G.{" + mtu + "})"));
                for (int i = 0; i < novaMsg.size(); i++) {
                    if(i == novaMsg.size()-1) novoPct.add(new Pacote(novaMsg.get(i), p.getTtl()-1, 0, p.getOffset()+i*mtu));
                    else novoPct.add(new Pacote(novaMsg.get(i), p.getTtl()-1, 0, p.getOffset()+i*mtu));
                }
            }
        }
        pacotes = novoPct;
    }
    
    public void printArpRequest(String ipDestino) {
        // Pacotes ARP Request: <src_name> box <src_name> : ETH (src=<MAC_src> dst =<MAC_dst>) \n ARP - Who has <IP_dst>? Tell <IP_src>;
        if(atual instanceof Node) {
            System.out.println(((Node) atual).getId() + " box " + ((Node) atual).getId() + " : (src=" + ((Node) atual).getMac() + " dst=:FF) \n ARP - Who has " + ipDestino + "? Tell " + ((Node) atual).printIp() + ";");
        }
    }
    public void printArpReply(Object dest) {
        // Pacotes ARP Reply: <src_name> => <dst_name> : ETH (src=<MAC_src> dst =<MAC_dst>) \n ARP - <src_IP> is at <src_MAC>;
        if(dest instanceof Node) {
            System.out.println(((Node) dest).getId() + " => " + ((Node) atual).getId() + " : ETH (src=" + ((Node) dest).getMac()
                    + " dst=" + ((Node) atual).getMac() + ") \n ARP - " + ((Node) dest).printIp() + " is at " + ((Node) dest).getMac());
        }
        // System.out.println(dest.getId() + " => " + origem.getId() + " : ETH (src=" + dest.getMac() + " dst=" + origem.getMac() + ") \n ARP - " + dest.printIp() + " is at " + dest.getMac());
    }

    public void printIcmpEchoRequest(Object dest, Pacote pct) {
        // Pacotes ICMP Echo Request: <src_name> => <dst_name> : ETH (src=<MAC_src> dst =<MAC_dst>) \n IP (src=<IP_src> dst=<IP_dst> ttl=<TTL> mf=<mf_flag> off=<offset>) \n ICMP - Echo request (data=<msg>);
        if(atual instanceof Node) {
            System.out.println(((Node) atual).getId() + " => " + ((Node) dest).getId() + " : ETH (src=" + ((Node) atual).getMac() + " dst=" + ((Node) dest).getMac() + ") \n IP (src=" + ((Node) atual).printIp() + " dst=" + ((Node) dest).printIp() + " ttl=" + pct.getTtl() + " mf=" + pct.getMf() + " off=" + pct.getOffset() + ") \n ICMP - Echo request (data=" + pct.getMensagem() + ");");
        }
        //System.out.println(atual.getId() + " => " + dest.getId() + " : ETH (src=" + atual.getMac() + " dst=" + dest.getMac() + ") \n IP (src=" + atual.printIp() + " dst=" + dest.printIp() + " ttl=" + pct.getTtl() + " mf=" + pct.getMf() + " off=" + pct.getOffset() + ") \n ICMP - Echo request (data=" + pct.getMensagem() + ");");
    }

// Pacotes ICMP Echo Reply: <src_name> => <dst_name> : ETH (src=<MAC_src> dst =<MAC_dst>) \n IP (src=<IP_src> dst=<IP_dst> ttl=<TTL> mf=<mf_flag> off=<offset>) \n ICMP - Echo reply (data=<msg>);
// Pacotes ICMP Time Exceeded: <src_name> => <dst_name> : ETH (src=<MAC_src> dst =<MAC_dst>) \n IP (src=<IP_src> dst=<IP_dst> ttl=<TTL>) \n ICMP - Time Exceeded
// Processamento final do ICMP Echo Request/Reply no nó: <dst_name> rbox <dst_name> : Received <msg>;
}