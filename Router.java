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
    //busca na arp
    //busca na routertable
    //busca na interface
}