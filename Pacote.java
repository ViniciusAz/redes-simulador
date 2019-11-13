public class Pacote {
    //cabecalho?
    private IPv4 origem;
    private IPv4 destino;
    private int ttl;
    private boolean temProximo; //1 = tem, 0 = ultimo
    private int offset;

    private List<Rota> rota;
    
    public Pacote(IPv4 origem, IPv4 destino, int ttl) {
        this.origem = origem;
        this.destino = destino;
        this.ttl = ttl;
    }
    public Pacote(IPv4 origem, IPv4 destino, int ttl, boolean temProximo, int offset) {
        this.origem = origem;
        this.destino = destino;
        this.ttl = ttl;
        this.temProximo = temProximo;
        this.offset = offset;
    }

    public IPv4 getOrigem() { return origem; }
    public IPv4 getDestino() { return destino; }
    public int getTtl() { return ttl; }
    public boolean temProximo() { return temProximo; }
    public int getOffset() { return offset; }
    public void updateTtl() { ttl--; }
}