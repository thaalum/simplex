package simplex;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Simplex {

    public static void main(String ficheiroInput, String ficheiroOutput) throws FileNotFoundException {
        System.out.println("Insira o nome do ficheiro: ");
        Scanner ler = new Scanner(System.in);
        int numLinhas = retornaNumLinhasFicheiro(ler.toString());
        System.out.println(numLinhas);
        testesUnitarios.testeRetornaNumLinhasFicheiro();
    }

    /**
     *
     * @param ficheiro
     * @return
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

}
