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

    public boolean temEntrada(IPv4 rede) {
        boolean x = false;
        for (Entrada e : lista) {
            if(e.getRede() == rede) x = true;
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

    public Interface getPortaRede(IPv4 rede) {
		// Cria uma rede com 0.0.0.0 para ser o qualquer
		IPv4 redeZero = new IPv4("0.0.0.0/0");
		// Acha o IP e manda para a porta
        for(int i = 0; i < lista.size(); i++)
            if(lista.get(i).getRede() == rede)
                return lista.get(i).getPorta();
		//Vai para o QUALQUER -> usando o próximo HOP
        for(int i = 0; i < lista.size(); i++)
			if(lista.get(i).getRede() == redeZero)
				return lista.get(i).getPorta();	
			
		return null; // Deu algum problema !
    }
}