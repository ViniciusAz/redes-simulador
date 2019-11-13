import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.list;
import java.util.ArrayList;

public Leitura{
    Scanner ler = new Scanner(System.in);
    FileReader arq;
    BufferedReader lerArq;
    ArrayList<Router> roteadores;
    ArrayList<Node> nodos;
    public leitura(String txt){
        arq        = new FileReader(txt);
        larArq     = new BufferedReader(arq);
        roteadores = new ArrayList<Router>();
        nodos      = new ArrayList<Node>();         
    }
    public lendoArquivo(){
        boolean node        = false;
        boolean router      = false;
        boolean routertable = false;


        try {
            Scanner ler = new Scanner(System.in);
            String linha = lerArq.readLine(); // Primeira Linha
            String[] aux;
            if(linha.equals("#NODE")) node = true;

            while (linha != null) {
                linha = lerArq.readLine(); // lê da segunda até a última linha
                if(linha.equals("#ROUTER")){
                    router = true;
                    node = false;
                    routertable = false;
                }
                else if(linha.equals("#ROUTERTABLE")){
                    routertable = true;
                    node = false;
                    routertable = false;
                }else{
                    if(router){
                        aux = linha.split(",");
                        ArrayList<Interface> listaInterfaces = new ArrayList<Interface>();
                        for(int i = 0; i < Integer.parseInt(aux[1]; i++)){
                            //                                   ID        IP             MAC          MTU
                            listaInterfaces.add(new Interface(("e"+i), aux[3*(3+i)], aux[2*(3+i)], aux[4*(3+i)]));
                        }
                        roteadores.add(new Router(aux[0], listaInterfaces, novaRouterTable));
                            

                    }else if(node){

                    }else{

                    }
                }
            }
 
            arq.close();
        } catch (IOException e) {
            System.err.printf("Erro na abertura do arquivo: %s.\n",
            e.getMessage());
        }
    }
    public ArrayList<Router> getRoteadores(){return roteadores;}
    public ArrayList<Nodes> getNodos(){return nodos;}
}