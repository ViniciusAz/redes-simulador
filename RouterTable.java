import java.util.List;
import java.util.ArrayList;

public class RouterTable{

    private class Entrada {
        private IPv4 rede;
        private IPv4 proxHop;
        private Interface porta;

        public Entrada(IPv4 rede, IPv4 proxHop, Interface porta) {
            this.rede = rede;
            this.proxHop = proxHop;
            this.porta = porta;
        }
        public Interface getPorta() { return porta; }
        public IPv4 getRede() { return rede; }
        public IPv4 getproxHop() { return proxHop; }
    }

    ArrayList<Entrada> lista;

    public RouterTable() {
        lista = new ArrayList<Entrada>();
    }

    public void add(IPv4 rede, IPv4 proxHop, Interface porta) {
        lista.add(new Entrada(rede, proxHop, porta));
    }
    public void add(IPv4 rede, Interface porta) {
        lista.add(new Entrada(rede, null, porta));
    }

    public boolean temEntrada(String rede) {
        boolean x = false;
        for (Entrada e : lista) {
            if(e.getRede().printIpNoCidr().equals(rede)) x = true;
        }
        return x;
    }
    public boolean temSalto(IPv4 rede) {
        boolean x = false;
        for (Entrada e : lista) {
            if(e.getproxHop() != null) x = true;
        }
        return x;
    }

    public Interface getPortaRede(String rede) {
        // Acha o IP e manda para a porta
        for(int i = 0; i < lista.size(); i++)
            if(lista.get(i).getRede().printIpNoCidr().equals(rede))
                return lista.get(i).getPorta();
		//Vai para o QUALQUER -> usando o próximo HOP
		// Cria uma rede com 0.0.0.0 para ser o qualquer
        for(int i = 0; i < lista.size(); i++)
			if(lista.get(i).getRede().printIpNoCidr().equals("0.0.0.0"))
				return lista.get(i).getPorta();	
			
		return null; // Deu algum problema !
    }
    public IPv4 getHop(String rede) {
        // Acha o IP e manda para a porta
        for(int i = 0; i < lista.size(); i++)
            if(lista.get(i).getRede().printIpNoCidr().equals(rede))
                return lista.get(i).getproxHop();
		//Vai para o QUALQUER -> usando o próximo HOP
		// Cria uma rede com 0.0.0.0 para ser o qualquer
        for(int i = 0; i < lista.size(); i++)
			if(lista.get(i).getRede().printIpNoCidr().equals("0.0.0.0"))
				return lista.get(i).getproxHop();	
			
		return null; // Deu algum problema !
    }

    public String toString() {
        String x = "";
        for (Entrada e : lista) {
            System.out.println(e.getRede().printIpNoCidr() + " | " + e.getproxHop().printIpNoCidr() + " | eth" + e.getPorta().getId());
            x += e.getRede().printIpNoCidr() + " | " + e.getproxHop().printIpNoCidr() + " | eth" + e.getPorta().getId();
        }
        return x;
    }
    public int size() {
        return lista.size();
    }
}