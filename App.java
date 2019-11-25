import java.util.Arrays;
import java.util.List;

public class App {
    public static void main(String[] args) {
        //Rede rede = new Rede(args[0], args[1], args[2], args[3]);

        //Ajustando e testando cada classe
        IPv4 ip1 = new IPv4("200.200.21.13/16");
        IPv4 ip2 = new IPv4("200.125.8.78");
        System.out.println(ip1);
        System.out.println(ip2);

        ArpTable arp = new ArpTable();
        arp.add(ip1, ":AA");
        arp.add(ip2, ":BB");
        System.out.println(arp);

        System.out.println(arp.get(ip1));
        System.out.println(arp.get(new IPv4("200.150.12.3")));

        Interface i1 = new Interface(1, "55.15.8.95", ":CC", 15, "55.0.0.1");
        System.out.println(i1);

        // Node nz = new Node(id, mac, ip, mtu, gateway)
        Node n1 = new Node("N1", "105.15.8.95", ":CC", 15, "105.0.0.1");
        Node n2 = new Node("N2", "105.15.8.96", ":CD", 15, "105.0.0.1");
        n1.add(n2.getIp(), ":CD");
        n2.add(n1.getIp(), ":CC");
        IPv4 ipR1 = new IPv4("105.15.8.1");
        n1.add(ipR1, ":01");
        n2.add(ipR1, ":01");

        System.out.println(n1.temArp(ipR1));
        System.out.println(n1.temArp(new IPv4("200.150.12.3")));

        System.out.println(n1);
        System.out.println(n2);
        System.out.println("Simulador de Rede");

    }
}