/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplex;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author fabiopinto
 */
public class Simplex {
    public static void main(String[] args) {
        Scanner ler = new Scanner(System.in);
        lerFicheiro(ler);
    }
    
    teste
    public static String[] lerFicheiro(String ficheiro) throws FileNotFoundException {
        Scanner ler = new Scanner(new File(ficheiro));
        int numeroLinhas = 0;
        while (ler.hasNextLine() && numeroLinhas <= 230) {
            String aux = ler.nextLine();
            if (!aux.isEmpty()) {
                numeroLinhas++;
            }
        }
        

    }
}
