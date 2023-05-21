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
            PrintWriter writer = new PrintWriter(fileName, encoding);   //Crea el archivo, si ya existe lo reescribirá
            for (int i = 0; i < pf.CuentaElementos(); i++) { //por cada valor de la lista pasada

                String itemValue = pf.RecorreUno(i)[0]; //se guarda el valor del item
                int itemAtributo = Integer.parseInt(pf.RecorreUno(i)[1]);

                if(itemAtributo == 59){ //Si es un ;, es final de linea, por lo que se debe imprimir como una linea nueva en el archivo
                    //temp += itemValue +"("+itemAtributo+")";
                    temp += itemValue; // guarda el ; en la linea
                    writer.println(temp); //crea la nueva linea en el archivo
                    temp = ""; //se limpia la variable temporal, o la línea ya guardada
                }else{ //si no es el fin de la linea
                    //temp += itemValue+"("+itemAtributo+")";
                    temp += itemValue; //guarda el item en la linea actual
                }
            }
                        
            writer.close(); //cierra el archivo
        }
        catch (IOException e){
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    
}
