import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class Leitura {
    Scanner ler = new Scanner(System.in);
    FileReader arq;
    BufferedReader lerArq;
    ArrayList<Router> roteadores;
    ArrayList<Node> nodos;

    public Leitura(String arquivo) throws FileNotFoundException {
        arq        = new FileReader(arquivo);
        lerArq     = new BufferedReader(arq);
        roteadores = new ArrayList<Router>();
        nodos      = new ArrayList<Node>();         
    }

    public void lendoArquivo() {
        boolean ehNode    = false;
        boolean ehRouter  = false;
        boolean ehRouterT = false;

        try {
            Scanner ler = new Scanner(System.in);
            String linha = " ";
            String[] aux;
            while (linha != null) {
                linha = lerArq.readLine(); // lê a linha inteira
                if(linha.equals("#NODE")) { ehNode = true; ehRouter = ehRouterT = false; }
                else if(linha.equals("#ROUTER")) { ehRouter = true; ehNode = ehRouterT = false; }
                else if(linha.equals("#ROUTERTABLE")) { ehRouterT = true; ehNode = ehRouter = false;
                } else {
                    if(ehRouter) {
                        aux = linha.split(",");
                        ArrayList<Interface> listaInterfaces = new ArrayList<Interface>();
                        for(int i = 0; i < Integer.parseInt(aux[1]); i++) {
                            //                                   ID        IP             MAC          MTU
                            listaInterfaces.add(new Interface(("e"+i), aux[3*(3+i)], aux[2*(3+i)], Integer.parseInt(aux[4*(3+i)])));
                        }
                        roteadores.add(new Router(aux[0], listaInterfaces, novaRouterTable));

                    } else if(ehNode) {

                    } else {

                    }
                }
            }
            arq.close();
        } catch (IOException e) {
            System.err.printf("Erro na abertura do arquivo: %s.\n",
            e.getMessage());
        }
    }

    public ArrayList<Router> getRoteadores() { return roteadores; }
    public ArrayList<Node> getNodos() { return nodos; }
}