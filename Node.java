public class Node {
    private String id;
    private Interface porta;
    private ArpTable atable;

    public Node(String id, String ip, String mac, int mtu, String gateway) {
        this.id = id;
        porta = new Interface(0, ip, mac, mtu, gateway);
        atable = new ArpTable();
    }

    public String getId() { return id; }
    public IPv4 getIp() { return porta.getIp(); }
    public int getRede() { return getIp().getRede(); }
    public String getMac() { return porta.getMac(); }
    public int getMtu() { return porta.getMtu(); }
    public String getGateway() { return porta.getGateway(); }
    public String printIp() { return porta.printIp(); }
    public String printRede() { return porta.getIp().printRede(); }
    public void add(IPv4 ip, String mac) { atable.add(ip, mac); }
    public boolean temArp(IPv4 ip) { return atable.get(ip) != null; }

    public String toString() {
        return id + " : " + porta + "\n" + atable;
    }


}