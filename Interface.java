public class Interface {
    private String id;
    private IPv4 ip;
    private String mac;
    private int mtu;
    private String gateway;

    public Interface(String id, String ip, String mac, int mtu, String gateway) {
        this.id = id;
        this.ip = new IPv4(ip);
        this.mac = mac;
        this.mtu = mtu;
        this.gateway = gateway;
    }

    public String getId() { return id; }
    public IPv4 getIp() { return ip; }
    public String getMac() { return mac; }
    public int getMtu() { return mtu; }
    public String getGateway() { return gateway; }
}