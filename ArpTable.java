import java.util.HashMap;
import java.util.Map;

public class ArpTable {
    private Map<IPv4, String> lista;

    public ArpTable() {
        lista = new HashMap<IPv4, String>();
    }

    public void add(IPv4 ip, String mac) { lista.put(ip, mac); }
    public String get(IPv4 ip) { return lista.get(ip); }

    public String toString() {
        String x = "";
        for (Map.Entry<IPv4, String> entry : lista.entrySet()) {
            x += "ArpT : " + entry.getKey().printIp() + " " + entry.getValue() + "\n";
        }
        return x;
    }
    /*
    private class Arp {
        IPv4 ip;
        String mac;
        public Arp(IPv4 ip, String mac){
            this.ip = ip;
            this.mac = mac;
        }
        public IPv4 getIp() { return ip; }
        public String getMac() { return mac; }
    }

    private ArrayList arpTable;

    public ArpTable(){
        ips = new ArrayList();
    }

    public void add(IPv4 ip, String mac) {
        arptable.add(new Arp(ip,mac));
    }

    public IPv4 getIp(String mac){

    }
*/
}