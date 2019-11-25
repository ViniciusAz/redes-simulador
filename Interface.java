public class Interface {
    private int id;
    private IPv4 ip;
    private String mac;
    private int mtu;
    private String gateway;

    public Interface(int id, String ip, String mac, int mtu, String gateway) {
        this.id = id;
        this.ip = new IPv4(ip);
        this.mac = mac;
        this.mtu = mtu;
        this.gateway = gateway;
    }

    public int getId() { return id; }
    public IPv4 getIp() { return ip; }
    // public int getRede() { return ip.getRede(); }
    public String getMac() { return mac; }
    public int getMtu() { return mtu; }
    public String getGateway() { return gateway; }
    public String printIp() { return ip.printIp(); }

    public String toString() {
        return "[porta=" + id + "] " + ip.printIp() + " " + mac + " (mtu= " + mtu + ") " + gateway;
    }
}