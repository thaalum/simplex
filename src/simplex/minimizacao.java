/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplex;

/**
 *
 * @author teixe
 */
public class minimizacao {

    public static void main(String[] args) {
        TestesUnitarios.testetransposta();

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
}
