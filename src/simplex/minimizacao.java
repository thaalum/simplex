/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplex;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Formatter;
/**
 *
 * @author teixe
 */
public class minimizacao {

    public static void main(String[] args) {
        TestesUnitarios.testetransposta();
        double matrizTransposta[][]= transposta(matriz);
        double matrizMin [][]=//metodo q adiciona variaveis folga a matriz
        int numVariaveis=//metodo q retorna numero de variaveis da matriz
        String cabecalhoMin[]=criarCabecalhoMatrizMin(numcolunas, numVariaveis);
        String variaveisBaseMin[] = Simplex.criarVectorVariaveis(numLinhasMatriz);
        minimizacao();
        verificaLinhaZMin(matrizMin, numColunasMatriz, numLinhasMatriz, cabecalhoMin, variaveisBaseMin);
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
     public static void minimizacao()throws FileNotFoundException{
       File ficheiro = new File(nomeFicheiroSaida);
       Formatter escrever = new Formatter(ficheiro); 
       escrever.format("%24s%n", "Problema de Minimização!");
       System.out.println("Problema de Minimização!");
       escrever.close();
    }
      public static String[] criarCabecalhoMatrizMin(int numColunas, int numVariavies) {
        String cabecalhoMin[] = new String[numColunas];
        for(int j=0; j< numVariaveis; i++){
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
       public static void verificaLinhaZMin(double[][] matrizMin, int numeroColunasMatriz, int numeroLinhasMatriz, String cabecalhoMin[], String variaveisBaseMin[]) throws FileNotFoundException {

        boolean temNegativos = false;
        do {
            int colunaPivotMin = Simplex.variavelEntrada(numLinhasMatriz, numColunasMatriz, matrizMin);
            double[] pivotMin = Simplex.procurarVariavelSaida(numLinhasMatriz, matrizMin, colunaPivotMin, numColunasMatriz);
            Simplex.atualizarVariaveisBase(cabecalhoMin, variaveisBaseMin, pivotMin);
            temNegativos = false;
            Simplex.dividirLinhaPivot(pivotMin, numColunasMatriz, matrizMin);
            Simplex.anulaLinhas(pivotMin, matrizMin, numLinhasMatriz, numColunasMatriz);
            Simplex.escreveFicheiroTexto(matrizMin, cabecalhoMin, numLinhasMatriz);
            Simplex.imprimeMatrizConsola(matrizMin, numLinhasMatriz, numColunasMatriz, variaveisBaseMin);
            for (int j = 0; j < numColunasMatriz - 1; j++) {
                if (matrizMin[numLinhasMatriz - 1][j] < 0) {
                    temNegativos = true;
                }
            }
        } while (temNegativos);
        Simplex.solucaoBasica(numLinhasMatriz, variaveisBaseMin, numColunasMatriz);
    }
}
