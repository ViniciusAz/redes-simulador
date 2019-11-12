public class Pacote {
    //cabecalho?
    private IPv4 origem;
    private IPv4 destino;
    private int ttl;
    private int temProximo; //1 = tem, 0 = ultimo
    private int offset;

    private List<Rota> rota;

    
}