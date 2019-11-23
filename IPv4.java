public class IPv4 {

    private String ip;
    private String rede;
    private int cidr;
    private int ipBin;
    private int redeBin;

    public IPv4(String ip) {
        this.ip = ip;
        //Fazer o split da string e tratamento, etc.
        String[] array = ip.split("\\/");
        if(array.length > 1)
            cidr = Integer.parseInt(array[1]);
        else
            cidr = 0;
        array = array[0].split("\\.");
        ipBin = (Integer.parseInt(array[0]) << 24)
                 + (Integer.parseInt(array[1]) << 16)
                 + (Integer.parseInt(array[2]) << 8)
                 + Integer.parseInt(array[3]);
        if(cidr > 0) {
            redeBin = (ipBin & (Integer.MAX_VALUE << (32 - cidr)));
            rede = (redeBin >> 24 & 0xFF) + "." + (redeBin >> 16 & 0xFF) + "." + (redeBin >> 8 & 0xFF) + "." + (redeBin & 0xFF);
        } else {
            redeBin = 0;
            rede = "";
        }
    }

    public int getIp() { return ipBin; }
    public int getCidr() { return cidr; }
    public int getRede() { return redeBin; }
    public String printIp() { return ip; }
    public String printRede() { return rede; }
    
    public String toString() {
        return (
            "Endereço IP : " + ip + " (" + ipBin + ")" +
            "\nCidr        : " + cidr +
            "\nRede        : " + rede + " (" + redeBin + ")"
        );
    }

}