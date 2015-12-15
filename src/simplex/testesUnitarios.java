
package simplex;

import java.io.File;
import java.io.FileNotFoundException;

public class testesUnitarios {
    
    public static void testeRetornaNumLinhasFicheiro() throws FileNotFoundException {
        File ficheiro1 = new File("testeRetornaNumLinhasFicheiro1.txt");
        File ficheiro2 = new File("testeRetornaNumLinhasFicheiro2.txt");
        int numeroLinhas = Simplex.retornaNumLinhasFicheiro(ficheiro1.toString());
        if (numeroLinhas == 6)
        {
            System.out.println("1 - testeReturnaNumLinhasFicheiro1");
        }
        else
        {
            System.out.println("0 - testeReturnaNumLinhasFicheiro1");
        }
        numeroLinhas = Simplex.retornaNumLinhasFicheiro(ficheiro2.toString());
        if (numeroLinhas == 5)
        {
            System.out.println("1 - testeReturnaNumLinhasFicheiro2");
        }
        else
        {
            System.out.println("0 - testeReturnaNumLinhasFicheiro2");
        }
        
    }
    
}
