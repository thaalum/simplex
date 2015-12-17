package simplex;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Formatter;
import java.util.Scanner;

public class Simplex {

    public static String nomeFicheiroEntrada;
    public static String nomeFicheiroSaida;
    public static int numLinhasMatriz;
    public static int numColunasMatriz;
    public static String[][] matriz;

    public static void main(String[] args) throws FileNotFoundException {
        TestesUnitarios.executarTestes();
        nomeFicheiroEntrada = args[0];
        nomeFicheiroSaida = args[1];
        int numLinhasFicheiro = retornaNumLinhasFicheiro(nomeFicheiroEntrada);
        numColunasMatriz = numLinhasFicheiro + 2;
        numLinhasMatriz = numLinhasFicheiro;
        System.out.println(numColunasMatriz + " colunas");
        System.out.println(numLinhasMatriz + " linhas");
        String variaveisBase [] = criarVectorVariaveis(numLinhasFicheiro);
        String cabecalho [][]= criarCabecalhoMatriz(numColunasMatriz);
        
    }

    /**
     *
     * @param String ficheiro
     * @return int numeroLinhas
     * @throws FileNotFoundException
     */
    public static int retornaNumLinhasFicheiro(String ficheiro) throws FileNotFoundException {
        Scanner ler = new Scanner(new File(ficheiro));
        int numeroLinhas = 0;
        while (ler.hasNextLine()) {
            String aux = ler.nextLine();
            if (!aux.isEmpty()) {
                numeroLinhas++;
            }
        }
        return numeroLinhas;
    }

    /**
     * precisa da matriz*
     */
    public static int variavelEntrada(int numLinhas, double matriz[][]) {
        double valorMenor = 0;
        int colunaPivot = 0;
        int j;
        for (j = 0; j < numLinhas + 2; j++) {
            if (matriz[numLinhas - 1][j] < valorMenor) {
                valorMenor = matriz[numLinhas - 1][j];
                colunaPivot = j;
            }
        }
        return colunaPivot;

    }

    /**
     * cria vector pivot constituido pelo valor do pivot em 0, linha de pivot em
     * 1 e coluna do pivot em 2
     *
     * @param numeroLinhas
     * @param matriz
     * @param colunaPivot
     * @return
     */
    public static double[] procurarVariavelSaida(int numeroLinhas, double[][] matriz, int colunaPivot) {
        double pivot[] = new double[3];
        pivot[2] = colunaPivot;
        for (int i = 0; i < numeroLinhas - 1; i++) {
            double temp = ((matriz[i][numeroLinhas + 1]) / (matriz[i][colunaPivot]));
            for (int j = i + 1; j < numeroLinhas; j++) {
                double aux = ((matriz[j][numeroLinhas + 1]) / (matriz[j][colunaPivot]));

                if (temp > 0 && aux > 0) {
                    if (temp < aux) {
                        pivot[1] = i;
                        pivot[0] = matriz[i][colunaPivot];
                    } else {
                        pivot[1] = j;
                        pivot[0] = matriz[j][colunaPivot];
                    }
                } else {
                    if (temp > 0) {
                        pivot[1] = i;
                        pivot[0] = matriz[i][colunaPivot];
                    }
                    if (aux > 0) {
                        pivot[1] = j;
                        pivot[0] = matriz[j][colunaPivot];
                    }
                }
            }
        }
        return pivot;
    }

    /**
     * Divide a Linha do pivot pelo seu valor
     *
     * @param pivot
     * @param numeroLinhas
     * @param matriz
     */
    public static void dividirLinhaPivot(double[] pivot, int numeroLinhas, double[][] matriz) {
        double linhaPivot = pivot[1];
        for (int i = 0; i < numeroLinhas + 2; i++) {
            matriz[linhaPivot][i] = (matriz[linhaPivot][i] / pivot[0]);
        }
    }

    public static void anulaLinhas(double[] pivot, double[][] matriz, int numeroLinhasFicheiro) {
        double linha = pivot[1];
        int i = 0, j = 0;
        for (i = 0; i <= numeroLinhasFicheiro; i++) {
            /*
            avançar caso i seja = linha Pivot
            if (i = pivot[1]) {
                continue;
            }
            i++;
            */
              
            for (j = 0; j <= numeroLinhasFicheiro + 2; j++) {

                matriz [linha][j] = (matriz[i][j] * matriz [linha][j] + matriz [i][j]);
                      
                          
            }
            
        }
    }

    public static void escreveFicheiroTexto(double [][] matriz) throws FileNotFoundException {

        File ficheiro = new File(nomeFicheiroSaida);
        Formatter escrever = new Formatter(ficheiro);
        for (int i = 0; i < matriz.length; i++) {
            escrever.format("\n");
            for (int j = 0; j < matriz[0].length + 2; j++) {
              escrever.format("%3.2f",matriz[i][j]);  
            }
            if (i == matriz.length) {
                escrever.format("\n");
            }
        }
        escrever.format("");
        escrever.close();

    }
     
    public static void imprimematrizconsola (double [][] matriz, int numeroLinhasFicheiro, int numeroColunasFicheiro, int [] variaveisbase) {
        int i=0, j=0;
        for (i=0; i <= numeroLinhasFicheiro; i++){
            for (j=0; j<= numeroColunasFicheiro; j++) {
                System.out.printf("%3.2f", matriz[i][j]);
            }
            System.out.println();
        }
        
    }      
    private static void cabecalho(String [][] cabecalho) throws FileNotFoundException {
     File ficheiro= new File(nomeFicheiroSaida);
     Formatter escrever = new Formatter( ficheiro);
        for(int i=0; i<cabecalho[0].length; i++){          
             escrever.format("%5s", cabecalho[0][i]);                       
        }       
        escrever.format("%n");          
    }   
    

    
    public static void verificaLinhaZ (double [][] matriz, int numeroColunasFicheiro, int numeroLinhasFicheiro){
        
        int j = 0;
        double cont = 0;
        
        
        for (j=0; j <= numeroColunasFicheiro; j++){
            if (matriz[numeroLinhasFicheiro][j] < cont){
                cont = matriz[numeroLinhasFicheiro][j];
            }
        }
        
        while (cont < 0) {
            /*
            chamar metodos
            verificar ordem de entrada dos metodos
            */
           
            
            
            for (j=0; j <= numeroColunasFicheiro; j++){
                if (matriz[numeroLinhasFicheiro][j] < cont){
                    cont = matriz[numeroLinhasFicheiro][j];
        }
    
    
    }

     public static String [] criarVectorVariaveis(int numeroLinhas) {
        String variaveisBase[] = new String[numeroLinhas - 1];
        for (int i = 0; i < numeroLinhas - 1; i++) {
            String folga = "s" + i;
            variaveisBase[i] = folga;
        } return variaveisBase;
    }
      public static String[][] criarCabecalhoMatriz(int numColunas){
        String cabecalho[][] = new String[1][numColunas];
        cabecalho[1][0] = "X1";
        cabecalho[1][1] = "X2";
        cabecalho[1][numColunas-1] = "b";
        for (int i = 2; i < numColunas-1; i++) {
            String folga = "S" + i;
            cabecalho[1][i] = folga;
        } return cabecalho;
    }
}
