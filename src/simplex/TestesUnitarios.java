package simplex;

import java.io.File;
import java.io.FileNotFoundException;

public class TestesUnitarios {

    public static void testeRetornaNumLinhasFicheiro() throws FileNotFoundException {
        File ficheiro1 = new File("testeRetornaNumLinhasFicheiro1.txt");
        File ficheiro2 = new File("testeRetornaNumLinhasFicheiro2.txt");
        int numeroLinhas = Simplex.retornaNumLinhasFicheiro(ficheiro1.toString());
        if (numeroLinhas == 6) {
            System.out.println("1 - testeReturnNumLinhasFicheiro1");
        } else {
            System.out.println("0 - testeReturnNumLinhasFicheiro1");
        }
        numeroLinhas = Simplex.retornaNumLinhasFicheiro(ficheiro2.toString());
        if (numeroLinhas == 5) {
            System.out.println("1 - testeReturnNumLinhasFicheiro2");
        } else {
            System.out.println("0 - testeReturaNumLinhasFicheiro2");
        }
    }

    public static void testeProcurarVariavelSaida() {
        int numeroLinhas = 5, colunaPivot = 0;
        double pivot[] = new double[3];
        double[][] matriz = {
            {2, -5, 2, -5, -45},
            {0, 2, 0, 2, -6},
            {1, 0, 0, 0, 0},
            {0, 1, 0, 0, 0},
            {0, 0, 1, 0, 0},
            {0, 0, 0, 1, 0},
            {5, 4, 5, 4, 0},};
        //pivot = Simplex.procurarVariavelSaida(numeroLinhas, matriz, colunaPivot);
        if (pivot[0] == 2 && pivot[1] == 0 && pivot[2] == 0) {
            System.out.println("1-testeProcurarVariavelSaida retorna valor correto");
        } else {
            System.out.println("0-testeProcurarVariavelSaida retorna valor incorreto");
        }

    }

    public static void testeDividirLinhaPivot() {
        double[] pivot = {2, 0, 0};
        int numeroLinhas = 5;
        double[][] matriz = {
            {2, -5, 2, -5, -45},
            {0, 2, 0, 2, -6},
            {1, 0, 0, 0, 0},
            {0, 1, 0, 0, 0},
            {0, 0, 1, 0, 0},
            {0, 0, 0, 1, 0},
            {5, 4, 5, 4, 0},};
        Simplex.dividirLinhaPivot(pivot, numeroLinhas, matriz);
        if (matriz[0][0] == 1 && matriz[0][2] == 1 / 2 && matriz[0][6] == 5 / 2) {
            System.out.println("1-testeDividirLinhaPivot retorna valor correto");
        } else {
            System.out.println("0-testeDividirLinhaPivot retorna valor incorreto");
        }
    }

    public static void testeVariavelEntrada() {
        double[][] matriz = {
            {2, -5, 2, -5, -45},
            {0, 2, 0, 2, -6},
            {1, 0, 0, 0, 0},
            {0, 1, 0, 0, 0},
            {0, 0, 1, 0, 0},
            {0, 0, 0, 1, 0},
            {5, 4, 5, 4, 0},};
        int numeroLinhas = 5;
        int colunaPivot = Simplex.variavelEntrada(numeroLinhas, matriz);
        if (colunaPivot == 0) {
            System.out.println("1-testeVariavelEntrada retorna valor correto");
        } else {
            System.out.println("0-testeDVariavelEntrada retorna valor incorreto");
        }
    }

    public static void testeanulaLinhas() {
        double pivot[] = {1, 0, 1};
        int numLinhas = 4, numColunas = 6;
        double matriz[][] = {
            {-1, 1, 1, 0, 0, 11},
            {1, 1, 0, 1, 0, 27},
            {2, 5, 0, 0, 1, 90},
            {-4, -6, 0, 0, 0, 0},};
        double matrizsaida[][] = {
            {-1.00, 1.00, 1.00, 0.00, 0.00, 11.00},
            {2.00, 0.00, -1.00, 1.00, 0.00, 16.00},
            {7.00, 0.00, -5.00, 0.00, 1.00, 35.00},
            {-10.00, 0.00, 6.00, 0.00, 0.00, 66.00},};
        Simplex.anulaLinhas(pivot, matriz, numLinhas, numColunas);
        for (int i = 0; i < numLinhas; i++){
            for (int j = 0; j < numColunas; j++){
                if (matriz[i][j] != matrizsaida[i][j]){
                    System.out.println("0 - testeanulaLinhas" + i + j);
                } 
                    
                }
            }
        System.out.println("1 - testeanulaLinhas");
        }
        
      
    

    public static void executarTestes() throws FileNotFoundException {
        testeRetornaNumLinhasFicheiro();
        testeanulaLinhas();
        //testeProcurarVariavelSaida();
        testeDividirLinhaPivot();
        testeVariavelEntrada();
        
    }

}
