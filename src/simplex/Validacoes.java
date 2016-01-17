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
public class Validacoes {

    public static boolean verificaEspacosEmBranco(String matriz[], int numLinhasMatriz) {
        boolean temEspacos = false;
        for (int i = 0; i < numLinhasMatriz; i++) {
            String linha = matriz[i];
            System.out.println("linha: " + linha);
            Pattern encontraEspacos = Pattern.compile("(\\s{3})");
            Matcher mEspacos = encontraEspacos.matcher(linha);
            if (mEspacos.find()) {
                temEspacos = true;
            }
        }
        return temEspacos;
    }

    public static boolean verificaCasasDecimais(String matriz[], int numLinhasMatriz) {
        boolean temCasasDecimais = false;
        for (int i = 0; i < numLinhasMatriz; i++) {
            String linha = matriz[i];
            Pattern encontraDecimais = Pattern.compile("([\\.]\\d{3})");
            Matcher mDecimais = encontraDecimais.matcher(linha);
            if (mDecimais.find()) {
                temCasasDecimais = true;
            }
        }
        return temCasasDecimais;
    }

    public static boolean verificaFuncaoRestricao(String matriz[], int numLinhasMatriz) {
        boolean temFuncaoRestricao = true;
        for (int i = 0; i < numLinhasMatriz; i++) {
            String linha = matriz[i];
            Pattern encontraRestricao = Pattern.compile("([xX]\\d*[.]*\\d*[<>][=][-]?\\d*[.]?\\d*?)");
            Matcher mRestricao = encontraRestricao.matcher(linha);
            Pattern encontraFuncao = Pattern.compile("([zZ][=][-]?\\d*[\\.]?\\d*?[xX]\\d*)");
            Matcher mFuncao = encontraFuncao.matcher(linha);
            boolean funcao = mFuncao.find();
            boolean restricao = mRestricao.find();
            System.out.println("Funcao: " + funcao + " Restricao: " + restricao);
            if (!funcao && !restricao) {
                temFuncaoRestricao = false;
                i = numLinhasMatriz;
            }
        }
        return temFuncaoRestricao;
    }
    
    
    //Devolve 1 se for Maximização, 0 se for Minimização
    public static int decideMaxMin(String matriz[], int numLinhasMatriz){
        int maxMin=-1;
        boolean max=false;
        boolean min=false;
        for (int i = 0; i < numLinhasMatriz; i++) {
            String linha = matriz[i];
            Pattern encontraMaior = Pattern.compile("([.]?[>][=])");
            Matcher mMaior = encontraMaior.matcher(linha);
            Pattern encontraMenor = Pattern.compile("([.]?[<][=])");
            Matcher mMenor = encontraMenor.matcher(linha);
            if(mMaior.find()){
                max=true;
                maxMin=1;
            }
            else if(mMenor.find()){
                min=true;
                maxMin=0;
            }
        }
        if (max==min){
            System.out.println("Existem restrições erradas! (sinais >= ou <=)");
            maxMin=-1;
        }
        return maxMin;
    }
}
