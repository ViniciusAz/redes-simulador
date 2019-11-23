import java.util.List;
import java.util.ArrayList;

public class RouterTable{

    private class Entrada {
        private IPv4 rede;
        private IPv4 destino;
        private Interface porta;

        public Entrada(IPv4 rede, IPv4 destino, Interface porta) {
            this.rede = rede;
            this.destino = destino;
            this.porta = porta;
        }
        public Interface getPorta() { return porta; }
        public IPv4 getRede() { return rede; }
        public IPv4 getDestino() { return destino; }
    }

    ArrayList<Entrada> lista;

    public RouterTable() {
        lista = new ArrayList<Entrada>();
    }

    public void add(IPv4 rede, IPv4 destino, Interface porta) {
        lista.add(new Entrada(rede, destino, porta));
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
    public boolean temSalto() { return destino != null; }





    public int getPortaRede(IPv4 rede) {
        for(int i = 0; i < lista.size(); i++)
            if(lista.get(i).getIpRede() == rede)
                return lista.get(i).getPorta();
        return null;
    }

    public int getPortaDestino(IPv4 destino) {
        for(int i = 0; i < lista.size(); i++)
            if(lista.get(i).getIpRede() == destino)
                return lista.get(i).getPorta();
        return null;
    }

    public IPv4 getIpRede(Interface porta) {
        for(int i = 0; i < lista.size(); i++)
            if(lista.get(i).getPorta() == porta)
                return lista.get(i).getIpRede();
        return null;
    }

    public IPv4 getIpDestino(Interface porta) {
        for(int i = 0; i < lista.size(); i++)
            if(lista.get(i).getPorta() == porta)
                return lista.get(i).getIpDestino();
        return null;
    }
}