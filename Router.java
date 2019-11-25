import java.util.List;
import java.util.ArrayList;

public class Router {
    private String id;
    private List<Interface> interfaces;
    private RouterTable rtable;
    private ArpTable atable;

    public Router (String id, ArrayList<Interface> interfaces, RouterTable rtable) {
        this.id = id;
        this.interfaces = interfaces;
        this.rtable = rtable;
        atable = new ArpTable();
    }

    public String getId() { return id; }
    public Interface getPortaGateway(String ip) {
        for (Interface i : interfaces) {
            if(i.getIp().printIpNoCidr().equals(ip)) return i;
        }
        return null;
    }

    public void add(IPv4 ip, String mac) { atable.add(ip, mac); }
    public boolean temArp(IPv4 ip) { return atable.get(ip) != null; }
    
    public Interface buscaRouterTable(String rede) {
        return rtable.getPortaRede(rede);
    }
    public String buscaProximoHop(String rede) {
        return rtable.getHop(rede).printIpNoCidr();
    }
    public void addRouterTable(String rede, String proxHop, String porta) {
        if(proxHop == "0.0.0.0") rtable.add(new IPv4(rede), null, getPorta(porta));
        else rtable.add(new IPv4(rede), new IPv4(proxHop), getPorta(porta));
    }

    public Interface getPorta(String porta) {
        for (Interface i : interfaces) {
            if(i.getId() == Integer.parseInt(porta))
                return i;
        }
        return null;
    }
    public RouterTable getRT() {
        System.out.println(rtable.size());

        return rtable;
    }

    // esses metodos tem que desbravar a RouterT
    // public IPv4 getIp() { return porta.getIp(); }
    // public int getRede() { return getIp().getRede(); }
    // public String getMac() { return porta.getMac(); }
    // public int getMtu() { return porta.getMtu(); }
    // public String printIp() { return porta.printIp(); }
}