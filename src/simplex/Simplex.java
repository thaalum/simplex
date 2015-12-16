package simplex;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Simplex {
    public static String nomeFicheiroEntrada;
    public static String nomeFicheiroSaida;
    public static void main(String[] args) throws FileNotFoundException {
        //TestesUnitarios.executarTestes();
        nomeFicheiroEntrada = args[0];
        nomeFicheiroSaida = args[1];
        System.out.println(nomeFicheiroEntrada);
        System.out.println(nomeFicheiroSaida);
        int numLinhas = retornaNumLinhasFicheiro(nomeFicheiroEntrada);
        System.out.println(numLinhas);
        
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
    /**precisa da matriz**/
    public static int variavelEntrada(int numLinhas, matriz[][]){
        double valorMenor = 0;
        int colunaPivot=0;
        int j;
        for (j = 0; j < numLinhas + 2; j++) {
            if (matriz[numLinhas-1][j] < valorMenor) {
                valorMenor = matriz[numLinhas-1][j];
                colunaPivot=j;
            }
        }
        return colunaPivot;

}
