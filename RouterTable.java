import java.util.List;
import java.util.ArrayList;


public class RouterTable{

    private class Entrada {
        private IPv4 ipRede;
        private IPv4 ipDestino;
        private int porta;

        public Entrada(IPv4 ipRede, IPv4 ipDestino, int porta){
            this.ipRede = ipRede;
            this.ipDestino = ipDestino;
            this.porta = porta;
        }
        
        public int getPorta() { return porta; }
        public IPv4 getIpRede() { return ipRede; }
        public IPv4 getIpDestino() { return ipDestino; }

    }

    ArrayList<Entrada> portas;

    public RouterTable() {
        portas = new ArrayList<Entrada>();
    }

    public void add(IPv4 ipRede, IPv4 ipDestino, int porta){
        Entrada n = new Entrada(ipRede, ipDestino, porta);
        portas.add(n);
    }

    public void add(IPv4 ipRede, int porta){
        Entrada n = new Entrada(ipRede, null, porta);
        portas.add(n);
    }

    public IPv4 getIpRede(int porta){
        for(int i = 0; i < portas.size(); i++)
            if(portas.get(i).getPorta() == porta)
                return portas.get(i).getIpRede();
        return null;
    }

    public IPv4 getIpDestino(int porta){
        for(int i = 0; i < portas.size(); i++)
            if(portas.get(i).getPorta() == porta)
                return portas.get(i).getIpDestino();
        return null;
    }

    public IPv4 getPortaRede(IPv4 ipRede){
        for(int i = 0; i < portas.size(); i++)
            if(portas.get(i).getIpRede() == ipRede)
                return portas.get(i).getPorta();
        return null;
    }

    public IPv4 getPortaDestino(IPv4 ipDestino){
        for(int i = 0; i < portas.size(); i++)
            if(portas.get(i).getIpRede() == ipDestino)
                return portas.get(i).getPorta();
        return null;
    }

}