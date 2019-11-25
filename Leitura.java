import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

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
        lendoArquivo();      
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
                // System.out.println(linha);
                if(linha.length() > 0 && linha.charAt(0) == '$') break;
                if(linha.equals("#NODE")) { ehNode = true; ehRouter = ehRouterT = false; }
                else if(linha.equals("#ROUTER")) { ehRouter = true; ehNode = ehRouterT = false; }
                else if(linha.equals("#ROUTERTABLE")) { ehRouterT = true; ehNode = ehRouter = false;
                } else {
                    aux = linha.split("\\,");
                    if(ehRouter) {
                        System.out.println("@@@ é router " + Arrays.toString(aux));
                        ArrayList<Interface> listaInterfaces = new ArrayList<Interface>();
                        for(int i = 0; i < Integer.parseInt(aux[1]); i++) {
                            System.out.println(" > " + aux[3*i+2] + " " + aux[3*i+3] + " " + aux[3*i+4]);
                            //                               ID      IP           MAC          MTU   gateway
                            listaInterfaces.add(new Interface(i, aux[3*i+3], aux[3*i+2], Integer.parseInt(aux[3*i+4]), ""));
                        }
                        roteadores.add(new Router(aux[0], listaInterfaces, new RouterTable()));

                    } else if(ehNode) {
                        System.out.println("@@@ é node " + Arrays.toString(aux));
                        //estrutura :       id,     ip,     mac,             mtu,           gateway
                        nodos.add(new Node(aux[0], aux[2], aux[1], Integer.parseInt(aux[3]), aux[4]));
                    } else {
                        for (Router r : roteadores) {
                            if(r.getId() == aux[0]) {
                                //estrutura :                      ip     ip    interface
                                r.addRouterTable(aux[1], aux[2], aux[3]);
                                break;
                            }
                        }
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