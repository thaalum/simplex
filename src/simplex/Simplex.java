package simplex;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Formatter;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Simplex {

    public static String nomeFicheiroEntrada;
    public static String nomeFicheiroSaida;
    public static int numLinhasMatriz;
    public static int numColunasMatriz;
    public static double[][] matriz;
    public static String nomeFicheiroErros = "logErros.txt";

    public static void main(String[] args) throws FileNotFoundException {
        nomeFicheiroEntrada = args[0];
        nomeFicheiroSaida = args[1];
        numLinhasMatriz = retornaNumLinhasFicheiro(nomeFicheiroEntrada);
        String matrizTemp[] = retornaMatrizTemp(nomeFicheiroEntrada, numLinhasMatriz);
        numColunasMatriz = numLinhasMatriz + Utilitarios.procuraNumeroVariaveis(matrizTemp[Utilitarios.procuraLinhaZ(matrizTemp, numLinhasMatriz)]+1);
        String variaveisBase[] = criarVectorVariaveis(numLinhasMatriz);
        String cabecalho[] = criarCabecalhoMatriz(numColunasMatriz);
        matriz = preencheMatriz(matrizTemp, numLinhasMatriz);
        verificaLinhaZ(matriz, numColunasMatriz, numLinhasMatriz, cabecalho, variaveisBase);

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
     *
     * @param ficheiro
     * @param numLinhas
     * @return
     * @throws FileNotFoundException
     */
    public static String[] retornaMatrizTemp(String ficheiro, int numLinhas) throws FileNotFoundException {
        String[] matrizTemp = new String[numLinhas];
        Scanner ler = new Scanner(new File(ficheiro));
        int i = 0;
        while (ler.hasNextLine()) {
            String aux = ler.nextLine();
            if (!aux.isEmpty()) {
                matrizTemp[i] = aux.replaceAll("\\s", "");
                matrizTemp[i] = aux.replaceAll(",", "\\.");
                matrizTemp[i] = matrizTemp[i].replaceAll("[<|≤]", "=");
                matrizTemp[i] = matrizTemp[i].replaceAll("[>|≥]", "=");
                i++;
            }
        }
        return matrizTemp;
    }

    public static double[][] preencheMatriz(String[] matrizTemp, int numLinhas) throws FileNotFoundException {

        int numVariaveis = Utilitarios.procuraNumeroVariaveis(matrizTemp[Utilitarios.procuraLinhaZ(matrizTemp, numLinhas)]);
        int numColunas = numLinhas + numVariaveis + 1;
        double matriz1[][] = new double[numLinhas][numColunas];
        for (int m = 0; m < numLinhas; m++) {
            for (int o = 0; o < numColunas; o++) {
                matriz1[m][o] = 0.0;
            }
        }
        int numLinhaEscrever = 0;
        for (int i = 0; i < numLinhas; i++) {
            if (i == Utilitarios.procuraLinhaZ(matrizTemp, numLinhas)) {
                String linha = matrizTemp[i];
                Pattern encontraValores = Pattern.compile("([-]?\\d*[.]?\\d{1,2}[\\/]?\\d*[xX]\\d*)");
                Matcher mValores = encontraValores.matcher(linha);
                while (mValores.find()) {
                    String valor = mValores.group();
                    double coefVariavelDouble;
                    int posicaoX = (valor.indexOf("x"));
                    int numVariavel = Integer.parseInt(valor.substring(posicaoX + 1, valor.length()));
                    String coefVariavel = valor.substring(0, (valor.length()) - 2);
                    if (valor.charAt(0) == '-') {
                        coefVariavel = valor.substring(1, posicaoX);
                        coefVariavelDouble = (Double.parseDouble(coefVariavel));
                    } else {
                        coefVariavelDouble = ((Double.parseDouble(coefVariavel)) * (-1.0));
                    }
                    matriz1[numLinhas - 1][numVariavel - 1] = coefVariavelDouble;
                }
            } else {
                String linha = matrizTemp[i];
                Pattern encontraValores = Pattern.compile("([-]?\\d*[.]?\\d{1,2}[\\/]?\\d*[xX]\\d*)");
                Matcher mValores = encontraValores.matcher(linha);
                while (mValores.find()) {
                    String valor = mValores.group();
                    double coefVariavelDouble;
                    int posicaoX = (valor.indexOf("x"));
                    int numVariavel = Integer.parseInt(valor.substring(posicaoX + 1, valor.length()));
                    String coefVariavel = valor.substring(0, (valor.length()) - 2);
                    if (valor.charAt(0) == '-') {
                        coefVariavel = valor.substring(1, posicaoX);
                        coefVariavelDouble = ((Double.parseDouble(coefVariavel)) * (-1.0));
                    } else {
                        coefVariavelDouble = (Double.parseDouble(coefVariavel));
                    }
                    matriz1[numLinhaEscrever][numVariavel - 1] = coefVariavelDouble;
                    Pattern encontraB = Pattern.compile("(?:.*[=])([-]?\\d*[\\.]?\\d*[\\/]?\\d*[\\.]?\\d*)");
                    Matcher mB = encontraB.matcher(linha);
                    mB.find();
                    if (valor.charAt(0) == '-') {
                        matriz1[numLinhaEscrever][numColunas - 1] = (Double.parseDouble(mB.group(1))) * (-1);
                    } else {
                        matriz1[numLinhaEscrever][numColunas - 1] = Double.parseDouble(mB.group(1));
                    }
                }
                numLinhaEscrever++;
            }
        }
        int contador = numVariaveis;
        for (int n = 0; n < numLinhas - 1; n++) {
            matriz1[n][contador] = 1.0;
            contador++;
        }
        for (int m = 0; m < numLinhas; m++) {
            for (int o = 0; o < numColunas; o++) {
                System.out.print(matriz1[m][o] + " ");
            }
            System.out.println("");
        }
        return matriz1;

    }

    /**
     * precisa da matriz
     *
     *
     * @param numLinhas
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
     * @param cabecalho
     * @return
     */
    public static double[] procurarVariavelSaida(int numeroLinhas, double[][] matriz, int colunaPivot, int numeroColunas) {
        double pivot[] = new double[3];
        pivot[2] = colunaPivot;
        double linhaPivot = 0;
        double pivotTemp = 0;
        int i = 0;
        double temp = 0;
        while (i < numeroLinhas - 1 && ((matriz[i][numeroColunas - 1]) / (matriz[i][colunaPivot]) < 0)) {
            i++;
        }
        if (i < numeroLinhas - 1) {
            temp = ((matriz[i][numeroColunas - 1]) / (matriz[i][colunaPivot]));
            linhaPivot = i;
            pivotTemp = matriz[i][colunaPivot];
        }
        for (int j = i + 1; j < matriz.length - 1; j++) {
            double aux = ((matriz[j][numeroColunas - 1]) / (matriz[j][colunaPivot]));
            if (temp > aux && aux > 0) {
                temp = aux;
                linhaPivot = j;
                pivotTemp = matriz[j][colunaPivot];
            }
        }
        pivot[0] = pivotTemp;
        pivot[1] = linhaPivot;
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
        int linha = (int) pivot[1];
        int coluna = (int) pivot[2];
        int i, j;
        for (i = 0; i < numeroLinhasMatriz; i++) {
            if (i == linha) {
                i = i + 1;
            }

            for (j = 0; j < numeroColunasMatriz; j++) {

                matriz[i][j] = matriz[i][j] - (-matriz[i][coluna]) * matriz[linha][j];
            }
        }
    }

    public static void escreveFicheiroTexto(double[][] matriz, String[] cabecalho, int numLinhasMatriz) throws FileNotFoundException {
        File ficheiro = new File(nomeFicheiroSaida);
        Formatter escrever;
        escrever = new Formatter(ficheiro);
        cabecalho(cabecalho);

        for (int i = 0; i < numLinhasMatriz; i++) {
            escrever.format("\n");
            for (int j = 0; j < matriz[i].length; j++) {
                escrever.format("%3.2f", matriz[i][j]);

            }
            if (i == matriz.length) {
                escrever.format("\n");
            }
        }
        escrever.close();
    }

    public static void imprimeMatrizConsola(double[][] matriz, int numeroLinhasFicheiro, int numeroColunasFicheiro, String[] variaveisBase) {
        int i = 0, j = 0;
        for (i = 0; i < numeroLinhasFicheiro; i++) {
            for (j = 0; j < numeroColunasFicheiro; j++) {
                System.out.printf("%3.2f%s", matriz[i][j], " ");
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
    public static void cabecalho(String[] cabecalho) throws FileNotFoundException {
        File ficheiro = new File(nomeFicheiroSaida);
        Formatter escrever = new Formatter(ficheiro);
        for (int i = 0; i < cabecalho.length; i++) {
            escrever.format("%5s", cabecalho[i]);
        }
        escrever.format("%n");
        escrever.close();
    }

    public static void verificaLinhaZ(double[][] matriz, int numeroColunasMatriz, int numeroLinhasMatriz, String cabecalho[], String variaveisBase[]) throws FileNotFoundException {

        int j = 0;
        boolean temNegativos = false;
        do {
            int colunaPivot = variavelEntrada(numLinhasMatriz, matriz);
            double[] pivot = procurarVariavelSaida(numLinhasMatriz, matriz, colunaPivot, numColunasMatriz);
            atualizarVariaveisBase(cabecalho, variaveisBase, pivot);
            temNegativos = false;
            dividirLinhaPivot(pivot, numLinhasMatriz, matriz);
            anulaLinhas(pivot, matriz, numLinhasMatriz, numColunasMatriz);
            escreveFicheiroTexto(matriz, cabecalho, numLinhasMatriz);
            imprimeMatrizConsola(matriz, numLinhasMatriz, numColunasMatriz, variaveisBase);
            for (j = 0; j < numColunasMatriz - 1; j++) {
                if (matriz[numLinhasMatriz - 1][j] < 0) {
                    temNegativos = true;
                }
            }
        } while (temNegativos);
        solucaoBasica(numLinhasMatriz, variaveisBase, numColunasMatriz);
    }

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
    public static String[] criarCabecalhoMatriz(int numColunas) {
        String cabecalho[] = new String[numColunas];
        cabecalho[0] = "X1";
        cabecalho[1] = "X2";
        cabecalho[numColunas - 1] = "b";
        for (int i = 2; i < numColunas - 1; i++) {
            String folga = "S" + i;
            cabecalho[i] = folga;
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
    public static void atualizarVariaveisBase(String[] cabecalho, String[] variaveisBase, double[] pivot) {
        int coluna = (int) pivot[2];
        int linha = (int) pivot[1];
        variaveisBase[linha] = cabecalho[coluna];
    }

    /**
     *
     * @param numeroLinhas
     * @param variaveisBase
     * @param numColunas
     * @throws FileNotFoundException
     */
    public static void solucaoBasica(int numeroLinhas, String[] variaveisBase, int numColunas) throws FileNotFoundException {
        // Escrever solução como (x1,x2,s1,s2.)=(_,_,_,_)
        File ficheiro = new File(nomeFicheiroSaida);
        Formatter escrever = new Formatter(ficheiro);
        escrever.format("%s%5s", "(", "X1,X2");
        for (int i = 1; i < numeroLinhas; i++) {
            String numFolga = String.valueOf(i);
            String folga = ",S";
            folga = folga.concat(numFolga);
            escrever.format("%2s", folga);
        }
        escrever.format("%4s", ") = ");
        double[] solucao = new double[numeroLinhas];
        int nEl = 0;
        int j = 0;
        while (!variaveisBase[j].equals("X1") && j < variaveisBase.length) {
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
                    escrever.format("%s", solucao);
                }
            }
        }
        escrever.close();
    }
}
