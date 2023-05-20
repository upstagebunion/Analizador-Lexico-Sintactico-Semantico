package Codigo;

import java.io.IOException;
import java.io.PrintWriter;

public class SavePf {
    String fileName = "../Docs/postfija.txt";
    String encoding = "UTF-8";

    public SavePf(Listas pf){
        
        try{
            PrintWriter writer = new PrintWriter(fileName, encoding);
            for (int i = 0; i < pf.CuentaElementos(); i++) {

                String itemValue = pf.RecorreUno(i)[0];
                int itemAtributo = Integer.parseInt(pf.RecorreUno(i)[1]);

                writer.println(itemValue+"("+itemAtributo+")");
            }            
            writer.close();
        }
        catch (IOException e){
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    
}
