package simplex;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.text.DecimalFormat; 
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;

public class Simplex {

    public static String nomeFicheiroEntrada;
    public static String nomeFicheiroSaida;
    public static int numLinhasMatriz;
    public static int numColunasMatriz;
    public static int numVariaveis;
    public static double[][] matriz;
    public static String nomeFicheiroErros = "logErros.txt";

    public static void main(String[] args) throws FileNotFoundException, IOException {
        nomeFicheiroEntrada = args[0];
        nomeFicheiroSaida = args[1];
        numLinhasMatriz = retornaNumLinhasFicheiro(nomeFicheiroEntrada);
        String matrizTemp[] = retornaMatrizTempValidada(nomeFicheiroEntrada, numLinhasMatriz);
        numColunasMatriz = numLinhasMatriz + Utilitarios.procuraNumeroVariaveis(matrizTemp[Utilitarios.procuraLinhaZ(matrizTemp, numLinhasMatriz)]) + 1;
        numVariaveis = Utilitarios.procuraNumeroVariaveis(matrizTemp[Utilitarios.procuraLinhaZ(matrizTemp, numLinhasMatriz)]);
        
        TestesUnitarios.executarTestes();
        int validacao= Validacoes.decideMaxMin(matrizTemp, numLinhasMatriz);
        if(validacao==1){
        matriz = preencheMatriz(matrizTemp, numLinhasMatriz);
        maximizacao();
        String variaveisBase[] = criarVectorVariaveis(numLinhasMatriz);
        String cabecalho[] = criarCabecalhoMatriz(numColunasMatriz, numVariaveis);
        verificaLinhaZ(matriz, numColunasMatriz, numLinhasMatriz, cabecalho, variaveisBase);
        }
        else{if(validacao==0){
            minimizacao.main(args);
        } 
        
        
    }
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
    public static String[] retornaMatrizTempValidada(String ficheiro, int numLinhas) throws FileNotFoundException {
        String[] matrizTemp = new String[numLinhas];
        Scanner ler = new Scanner(new File(ficheiro));
        for (int i = 0; i < numLinhas; i++) {
            String aux = ler.nextLine();
            matrizTemp[i] = aux;
        }
        int i = 0;
        if (!Validacoes.verificaEspacosEmBranco(matrizTemp, numLinhasMatriz)) {
            while (ler.hasNextLine()) {
                String aux = ler.nextLine();
                if (!aux.isEmpty()) {
                    matrizTemp[i] = aux.replaceAll("\\s", "");
                    matrizTemp[i] = aux.replaceAll(",", "\\.");
                    i++;
                }
            }
        }
        if (Validacoes.verificaCasasDecimais(matrizTemp, numLinhasMatriz)) {
            System.out.println("Mais de duas casas decimais!!");
        }
        if (!Validacoes.verificaFuncaoRestricao(matrizTemp, numLinhasMatriz)) {
            System.out.println("Erro funçao obj ou restricao!!");
        }
        return matrizTemp;
    }

    /**
     *
     * @param matrizTemp
     * @param numLinhas
     * @return
     * @throws FileNotFoundException
     */
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
     *
     * @param numLinhas
     * @param numColunas
     * @param matriz
     * @return
     */
    public static int variavelEntrada(int numLinhas, int numColunas, double matriz[][]) {
        double valorMenor = 0.0;
        int colunaPivot = 0;
        for (int j = 0; j < numColunas; j++) {
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
     * @param numeroColunas
     * @return
     */
    public static double[] procurarVariavelSaida(int numeroLinhas, double[][] matriz, int colunaPivot, int numeroColunas) {
        double pivot[] = new double[3];
        pivot[2] = colunaPivot;
        double linhaPivot = 0.0;
        double pivotTemp = 0.0;
        int i = 0;
        double temp = 0.0;
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
     * @param numeroColunas
     * @param matriz
     */
    public static void dividirLinhaPivot(double[] pivot, int numeroColunas, double[][] matriz) {
        int linhaPivot = (int) pivot[1];
        for (int i = 0; i < numeroColunas; i++) {
            matriz[linhaPivot][i] = (matriz[linhaPivot][i] / pivot[0]);
        }
    }

    /**
     *
     * @param pivot
     * @param matriz
     * @param numeroLinhasMatriz
     * @param numeroColunasMatriz
     */
    public static void anulaLinhas(double[] pivot, double[][] matriz, int numeroLinhasMatriz, int numeroColunasMatriz) {
        int linha = (int) pivot[1];
        int coluna = (int) pivot[2];
        int i, j;
        for (i = 0; i < numeroLinhasMatriz; i++) {
            if (i == linha) {
                i = i + 1;
            }

            for (j = 0; j < numeroColunasMatriz; j++) {
                if (j == coluna) {
                    j = j + 1;
                }

                matriz[i][j] = matriz[linha][j] * (-matriz[i][coluna]) + matriz[i][j];
            }
            matriz[i][coluna] = 0.0;
        }
    }

    /**
     *
     * @param matriz
     * @param cabecalho
     * @param numLinhasMatriz
     * @throws FileNotFoundException
     */
    public static void escreveFicheiroTexto(double[][] matriz, String[] cabecalho, int numLinhasMatriz) throws FileNotFoundException, IOException {

        //Formatter escrever = new Formatter(ficheiro);
        cabecalhoEscrever(cabecalho);
        try {
            // Assume default encoding.
            File file = new File(nomeFicheiroSaida);
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            

            for (int i = 0; i < numLinhasMatriz; i++) {
                bw.newLine();
                for (int j = 0; j < matriz[i].length; j++) {
                    DecimalFormat dec = new DecimalFormat("0.##");
                    String temp = dec.format(matriz[i][j]);
                    
                    bw.write(temp + " | ");

                }
                if (i == matriz.length) {
                    bw.newLine();
                }
            }

            bw.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    /**
     *
     * @param matriz
     * @param numeroLinhasFicheiro
     * @param numeroColunasFicheiro
     * @param variaveisBase
     */
    public static void imprimeMatrizConsola(double[][] matriz, int numeroLinhasFicheiro, int numeroColunasFicheiro, String[] variaveisBase) {
        for (int i = 0; i < numeroLinhasFicheiro; i++) {
            for (int j = 0; j < numeroColunasFicheiro; j++) {
                System.out.printf("%3.2f%s", matriz[i][j], " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * cabeçalho da matriz no ficheiro
     *
     * @param cabecalho
     * @throws FileNotFoundException
     */
    public static void cabecalhoEscrever(String[] cabecalho) throws FileNotFoundException, IOException {
        File ficheiro = new File(nomeFicheiroSaida);
        try {
        FileWriter fileWriter = new FileWriter(ficheiro, true);
        BufferedWriter escrever = new BufferedWriter(fileWriter);
        for (int i = 0; i < cabecalho.length-1; i++) {
            escrever.write(cabecalho[i]);
            escrever.write("  ");
        }
        escrever.newLine();
        escrever.close();
         }
        catch(IOException ex) {
           ex.printStackTrace();
        }
    }

    /**
     *
     * @param matriz
     * @param numeroColunasMatriz
     * @param numeroLinhasMatriz
     * @param cabecalho
     * @param variaveisBase
     * @throws FileNotFoundException
     */
    public static void verificaLinhaZ(double[][] matriz, int numeroColunasMatriz, int numeroLinhasMatriz, String cabecalho[], String variaveisBase[]) throws FileNotFoundException, IOException {

        boolean temNegativos = false;
        do {
            int colunaPivot = variavelEntrada(numLinhasMatriz, numColunasMatriz, matriz);
            double[] pivot = procurarVariavelSaida(numLinhasMatriz, matriz, colunaPivot, numColunasMatriz);
            atualizarVariaveisBase(cabecalho, variaveisBase, pivot);
            temNegativos = false;
            dividirLinhaPivot(pivot, numColunasMatriz, matriz);
            anulaLinhas(pivot, matriz, numLinhasMatriz, numColunasMatriz);
            escreveFicheiroTexto(matriz, cabecalho, numLinhasMatriz);
            imprimeMatrizConsola(matriz, numLinhasMatriz, numColunasMatriz, variaveisBase);
            for (int j = 0; j < numColunasMatriz - 1; j++) {
                if (matriz[numLinhasMatriz - 1][j] < 0) {
                    temNegativos = true;
                }
            }
        } while (temNegativos);
        solucaoBasica(numLinhasMatriz, variaveisBase, numColunasMatriz, numVariaveis, cabecalho);
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
            String folga = "S" + i;
            variaveisBase[i] = folga;
        }
        return variaveisBase;
    }

    /**
     * 
     * @param numColunas
     * @param numVariavies
     * @return 
     */
   public static String[] criarCabecalhoMatriz(int numColunas, int numVariavies) {
        String cabecalho[] = new String[numColunas];
        for(int j=0; j< numVariaveis; j++){
              String variavel= "X"+j;
              cabecalho[j]=variavel;
        }
        cabecalho[numColunas - 1] = "b";
        int k=1;
        for (int i = numVariaveis; i < numColunas - 1; i++) {
            String folga = "S"+k;
            cabecalho[i] = folga;
            k++;
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
     * @param numVariaveis
     * @param cabecalho
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public static void solucaoBasica(int numeroLinhas, String[] variaveisBase, int numColunas, int numVariaveis, String []cabecalho) throws FileNotFoundException, IOException {
        // Escrever solução como (x1,x2,s1,s2.)=(_,_,_,_)
        File ficheiro = new File(nomeFicheiroSaida);
        try{
        FileWriter fileWriter = new FileWriter(ficheiro, true);
        BufferedWriter escrever = new BufferedWriter(fileWriter);  
        escrever.write("(");
        int i;
        for (i = 0; i < cabecalho.length-2; i++) {
            escrever.write(cabecalho[i]);
            escrever.write(", ");
        }
        escrever.write(cabecalho[i+1]);
        escrever.write(") = (");
               
        int[] solucao = new int[numColunas-1];
        int nEl = 0;
        int j = 0;
        for (int k=0; k<cabecalho.length-1; k++){  
        while (!variaveisBase[j].equals(cabecalho[k]) && j < variaveisBase.length) {
            j++;
        }
        if (j < variaveisBase.length) {
            solucao[nEl] = (int)matriz[j][numColunas - 1];
            nEl++;
        } else {
            solucao[nEl] = 0;
            nEl++;
        }
        }  int z;
         for (z = 0; z < solucao.length-1; z++) {           
             escrever.write(solucao[z]);
             escrever.write(", ");             
         }
         escrever.write(solucao[z+1]);
         escrever.write(") = Z = ");
         int Z= (int)matriz[numeroLinhas-1][numColunas-1];
         escrever.write(Z);
         escrever.newLine();    
        
        escrever.close();
     }
        catch(IOException ex) {
           ex.printStackTrace();
        }
    }

    public static void maximizacao() throws FileNotFoundException, IOException {
        System.out.println("Problema de Maximização!");
        File ficheiro = new File(nomeFicheiroSaida);
        try{
        FileWriter fileWriter = new FileWriter(ficheiro, true);
        BufferedWriter escrever = new BufferedWriter(fileWriter);
        escrever.write("Problema de Maximização!");
        escrever.newLine();
        escrever.newLine();
        escrever.close();
         }
        catch(IOException ex) {
           ex.printStackTrace();
        }
    }

  
     
}
