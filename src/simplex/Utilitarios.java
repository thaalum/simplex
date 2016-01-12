/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author fabiopinto
 */
public class Utilitarios {

    public static int procuraLinhaZ(String[] matrizTemp, int numLinhas) {
        int linhaZ = -1;
        for (int i = 0; i < numLinhas; i++) {
            String linha = matrizTemp[i];
            Pattern encontraZ = Pattern.compile("([zZ])");
            Matcher mZ = encontraZ.matcher(linha);
            if (mZ.find()) {
                linhaZ = i;
            }
        }
        System.out.println(linhaZ);
        return linhaZ;
    }

    public static int procuraNumeroVariaveis(String expressao) {
        int numVariaveis = -1;
        Pattern encontraValores = Pattern.compile("([-]?\\d*[.]?\\d{1,2}[\\/]?\\d*[xX]\\d*)");
        Matcher mValores = encontraValores.matcher(expressao);
        while (mValores.find()) {
            String valor = mValores.group();
            int posicaoX = (valor.indexOf("x"));
            if ((Integer.parseInt(valor.substring(posicaoX + 1, valor.length()))) > numVariaveis) {
                numVariaveis = Integer.parseInt(valor.substring(posicaoX + 1, valor.length()));
            }
        }
        return numVariaveis;
    }
}
