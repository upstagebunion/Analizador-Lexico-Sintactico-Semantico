package Codigo;

import java.io.IOException;
import java.io.PrintWriter;

public class SavePf {
    String fileName = "src/Docs/posfija.txt";
    String encoding = "UTF-8";

    public SavePf(Listas pf, String dir){
        fileName = dir;
        String temp ="";
        
        try{
            PrintWriter writer = new PrintWriter(fileName, encoding);
            for (int i = 0; i < pf.CuentaElementos(); i++) {

                String itemValue = pf.RecorreUno(i)[0];
                int itemAtributo = Integer.parseInt(pf.RecorreUno(i)[1]);

                if(itemAtributo == 59){
                    //temp += itemValue +"("+itemAtributo+")";
                    temp += itemValue;
                    writer.println(temp);
                    temp = "";
                }else{
                    //temp += itemValue+"("+itemAtributo+")";
                    temp += itemValue;
                }
            }
                        
            writer.close();
        }
        catch (IOException e){
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    
}
