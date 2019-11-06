import java.util.ArrayList;
import java.util.List;

public class ArpTable{
    private class Arp{
        IPv4 ip;
        String mac;
        public Arp(IPv4 nip, String nmac){
            ip = nip;
            mac = nmac;
        }
        public IPv4 getIp(){
            return ip;
        }
        public String getMac(){
            return mac;
        }
    }

    private ArrayList arptable;

    public ArpTable(){
        ips = new ArrayList();
    }

    public add(IPv4 ip, String mac){
        Arp narp = new Arp(ip,mac);
        arptable.add(narp);
    }

    public IPv4 getIp(String mac){

    }
}