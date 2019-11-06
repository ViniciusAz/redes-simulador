public class Node {
    private String id;
    private String mac;
    private IPv4 ip;
    private int mtu;
    private IPv4 rede;
    private ArpTable atable;

    public Node(String id, String mac, String ip, int mtu, String rede) {
        this.id = id;
        this.mac = mac;
        this.ip = new IPv4(ip);
        this.mtu = mtu;
        this.rede = new IPv4(rede);
        atable = new ArpTable();
    }

}