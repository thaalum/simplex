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
        int numeroLinhas = 4, colunaPivot = 1, numeroColunas=6;
        double pivot[] = new double[3];
        double matriz[][] = {
            {-1, 1, 1, 0, 0, 11},
            {1, 1, 0, 1, 0, 27},
            {2, 5, 0, 0, 1, 90},
            {-4, -6, 0, 0, 0, 0},};
        pivot = Simplex.procurarVariavelSaida(numeroLinhas, matriz, colunaPivot, numeroColunas);
        if (pivot[0] == 1 && pivot[1] == 0 && pivot[2] == 1) {
            System.out.println("1-testeProcurarVariavelSaida retorna valor correto");
        } else {
            System.out.println("0-testeProcurarVariavelSaida retorna valor incorreto");
        }

    }

    public static void testeDividirLinhaPivot() {
        double[] pivot = {1, 0, 1};
        int numeroLinhas = 4;
        double matriz[][] = {
            {-1, 1, 1, 0, 0, 11},
            {1, 1, 0, 1, 0, 27},
            {2, 5, 0, 0, 1, 90},
            {-4, -6, 0, 0, 0, 0},};
        Simplex.dividirLinhaPivot(pivot, numeroLinhas, matriz);
        if (matriz[0][1] == 1 && matriz[0][2] == 1 && matriz[0][5] == 11) {
            System.out.println("1-testeDividirLinhaPivot retorna valor correto");
        } else {
            System.out.println("0-testeDividirLinhaPivot retorna valor incorreto");
        }
    }

    public static void testeVariavelEntrada() {
        double matriz[][] = {
            {-1, 1, 1, 0, 0, 11},
            {1, 1, 0, 1, 0, 27},
            {2, 5, 0, 0, 1, 90},
            {-4, -6, 0, 0, 0, 0},};
        int numeroLinhas = 4, numeroColunas=6;
        int colunaPivot = Simplex.variavelEntrada(numeroLinhas, numeroColunas, matriz);
        if (colunaPivot == 1) {
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
        for (int i = 0; i < numLinhas; i++) {
            for (int j = 0; j < numColunas; j++) {
                if (matriz[i][j] != matrizsaida[i][j]) {
                    System.out.println("0 - testeanulaLinhas" + i + j);
                }

            }
        }
        System.out.println("1 - testeanulaLinhas");
    }

    public static void testetransposta() {
        double matriz[][] = {
            {-1, 1, 1, 0, 0, 11},
            {1, 1, 0, 1, 0, 27},
            {2, 5, 0, 0, 1, 90},
            {-4, -6, 0, 0, 0, 0},};
        double transposta[][] = {
            {-1, 1, 2, -4},
            {1, 1, 5, -6},
            {1, 0, 0, 0},
            {0, 1, 0, 0},
            {0, 0, 1, 0},
            {11, 27, 90, 0},};
        double [][] trans = new double [matriz[0].length][matriz.length];
        trans = minimizacao.transposta(matriz);
        for (int i = 0;
                i < trans.length;
                i++) {
            for (int j = 0; j < trans[0].length; j++) {
                if (transposta[i][j] != trans[i][j]) {
                    System.out.println("0 - testetransposta" + i + j);
                }

            }
        }

        System.out.println(
                "1 - testetransposta");
    }

    public static void executarTestes() throws FileNotFoundException {
        testeRetornaNumLinhasFicheiro();
        testeanulaLinhas();
        testetransposta();
        testeProcurarVariavelSaida();
        testeDividirLinhaPivot();
        testeVariavelEntrada();

    }

}
