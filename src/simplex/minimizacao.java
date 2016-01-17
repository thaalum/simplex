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
import static simplex.Simplex.matriz;

import static simplex.Simplex.nomeFicheiroSaida;
import static simplex.Simplex.numLinhasMatriz;
import static simplex.Simplex.numColunasMatriz;
import static simplex.Simplex.numVariaveis;

/**
 *
 * @author teixe
 */
public class minimizacao {

    public static void main(String[] args) throws FileNotFoundException {
        TestesUnitarios.testetransposta();
        double matrizTransposta[][]= transposta(matriz);
        String cabecalhoMin []= new String[numColunasMatriz];
        //double matrizMin [][]=//metodo q adiciona variaveis folga a matriz
        
        cabecalhoMin = criarCabecalhoMatrizMin(numColunasMatriz, numVariaveis);
        String variaveisBaseMin[] = Simplex.criarVectorVariaveis(numLinhasMatriz);
        //minimizacao();
        verificaLinhaZMin(matrizTransposta, numColunasMatriz, numLinhasMatriz, cabecalhoMin, variaveisBaseMin);
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
      public static String [] criarCabecalhoMatrizMin(int numColunas, int numVariavies) {
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
       public static void verificaLinhaZMin(double[][] matrizMin, int numeroColunasMatriz, int numeroLinhasMatriz, String cabecalhoMin[], String variaveisBaseMin[]) throws FileNotFoundException, IOException {

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
        Simplex.solucaoBasica(numLinhasMatriz, variaveisBaseMin, numColunasMatriz, numVariaveis, cabecalhoMin);
    }
}
