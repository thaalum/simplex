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
    public static double[][] matriz;

    public static void main(String[] args) throws FileNotFoundException {
        TestesUnitarios.executarTestes();
        nomeFicheiroEntrada = args[0];
        nomeFicheiroSaida = args[1];
        int numLinhasFicheiro = retornaNumLinhasFicheiro(nomeFicheiroEntrada);
        numColunasMatriz = numLinhasFicheiro + 2;
        numLinhasMatriz = numLinhasFicheiro;
        System.out.println(numColunasMatriz + " colunas");
        System.out.println(numLinhasMatriz + " linhas");
        String variaveisBase[] = criarVectorVariaveis(numLinhasFicheiro);
        String cabecalho[][] = criarCabecalhoMatriz(numColunasMatriz);

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

    public static String[] retornaMatrizTemp(String ficheiro, int numLinhas) throws FileNotFoundException {
        String[] matrizTemp = new String[numLinhas];
        Scanner ler = new Scanner(new File(ficheiro));
        int i = 0;
        while (ler.hasNextLine()) {
            String aux = ler.nextLine();
            if (!aux.isEmpty()) {
                matrizTemp[i] = aux.replaceAll("\\s", "");
                matrizTemp[i] = matrizTemp[i].replaceAll("[<|≤]", "=");
                i++;
            }
        }
        return matrizTemp;
    }

    public static double[][] preencheMatriz(String[] matrizTemp, int numLinhas) throws FileNotFoundException {
        double matriz1[][] = new double[numLinhas][numLinhas + 2];
        matriz1[0][2] = 0.0;
        matriz1[0][3] = 0.0;
        matriz1[0][4] = 0.0;
        matriz1[0][5] = 0.0;
        matriz1[1][3] = matriz1[1][4] = 0.0;
        matriz1[2][2] = matriz1[2][4] = 0.0;
        matriz1[3][2] = matriz1[3][3] = 0.0;
        matriz1[1][2] = matriz1[2][3] = matriz1[3][4] = 1.0;
        for (int i = 0; i < numLinhas; i++) {
            if (i != 0) {
                for (int l = 0; l < matrizTemp[i].length(); l++) {
                    if (matrizTemp[i].charAt(l) == '=') {
                        String valorB = matrizTemp[i].substring(l + 1, matrizTemp[i].length());
                        matriz1[i][numLinhas + 1] = Double.parseDouble(valorB);
                    }
                }
            }
            for (int j = 0; j < matrizTemp[i].length(); j++) {
                if (matrizTemp[i].charAt(j) == 'x' | matrizTemp[i].charAt(j) == 'X') {
                    int numIncognita = Character.getNumericValue(matrizTemp[i].charAt(j + 1));
                    if (j == 0) {
                        if (i == 0) {
                            matriz1[i][numIncognita - 1] = -1.0;
                        } else {
                            matriz1[i][numIncognita - 1] = 1.0;
                        }
                    } else if (matrizTemp[i].charAt(j - 1) == '0' & j == 1) {
                        matriz1[i][numIncognita - 1] = 0.0;
                    } else if ((matrizTemp[i].charAt(j - 1) == '0') & (matrizTemp[i].charAt(j - 2) == '+' | matrizTemp[i].charAt(j - 2) == '=')) {
                        matriz1[i][numIncognita - 1] = 0.0;
                    } else if (matrizTemp[i].charAt(j - 1) == '+' | matrizTemp[i].charAt(j - 1) == '=') {
                        if (i == 0) {
                            matriz1[i][numIncognita - 1] = -1.0;
                        } else {
                            matriz1[i][numIncognita - 1] = 1.0;
                        }
                    } else {
                        for (int k = j - 1; k >= 0; k--) {
                            if (k == 0) {
                                if (i == 0) {
                                    String coefIncognita = Character.toString(matrizTemp[i].charAt(k));
                                    matriz1[i][numIncognita - 1] = Double.parseDouble(coefIncognita) * (-1);
                                } else {
                                    String coefIncognita = Character.toString(matrizTemp[i].charAt(k));
                                    matriz1[i][numIncognita - 1] = Double.parseDouble(coefIncognita);
                                }
                                k = 0;
                            }
                            else if (matrizTemp[i].charAt(k) == '+' | matrizTemp[i].charAt(k) == '=') {
                                if (i == 0) {
                                    String coefIncognita = matrizTemp[i].substring(k + 1, j);
                                    matriz1[i][numIncognita - 1] = Double.parseDouble(coefIncognita) * (-1);
                                } else {
                                    String coefIncognita = matrizTemp[i].substring(k + 1, j);
                                    matriz1[i][numIncognita - 1] = Double.parseDouble(coefIncognita);
                                }
                                k = 0;
                            }
                            else if (matrizTemp[i].charAt(k) == '0') {
                                for (int l = k - 1; l >= 0; l--) {
                                    if (matrizTemp[i].charAt(l) != '0') {
                                        String coefIncognita = matrizTemp[i].substring(l, k+1);
                                        matriz1[i][numIncognita - 1] = Double.parseDouble(coefIncognita);
                                    }
                                }
                                k = 0;
                            }
                        }
                    }
                }
            }
        }
        return matriz1;
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
    public static double[] procurarVariavelSaida(int numeroLinhas, double[][] matriz, int colunaPivot, String[] variaveisBase, String[][] cabecalho) {
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
        atualizarVariaveisBase(cabecalho, variaveisBase, pivot);
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
        int linhaPivot = (int) pivot[1];
        for (int i = 0; i < numeroLinhas + 2; i++) {
            matriz[linhaPivot][i] = (matriz[linhaPivot][i] / pivot[0]);
        }
    }

    public static void anulaLinhas(double[] pivot, double[][] matriz, int numeroLinhasMatriz, int numeroColunasMatriz) {
        int linha = (int)pivot[1];
        int coluna = (int)pivot[2];
        int i , j;
        for (i = 0; i < numeroLinhasMatriz; i++) {
            if ( i == linha)
                i = i + 1;

            for (j = 0; j < numeroColunasMatriz + 2; j++) {

                matriz[i][j] = (matriz[i][coluna]*(-1) * matriz[linha][j] + matriz[i][j]);

            }

        }
    }

    public static void escreveFicheiroTexto(double[][] matriz, String[][] cabecalho) throws FileNotFoundException {
        File ficheiro = new File(nomeFicheiroSaida);
        Formatter escrever = new Formatter(ficheiro);
        cabecalho(cabecalho);
        for (int i = 0; i < matriz.length; i++) {
            escrever.format("\n");
            for (int j = 0; j < matriz[0].length + 2; j++) {
                escrever.format("%3.2f", matriz[i][j]);
            }
            if (i == matriz.length) {
                escrever.format("\n");
            }
        }
        escrever.close();

    }
     
    public static void imprimematrizconsola (double [][] matriz, int numeroLinhasFicheiro, int numeroColunasFicheiro, String [] variaveisBase) {
        int i=0, j=0;
        for (i=0; i < numeroLinhasFicheiro; i++){
            for (j=0; j< numeroColunasFicheiro; j++) {
                System.out.printf("%3.2f", matriz[i][j]);
            }
            System.out.println();
        }

    }

    /**
     * cabeçalho da matriz no ficheiro
     *
     * @param cabecalho
     * @throws FileNotFoundException
     */
    private static void cabecalho(String[][] cabecalho) throws FileNotFoundException {
        File ficheiro = new File(nomeFicheiroSaida);
        Formatter escrever = new Formatter(ficheiro);
        for (int i = 0; i < cabecalho[0].length; i++) {
            escrever.format("%5s", cabecalho[0][i]);
        }
        escrever.format("%n");
    }

    public static void verificaLinhaZ(double[][] matriz, int numeroColunasMatriz, int numeroLinhasMatriz) {

        int j = 0;
        double cont = 0;              
        for (j=0; j < numColunasMatriz; j++){
            if (matriz[numLinhasMatriz][j] < cont){
                cont = matriz[numLinhasMatriz][j];
            }
        }

        while (cont < 0) {
            /*
            chamar metodos
            verificar ordem de entrada dos metodos
            */
           int colunaPivot = variavelEntrada (numLinhasMatriz, matriz);
            double [] pivot = procurarVariavelSaida (numLinhasMatriz, matriz, colunaPivot, variaveisBase, cabecalho);
            dividirLinhaPivot (pivot, numLinhasMatriz,matriz );
            anulaLinhas ( pivot, matriz, numLinhasMatriz, numColunasMatriz);
            escreveFicheiroTexto (matriz, cabecalho);
            imprimematrizconsola (matriz, numLinhasMatriz, numColunasMatriz, variaveisBase);
            
            
            for (j=0; j < numColunasMatriz; j++){
                if (matriz[numLinhasMatriz][j] < cont){
                    cont = matriz[numLinhasMatriz][j];
        }
    }
        }}
    

    /**
     * cria um vector com as variaveis base (s1, s2, etc)
     *
     * @param numeroLinhas
     * @return
     */
    public static String[] criarVectorVariaveis(int numeroLinhas) {
        String variaveisBase[] = new String[numeroLinhas - 1];
        for (int i = 0; i < numeroLinhas - 1; i++) {
            String folga = "s" + i;
            variaveisBase[i] = folga;
        }
        return variaveisBase;
    }

    /**
     * cria uma matriz com com os valores do cabeçalho
     *
     * @param numColunas
     * @return
     */
    public static String[][] criarCabecalhoMatriz(int numColunas) {
        String cabecalho[][] = new String[1][numColunas];
        cabecalho[1][0] = "X1";
        cabecalho[1][1] = "X2";
        cabecalho[1][numColunas - 1] = "b";
        for (int i = 2; i < numColunas - 1; i++) {
            String folga = "S" + i;
            cabecalho[1][i] = folga;
        }
        return cabecalho;
    }

    /**
     * com cada iteraçao da matriz, actualiza o vectos variaveis base com a
     * variavel de entrada correspondete a coluna do pivot
     *
     * @param cabecalho
     * @param variaveisBase
     * @param pivot
     */
    public static void atualizarVariaveisBase(String[][] cabecalho, String[] variaveisBase, double[] pivot) {
        int coluna = (int) pivot[2];
        int linha = (int) pivot[1];
        variaveisBase[linha] = cabecalho[0][coluna];
    }
    public static void solucaoBasica(int numeroLinhas, String[] variaveisBase, int numColunas) throws FileNotFoundException {
        // Escrever solução como (x1,x2,s1,s2.)=(_,_,_,_)
        File ficheiro = new File(nomeFicheiroSaida);
        Formatter escrever = new Formatter(ficheiro);
        escrever.format("%s%5s", "(", "X1,X2");
        for (int i = 1; i < numeroLinhas; i++) {
            String folga = "S" + i;
            escrever.format("%2s", folga);
        }
        escrever.format("%4s", ") = ");

        double[] solucao = new double[numeroLinhas];
        int nEl = 0;
        int j = 0;
        while (!variaveisBase[j].equals("X1")
                && j < variaveisBase.length) {
            j++;
        }

        if (j < variaveisBase.length) {
            solucao[nEl] = matriz[j][numColunas - 1];
            nEl++;
        } else {
            solucao[nEl] = 0;
            nEl++;

            while (!variaveisBase[j].equals("X2")
                    && j < variaveisBase.length) {
                j++;
            }
            
            if (j < variaveisBase.length) {
                solucao[nEl] = matriz[j][numColunas - 1];
                nEl++;
            } else {
                solucao[0] = 0;
                nEl++;
                for (int i = 1; i < numeroLinhas; i++) {
                    String folga = "S" + i;
                    int x = 0;
                    while (!variaveisBase[x].equals(folga) && x < variaveisBase.length) {
                        x++;
                    }
                    
                    if (x < variaveisBase.length) {
                        solucao[nEl] = matriz[x][numColunas - 1];
                        nEl++;
                    } else {
                        solucao[nEl] = 0;
                        nEl++;
                    }
                    escrever.format("%n");
                }

            }
        }
    }
}
