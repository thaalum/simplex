package simplex;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Simplex {

    public static String nomeFicheiroEntrada;
    public static String nomeFicheiroSaida;
    public static int numLinhasMatriz;
    public static int numColunasMatriz;
    public static String[][] matriz;
    public static void main(String[] args) throws FileNotFoundException {
        TestesUnitarios.executarTestes();
        nomeFicheiroEntrada = args[0];
        nomeFicheiroSaida = args[1];
        int numLinhasFicheiro = retornaNumLinhasFicheiro(nomeFicheiroEntrada);
        numColunasMatriz = numLinhasFicheiro + 3;
        numLinhasMatriz = numLinhasFicheiro;
        System.out.println(numColunasMatriz + " colunas");
        System.out.println(numLinhasMatriz + " linhas");
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
    public static int[] procurarVariavelSaida(int numeroLinhas, int [][]matriz, int colunaPivot){
    int pivot []= new int [3];
    pivot[3]= colunaPivot;
    for (int i=0; i<numeroLinhas-1; i++){
        double temp=((matriz[i][numeroLinhas+1])/(matriz[i][colunaPivot]));
        for (int j=i+1; j<numeroLinhas; j++){      
            double aux= ((matriz[j][numeroLinhas+1])/(matriz[j][colunaPivot]));
           
       if(temp > 0 && aux>0){
               if (temp < aux){
                   pivot[1]=i;
                   pivot[0]=matriz[i][colunaPivot];
               }else { 
                    pivot[1]=j;
                   pivot[0]=matriz[j][colunaPivot];
               }
           } else { if(temp>0){
                pivot[1]=i;
                pivot[0]=matriz[i][colunaPivot];    
       } if (aux >0){
                   pivot[1]=j;
                   pivot[0]=matriz[j][colunaPivot];
       }     
    } 
    }    
  } 
   return pivot;   
}
    
    
 public static void dividirLinhaPivot(int [] pivot, int numeroLinhas, int [][] matriz){
     int linhaPivot =pivot[1];
     for (int i=0; i<numeroLinhas+2;i++){
         matriz[linhaPivot][i]=(matriz[linhaPivot][i]/pivot[0]);        
     }     
 }
 
}
