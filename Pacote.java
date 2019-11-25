public class Pacote {
    private String mensagem;
    private int ttl;
    private int mf; //more fragments (1=temproximo 0=ultimo)
    private int off;
    
    public Pacote(String mensagem, int ttl, int mf, int off) {
        this.mensagem = mensagem;
        this.ttl = ttl;
        this.mf = mf;
        this.off = off;
    }
    public Pacote(String mensagem, int ttl) {
        this.mensagem = mensagem;
        this.ttl = ttl;
        this.mf = 0;
        this.off = 0;
    }
    public Pacote(String mensagem) {
        this.mensagem = mensagem;
        this.ttl = 8;
        this.mf = 0;
        this.off = 0;
    }

    public String getMensagem() { return mensagem; }
    public int getTtl() { return ttl; }
    public int getMf() { return mf; }
    public int getOffset() { return off; }
    public void updateTtl() { ttl--; }
}