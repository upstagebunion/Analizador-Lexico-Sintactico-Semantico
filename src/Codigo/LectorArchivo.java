
package Codigo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JOptionPane;

public class LectorArchivo {
    AnalizadorLexico al;
    String completo;
    
    LectorArchivo(String file){
        al = new AnalizadorLexico();    //Se crea la clase del analizador lexico y se construye
        completo = "";  //Variable temporal que guarda para su impresion el programa leido
        abrirtxt(file); //Se manda a llamar la funcion para leer el archivo
    }
    
    public void abrirtxt(String file) {
        String aux = ""; //Variable temporal que guarda el contenido del documento leído línea por línea
        try {
            FileReader archivos = new FileReader(file); //Se abre el archivo
            try (BufferedReader lee = new BufferedReader(archivos)) { //Se lee y guarda el archivo
                while ((aux = lee.readLine()) != null) {    //Mientras la línea tenga contenido, se guarda la linea en aux
                    al.inicia(aux);     //La Línea guardada en auxiliar se manda al analizador para procesarse
                    completo = completo + aux + "\n"; //Se guarda cada linea en la variable completo
                }
                lee.close(); //Se cierra el archivo
            }
        } catch (IOException ex) { //Excepcion por fallos al abrir el archivo
            JOptionPane.showMessageDialog(null, ex + "No se ha encontrado el archivo","ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        }
    }
}
