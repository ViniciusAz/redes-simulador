import java.util.List;
import java.util.ArrayList;

public class Router {
    private String id;
    private List<Interface> interfaces;
    private RouterTable rtable;
    private ArpTable atable;

    public Router (String id, String mac, String ip, int mtu, String rede) {
        this.id = id;
        interfaces = new ArrayList<Interface>();
        rtable = new RouterTable();
        atable = new ArpTable();
    }
}