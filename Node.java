public class Node {
    private String id;
    private Interface porta;
    private ArpTable atable;

    public Node(String id, String mac, String ip, int mtu, String gateway) {
        this.id = id;
        porta = new Interface("e0", ip, mac, mtu, gateway);
        atable = new ArpTable();
    }

    public String getId() { return id; }
    //interface
    //add
}