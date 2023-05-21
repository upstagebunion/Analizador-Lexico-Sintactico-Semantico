/********************************************************************
 ******************************************************************** 
 **                            **                                 ***
 **Analizador Lexico Sintáctico**      21 de Mayo de 2023         ***
 **  Semántico y conversor de  **                                 ***
 **  código a notación posfija **                                 ***
 **    y después a CodigoP     **                                 ***
 ********************************************************************
 ********************************************************************
 **   Francisco García Solís   **    Lenguajes y Automatas II     ***
 **Monjaraz Perez Sara Alexandra**      Inst. Tec. de León        ***
 ********************************************************************
 ******************************************************************** 
 ** Programa que analiza un programa de entrada y verifica su     ***
 ** estructura y gramática a traves de un análisis léxico,        ***
 ** sintáctico y semántico, para después convertirlo a notación   ***
 ** PostFija y después a CodigoP (intermedio) y los guarda en     ***
 ** un archivo de Texto.                                          ***
 ********************************************************************
 ******************************************************************** 
 ********************************************************************
 ******************************************************************** 
 **  Software empleado:                                           ***
 **        Java SE Runtime Environment 17.0.6+9-LTS-190           ***
 **        Visual Studio Code                                     ***
 **        Windows 10 Professional                                ***
 **                                                               ***
 **  Sistema:                                                     ***  
 *         AMD Ryzen 5 3400G, 3GHz, 16.0 GB en RAM                ***
 *         Sistema operativo de 64 bits                           ***  
 * ******************************************************************
 * ******************************************************************
*/
package Codigo;
public class Main {
    public static void main(String[] args) {
        LectorArchivo la = new LectorArchivo("Analizador-Lexico-Sintactico-Semantico/src/Docs/programa.txt"); //se Lee el archivo que contiene el programa
        AnalizadorLexico al = la.al; //Se construye el analizador Léxico y se usa el que contiene el lector de archivo
        Display dSim = new Display(al.lsimbolos,"Tabla de Simbolos sin repeticion", false); //Se muestra la primer tabla de variables sin repetición
        Display dEr = new Display(al.lerrores, "Tabla de Errores", false); //Se muestra la tabla de errores
        Display dPal = new Display(al.lpalReservadas, "Tabla de palabras reservadas", false); //Se muestra la tabla de palabras reservadas
        Display d = new Display(al.ltokens, "Tabla de Token Aceptados", false); //Se muestra la tabla de todos los tokens que existen incluye repetidos
        TablaSimbolos ts = new TablaSimbolos(al.ltokens); //Se construye la tabla de Simbolos
        Display simb = new Display(ts.simbolos, "Tabla de Simbolos", true); //Se muestra la tabla de simbolos
        AnalizadorSemantico as = new AnalizadorSemantico(ts.tokens, ts.simbolos); //Se construye y se ejecuta el analisis semántico y las verificaciones de tipos
        Posfija pf = new Posfija(ts.tokens);    //Genera el Código en notación PosFija
        SavePf spf = new SavePf(pf.pf, "Analizador-Lexico-Sintactico-Semantico/src/Docs/posfija.txt"); //Guarda en código en notacion posfija
        GenCodP gcp = new GenCodP(pf.pf); //Genera el codigo P usando la notacion posfija del código
        SavePf scp = new SavePf(gcp.codigoP, "Analizador-Lexico-Sintactico-Semantico/src/Docs/codigoP.txt"); //Guarda el códigoP en u .txt
    }
}