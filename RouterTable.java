import java.util.ArrayList;
import java.util.List;

public class RouterTable{

    private class Interface{
        private IPv4 ipRede;
        private IPv4 ipDestino;
        private int porta;

        public Interface(IPv4 ip1, IPv4 ip2, int nporta){
            ipRede = nIpRede;
            ipDestino = nipDestino;
            porta = nporta;
        }
        public int getPorta(){
            return porta;
        }

        public IPv4 getIpRede(){
            return ipRede;
        }

        public IPv4 getIpDestino(){
            return ipDestino;
        }

    }

    ArrayList<Interface> portas;

    public RouterTable(){
        portas = new ArrayList<Interface>();
    }

    public void addPorta(IPv4 ipRede, IPv4 ipDestino, int porta){
        Interface n = new Interface(ipRede, ipDestino, porta);
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