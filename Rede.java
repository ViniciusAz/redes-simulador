import java.util.ArrayList;
import java.util.List;

public class Rede {
    public List<Node> nodos;
    public List<Router> roteadores;

    public Rede(String arquivo, String noOrigem, String noDestino, String mensagem) {
        Leitura io = new Leitura(arquivo);
        nodos = io.getNodos();
        roteadores = io.getRoteadores();
    }

    //Met. que manipulam Listas de Roteadores e Nodos (add/get/size/remove/etc)
    public void add(Router roteador) { roteadores.add(roteador); }
    public void add(Node nodo) { nodos.add(nodo); }
    
    public Router getR(int i) { return roteadores.get(i); }
    public Node getN(int i) { return nodos.get(i); }

    public Router getR(String id) {
        for (Router r : roteadores)
            if(r.getId() == id) return r;
        return null;
    }
    public Node getN(String id) {
        for (Node n : nodos)
            if(n.getId() == id) return n;
        return null;
    }
}