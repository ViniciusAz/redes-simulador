public class Node {
    private String id;
    private Interface interface;
    private ArpTable atable;

    public Node(String id, String mac, String ip, int mtu) {
        this.id = id;
        interface = new Interface("e0", ip, mac, mtu);
        atable = new ArpTable();
    }

}