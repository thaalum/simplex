/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplex;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static simplex.Simplex.nomeFicheiroSaida;
import static simplex.Simplex.numLinhasMatriz;



/**
 *
 * @author teixe
 */
public class minimizacao {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        TestesUnitarios.testetransposta();
        String []matrizTemp= Simplex.retornaMatrizTempValidada(nomeFicheiroSaida, numLinhasMatriz);
        double matriz[][]= preencheMatrizMin(matrizTemp, numLinhasMatriz);
        double matrizTransposta[][]= transposta(matriz);
        int numLinhasMin=matrizTransposta.length;
        int numColunasMin=matrizTransposta[0].length + matrizTransposta.length;
        double matrizMin[][]= adicionaVFolgaMatriz(matrizTransposta, numLinhasMin, numColunasMin);
        int numVariaveis=matrizTransposta[0].length - 1;
        String cabecalhoMin []= criarCabecalhoMatrizMin(numColunasMin, numVariaveis);
        String variaveisBaseMin[] = Simplex.criarVectorVariaveis(numLinhasMin);
        minimizacao();
        verificaLinhaZMin(matrizTransposta, numColunasMin, numLinhasMin, cabecalhoMin, variaveisBaseMin, numVariaveis);
    }

    public static double [][] transposta (double [][]matriz) {
        double transposta[][] = new double[matriz[0].length][matriz.length];
        for (int lin = 0; lin < matriz.length; lin++) {
            for (int col = 0; col < matriz[lin].length; col++) {
                transposta[col][lin] = matriz[lin][col];
            }
        }
        return transposta;
    }
     public static void minimizacao() throws FileNotFoundException, IOException {
        System.out.println("Problema de Minimização!");
        try{
        File ficheiro = new File(nomeFicheiroSaida);
        FileWriter fileWriter = new FileWriter(ficheiro, true);
        BufferedWriter escrever = new BufferedWriter(fileWriter);
        escrever.write("Problema de Minimização!");
        escrever.newLine();
        escrever.newLine();
        escrever.close();
         }
        catch(IOException ex) {
           ex.printStackTrace();
        }
    }
      public static String [] criarCabecalhoMatrizMin(int numColunas, int numVariaveis) {
        String cabecalhoMin[] = new String[numColunas];
        for(int j=0; j< numVariaveis; j++){
              String variavel= "Y"+j;
              cabecalhoMin[j]=variavel;
        }
        cabecalhoMin[numColunas - 1] = "b";
        int k=1;
        for (int i = numVariaveis; i < numColunas - 1; i++) {
            String folga = "S"+k;
            cabecalhoMin[i] = folga;
            k++;
        }
        return cabecalhoMin;
    }
       public static void verificaLinhaZMin(double[][] matrizMin, int numColunasMin, int numLinhasMin, String cabecalhoMin[], String variaveisBaseMin[], int numVariaveis) throws FileNotFoundException, IOException {

        boolean temNegativos = false;
        do {
            int colunaPivotMin = Simplex.variavelEntrada(numLinhasMin, numColunasMin, matrizMin);
            double[] pivotMin = Simplex.procurarVariavelSaida(numLinhasMin, matrizMin, colunaPivotMin, numColunasMin);
            Simplex.atualizarVariaveisBase(cabecalhoMin, variaveisBaseMin, pivotMin);
            temNegativos = false;
            Simplex.dividirLinhaPivot(pivotMin, numColunasMin, matrizMin);
            Simplex.anulaLinhas(pivotMin, matrizMin, numLinhasMin, numColunasMin);
            Simplex.escreveFicheiroTexto(matrizMin, cabecalhoMin, numLinhasMin);
            Simplex.imprimeMatrizConsola(matrizMin, numLinhasMin, numColunasMin, variaveisBaseMin);
            for (int j = 0; j < numColunasMin - 1; j++) {
                if (matrizMin[numLinhasMin - 1][j] < 0) {
                    temNegativos = true;
                }
            }
        } while (temNegativos);
        Simplex.solucaoBasica(numLinhasMin, variaveisBaseMin, numColunasMin, numVariaveis, cabecalhoMin);
    }
       
       
       public static double[][] preencheMatrizMin(String[] matrizTemp, int numLinhas) throws FileNotFoundException {

        int numVariaveis = Utilitarios.procuraNumeroVariaveis(matrizTemp[Utilitarios.procuraLinhaZ(matrizTemp, numLinhas)]);
        int numColunas = numVariaveis + 1;
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
        
        for (int m = 0; m < numLinhas; m++) {
            for (int o = 0; o < numColunas; o++) {
                System.out.print(matriz1[m][o] + " ");
            }
            System.out.println("");
        }
        return matriz1;

    }

    private static double[][] adicionaVFolgaMatriz(double[][] matrizTransposta, int numLinhasMatriz, int numColunasMatriz) {
        double matrizMin [][]= new double[numLinhasMatriz][numColunasMatriz];
        for (int m = 0; m < numLinhasMatriz; m++) {
            for (int o = 0; o < numColunasMatriz; o++) {
                matrizMin[m][o] = 0.0;
            }
        }
        for (int i=0; i < matrizTransposta.length; i++){
            for( int j=0; j< matrizTransposta[0].length-1; j++){
                matrizMin[i][j]= matrizTransposta[i][j];
            }
        }
        int contador = matrizTransposta[0].length-2;
        for (int n = 0; n < numLinhasMatriz - 1; n++) {
            matrizMin[n][contador] = 1.0;
            contador++;
        }
        int b= matrizTransposta[0].length-1;
        for (int z=0; z<numLinhasMatriz; z++){
            matrizMin[z][numColunasMatriz-1]=matrizTransposta[z][b];
        }
        return matrizMin;
    }
}
