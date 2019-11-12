public class IPv4 {

    private String ip;
    private int cidr;
    private int endereco;
    private int rede;

    public IPv4(String ip) {
        this.ip = ip;
        //Fazer o split da string e tratamento, etc.
        String[] array = ip.split("\\/");
        cidr = Integer.parseInt(array[1]);
        array = array[0].split("\\.");
        endereco = (Integer.parseInt(array[0]) << 24)
                 + (Integer.parseInt(array[1]) << 16)
                 + (Integer.parseInt(array[2]) << 8)
                 + Integer.parseInt(array[3]);
        rede = (endereco & (Integer.MAX_VALUE << (32 - cidr)));
    }

    public String getIp() { return ip; }
    public int getCidr() { return cidr; }
    public int getEndereco() { return endereco; }
    public int getRedeBin() { return rede; }
    public String getRede() { return "rede"; }
    
    public String toString() {
        return (
            "Endereço IP : " + ip +
            "\nCidr        : " + cidr +
            "\nIP Binario  : " + endereco +
            "\nRede        : " + rede
        );
    }

}