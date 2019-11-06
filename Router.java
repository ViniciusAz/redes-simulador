public class Router {
    private String id;
    private String mac;
    private IPv4 ip;
    private int mtu;
    private IPv4 rede;
    private RouterTable rtable;
    private ArpTable atable;

    public Router (String id, String mac, String ip, int mtu, String rede) {
        this.id = id;
        this.mac = mac;
        this.ip = new IPv4(ip);
        this.mtu = mtu;
        this.rede = new IPv4(rede);
        rtable = new RouterTable();
        atable = new ArpTable();
    }
}