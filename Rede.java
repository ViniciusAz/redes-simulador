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
        Router proximoRouter = null;
        Interface portaRouter = null;
        Interface portaRouterAtual = null;
        boolean ida = false, volta = false;
        // while(atual instanceof Node && ((Node) atual).getId() != destino.getId()
        //     || atual instanceof Router && ((Router) atual).getId() != destino.getId()) {
        while(!ida || !volta) {
            // Nodo enviando > 
            if(atual instanceof Node) {
                // Nodo enviando > Nodo
                if(((Node) atual).getRede() == destino.getRede()) {
                    // verifica se o destino ta na ARP, faz ICMP
                    if(((Node) atual).temArp(destino.getIp())) {
                        ajustaPacote(((Node) atual).getMtu());
                        for (Pacote p : pacotes) { printIcmpEchoRequest(destino, p, null, null); }
                        atual = destino;
                    } else /* ARP Req e Reply */ {
                        printArpRequest(destino.printIp(), null);
                        destino.add(((Node) atual).getIp(), ((Node) atual).getMac());
                        printArpReply(destino, null, null);
                        ((Node) atual).add(((Node) destino).getIp(), destino.getMac());
                    }
                } else /* Nodo enviando > Router */ {
                    // Ja deixa a Interface de rede em maos
                    for (Router r : roteadores) {
                        portaRouter = r.getPortaGateway(((Node) atual).getGateway());
                        if(portaRouter != null) {
                            proximoRouter = r;
                            break;
                        }
                    }
                    // verifica se o destino ta na ARP, faz ICMP
                    if(((Node) atual).temArp(portaRouter.getIp())) {
                        ajustaPacote(((Node) atual).getMtu());
                        for (Pacote p : pacotes) { printIcmpEchoRequest(proximoRouter, p, portaRouter, null); }
                        atual = proximoRouter;
                        proximoRouter = null;
                        portaRouter = null;
                    } else /* ARP Req e Reply */ {
                        //tem q buscar o ip do router (da mesma rede)
                        printArpRequest(portaRouter.printIp(), null);
                        // add entradas na arp
                        proximoRouter.add(((Node) atual).getIp(), ((Node) atual).getMac());
                        printArpReply(proximoRouter, portaRouter, null);
                        ((Node) atual).add(portaRouter.getIp(), portaRouter.getMac());
                        
                    }
                }

            } else /*atual é router*/ {
                portaRouterAtual = ((Router) atual).buscaRouterTable(destino.printRede());
                // se destino ta na mesma rede da interface entao é router > nodo
                if(portaRouterAtual.getIp().getRede() == destino.getRede()) {
                    if(((Router) atual).temArp(destino.getIp())) {
                        ajustaPacote(portaRouterAtual.getMtu());
                        for (Pacote p : pacotes) { printIcmpEchoRequest(destino, p, null, portaRouterAtual); }
                        atual = destino;
                        // break;
                    } else /* se nao for mesma rede, faz arp rq/reply */ {
                        printArpRequest(destino.printIp(), portaRouterAtual);
                        // add entradas na arp
                        destino.add(portaRouterAtual.getIp(), portaRouterAtual.getMac());
                        ((Router) atual).add(destino.getIp(), destino.getMac());
                        printArpReply(destino, null, portaRouterAtual);
                    }                    
                } else /* router > router */ {
                    for (Router r : roteadores) {
                        portaRouter = r.getPortaGateway(((Router) atual).buscaProximoHop(destino.printRede()));
                        if(portaRouter != null) {
                            proximoRouter = r;
                            break;
                        }
                    }
                    // verifica se o destino ta na ARP
                    if(((Router) atual).temArp(portaRouter.getIp())) {
                        ajustaPacote(portaRouterAtual.getMtu());
                        for (Pacote p : pacotes) { printIcmpEchoRequest(proximoRouter, p, portaRouter, portaRouterAtual); }
                        atual = proximoRouter;
                        proximoRouter = null;
                        portaRouter = null;
                        portaRouterAtual = null;
                        // if(ida) break;
                    } else /* se nao for mesma rede, faz arp rq/reply usando gateway padrao */ {
                        //tem q buscar o ip do router (da mesma rede)
                        printArpRequest(portaRouter.printIp(), portaRouterAtual);
                        // add entradas na arp
                        proximoRouter.add(portaRouterAtual.getIp(), portaRouterAtual.getMac());
                        ((Router) atual).add(portaRouter.getIp(), portaRouter.getMac());
                        printArpReply(proximoRouter, portaRouter, portaRouterAtual);
                        
                    }
                }
            }
            // if(atual instanceof Node) System.out.println("DEBUG @ Atual = " + ((Node) atual).getId());
            // else System.out.println("DEBUG @ Atual = " + ((Router) atual).getId());

            if(!ida) {
                // System.out.println("DEBUG @ entrou no ida");
                if(atual instanceof Node && ((Node) atual).getId() == destino.getId()) {
                    ida = true;
                    Node trocaAux = origem;
                    origem = destino;
                    destino = trocaAux;
                    printIcmpFinal();
                    // System.out.println("DEBUG @ trocou tudo");
                }
            } else /* voltando */ {
                if(atual instanceof Node && ((Node) atual).getId() == destino.getId()) {
                    // System.out.println("DEBUG @ volta é true");
                    volta = true;
                    printIcmpFinal();
                }
            }
        }//while
        
            
    }

    public void ajustaPacote(int mtu) {
        List<Pacote> novoPct = new ArrayList<Pacote>();
        List<String> novaMsg = new ArrayList<String>();

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
    
    public void printArpRequest(String ipDestino, Interface interf) {
        // Pacotes ARP Request: <src_name> box <src_name> : ETH (src=<MAC_src> dst =<MAC_dst>) \n ARP - Who has <IP_dst>? Tell <IP_src>;
        if(atual instanceof Node) {
            System.out.println(((Node) atual).getId() + " box " + ((Node) atual).getId() + " : (src=" + ((Node) atual).getMac() + " dst=FF:FF:FF:FF:FF:FF) \\n ARP - Who has " + ipDestino + "? Tell " + ((Node) atual).printIp() + ";");
        } else {
            System.out.println(((Router) atual).getId() + " box " + ((Router) atual).getId() + " : (src=" + interf.getMac() + " dst=FF:FF:FF:FF:FF:FF) \\n ARP - Who has " + ipDestino + "? Tell " + interf.printIp() + ";");
        }
    }
    public void printArpReply(Object dest, Interface interf, Interface interfAtual) {
        // Pacotes ARP Reply: <src_name> => <dst_name> : ETH (src=<MAC_src> dst =<MAC_dst>) \n ARP - <src_IP> is at <src_MAC>;
        if(atual instanceof Node && dest instanceof Node) {
            System.out.println(((Node) dest).getId() + " => " + ((Node) atual).getId() + " : ETH (src=" + ((Node) dest).getMac()
                    + " dst=" + ((Node) atual).getMac() + ") \\n ARP - " + ((Node) dest).printIp() + " is at " + ((Node) dest).getMac());
        } else if(atual instanceof Node && dest instanceof Router) {
            System.out.println(((Router) dest).getId() + " => " + ((Node) atual).getId() + " : ETH (src=" + interf.getMac() + " dst=" + ((Node) atual).getMac() + ") \n ARP - " + interf.printIp() + " is at " + interf.getMac());
        } else if(atual instanceof Router && dest instanceof Router) {
            System.out.println(((Router) dest).getId() + " => " + ((Router) atual).getId() + " : ETH (src=" + interf.getMac() + " dst=" + interfAtual.getMac() + ") \n ARP - " + interf.printIp() + " is at " + interf.getMac());
        } else if(atual instanceof Router && dest instanceof Node) {
            System.out.println(((Node) dest).getId() + " => " + ((Router) atual).getId() + " : ETH (src=" + ((Node) dest).getMac() + " dst=" + interfAtual.getMac() + ") \n ARP - " + ((Node) dest).printIp() + " is at " + ((Node) dest).getMac());
        } else {
            System.out.println("ARP REPLY DEU PAU");
        }
    }

    public void printIcmpEchoRequest(Object dest, Pacote pct, Interface interf, Interface interfAtual) {
        // Pacotes ICMP Echo Request: <src_name> => <dst_name> : ETH (src=<MAC_src> dst =<MAC_dst>) \n IP (src=<IP_src> dst=<IP_dst> ttl=<TTL> mf=<mf_flag> off=<offset>) \n ICMP - Echo request (data=<msg>);
        if(atual instanceof Node && dest instanceof Node) {
            System.out.println(((Node) atual).getId() + " => " + ((Node) dest).getId() + " : ETH (src=" + ((Node) atual).getMac() + " dst=" + ((Node) dest).getMac() + ") \\n IP (src=" + ((Node) atual).printIp() + " dst=" + ((Node) dest).printIp() + " ttl=" + pct.getTtl() + " mf=" + pct.getMf() + " off=" + pct.getOffset() + ") \\n ICMP - Echo request (data=" + pct.getMensagem() + ");");
        } else if(atual instanceof Node && dest instanceof Router) {
            System.out.println(((Node) atual).getId() + " => " + ((Router) dest).getId() + " : ETH (src=" + ((Node) atual).getMac() + " dst=" + interf.getMac() + ") \\n IP (src=" + ((Node) atual).printIp() + " dst=" + interf.printIp() + " ttl=" + pct.getTtl() + " mf=" + pct.getMf() + " off=" + pct.getOffset() + ") \\n ICMP - Echo request (data=" + pct.getMensagem() + ");");
        } else if(atual instanceof Router && dest instanceof Router) {
            System.out.println(((Router) atual).getId() + " => " + ((Router) dest).getId() + " : ETH (src=" + interfAtual.getMac() + " dst=" + interf.getMac() + ") \\n IP (src=" + interfAtual.printIp() + " dst=" + interf.printIp() + " ttl=" + pct.getTtl() + " mf=" + pct.getMf() + " off=" + pct.getOffset() + ") \\n ICMP - Echo request (data=" + pct.getMensagem() + ");");
        } else if(atual instanceof Router && dest instanceof Node) {
            System.out.println(((Router) atual).getId() + " => " + ((Node) dest).getId() + " : ETH (src=" + interfAtual.getMac() + " dst=" + ((Node) dest).getMac() + ") \\n IP (src=" + interfAtual.printIp() + " dst=" + ((Node) dest).printIp() + " ttl=" + pct.getTtl() + " mf=" + pct.getMf() + " off=" + pct.getOffset() + ") \\n ICMP - Echo request (data=" + pct.getMensagem() + ");");
        } else {
            System.out.println("ICMP DEU PAU");
        } 
    }
    public void printIcmpFinal() {
        // Processamento final do ICMP Echo Request/Reply no nó: <dst_name> rbox <dst_name> : Received <msg>;
        System.out.println(destino.getId() + " rbox " + destino.getId() + " : Receuved " + mensagem);
    }

// Pacotes ICMP Echo Reply: <src_name> => <dst_name> : ETH (src=<MAC_src> dst =<MAC_dst>) \n IP (src=<IP_src> dst=<IP_dst> ttl=<TTL> mf=<mf_flag> off=<offset>) \n ICMP - Echo reply (data=<msg>);
// Pacotes ICMP Time Exceeded: <src_name> => <dst_name> : ETH (src=<MAC_src> dst =<MAC_dst>) \n IP (src=<IP_src> dst=<IP_dst> ttl=<TTL>) \n ICMP - Time Exceeded
}