package Codigo;

import java.util.ArrayList;

public class GenCodP {
    Listas pf, linea, codigoP;
    Boolean cont;

    public GenCodP(Listas pf){
        this.pf = pf;
        linea = new Listas();
        codigoP = new Listas();
        cont = true;
        Generar();
    }

    public void Generar(){
        for (int i = 0; i < pf.CuentaElementos(); i++) {

            String itemValue = pf.RecorreUno(i)[0];
            int itemAtributo = Integer.parseInt(pf.RecorreUno(i)[1]);

            if(itemAtributo == 59){
                linea.agregarAlFinal(itemValue, itemAtributo);
                cont = true;
                ConvLineaCodP(linea);
                linea.limpiar();
            }else{
                linea.agregarAlFinal(itemValue, itemAtributo);
            }

        }
    }

    public void ConvLineaCodP(Listas linea){
        for (int i = 0; i < linea.CuentaElementos(); i++) {
            String itemValue = linea.RecorreUno(i)[0];
            int itemAtributo = Integer.parseInt(linea.RecorreUno(i)[1]);

            if(itemAtributo == 401){
                codigoP.agregarAlFinal(itemValue, itemAtributo);
                codigoP.agregarAlFinal(";", 59);
                break;
            }else if(linea.contieneProd(403) || linea.contieneProd(402)){ //Si es una declaración
                for (int j = 0; j < linea.vueltaNeeded; j++) {
                    String it = linea.RecorreUno(j)[0];
                    codigoP.agregarAlFinal("MOV "+ it + ", 0", 1);
                    codigoP.agregarAlFinal(";", 59);
                }
                break;
            }else if(linea.contieneProd(404) || linea.contieneProd(405)){
                for (int j = 0; j < linea.vueltaNeeded; j++) {
                    String it = linea.RecorreUno(j)[0];
                    String orden = linea.RecorreUno(linea.vueltaNeeded)[0];
                    codigoP.agregarAlFinal(orden + " " + it, 1);
                    codigoP.agregarAlFinal(";", 59);
                }
                break;
            }else if(itemAtributo == 406){
                codigoP.agregarAlFinal(itemValue, itemAtributo);
                codigoP.agregarAlFinal(";", 59);
                break;
            }else{
                resolverLinea(linea);
                break;
            }
        }
    }

    public void resolverLinea(Listas linea){
        if(cont){ 
        Listas temp = new Listas();

        for (int i = 0; i < linea.CuentaElementos(); i++) {
            String itemValue = linea.RecorreUno(i)[0];
            int itemAtributo = Integer.parseInt(linea.RecorreUno(i)[1]);
            System.out.println("estamos en: "+itemValue);
            if(itemAtributo < 500){
                switch(itemAtributo){
                    case 45:
                        String op1 = linea.RecorreUno(i-2)[0];
                        int op1A = Integer.parseInt(linea.RecorreUno(i-2)[1]); 
                        String op2 = linea.RecorreUno(i-1)[0];
                        int op2A = Integer.parseInt(linea.RecorreUno(i-1)[1]); 
                        if(op1A == 800 || op2A == 800){
                            codigoP.agregarAlFinal("SUB "+op1+", "+ op2, 3);
                            codigoP.agregarAlFinal(";", 59);
                        }else{
                            codigoP.agregarAlFinal("MOVE temp, "+ op1, 2);
                            codigoP.agregarAlFinal(";", 59);
                            codigoP.agregarAlFinal("SUB temp, "+ op2, 4);
                            codigoP.agregarAlFinal(";", 59);
                        }
                        temp.eliminarUltimosDos();
                        temp.agregarAlFinal("temp"+i, 800);
                        for(int j = i+1; j < linea.CuentaElementos(); j++){
                            String iV = linea.RecorreUno(j)[0];
                            int iA = Integer.parseInt(linea.RecorreUno(j)[1]);
                            temp.agregarAlFinal(iV, iA);
                        }
                        resolverLinea(temp);
                    ;break;
                    case 43:
                        String op3 = linea.RecorreUno(i-2)[0];
                        int op3A = Integer.parseInt(linea.RecorreUno(i-2)[1]); 
                        String op4 = linea.RecorreUno(i-1)[0];
                        int op4A = Integer.parseInt(linea.RecorreUno(i-1)[1]); 
                        if(op3A == 800 || op4A == 800){
                            codigoP.agregarAlFinal("ADD "+op3+", "+ op4, 3);
                            codigoP.agregarAlFinal(";", 59);
                        }else{
                            codigoP.agregarAlFinal("MOVE temp, "+ op3, 2);
                            codigoP.agregarAlFinal(";", 59);
                            codigoP.agregarAlFinal("ADD temp, "+ op4, 3);
                            codigoP.agregarAlFinal(";", 59);
                        }
                        temp.eliminarUltimosDos();
                        temp.agregarAlFinal("temp"+i, 800);
                        for(int j = i+1; j < linea.CuentaElementos(); j++){
                            String iV = linea.RecorreUno(j)[0];
                            int iA = Integer.parseInt(linea.RecorreUno(j)[1]);
                            temp.agregarAlFinal(iV, iA);
                        }
                        resolverLinea(temp);
                    ;break;
                    case 47:
                        String op5 = linea.RecorreUno(i-2)[0];
                        String op6 = linea.RecorreUno(i-1)[0];
                        codigoP.agregarAlFinal("XOR eax, eax", 10);
                        codigoP.agregarAlFinal(";", 59);
                        codigoP.agregarAlFinal("MOVE edx, "+ op5, 1);
                        codigoP.agregarAlFinal(";", 59);
                        codigoP.agregarAlFinal("MOVE ecx, "+ op6, 1);
                        codigoP.agregarAlFinal(";", 59);
                        codigoP.agregarAlFinal("DIV ecx", 5);
                        codigoP.agregarAlFinal(";", 59);
                        codigoP.agregarAlFinal("MOV temp, eax", 1);
                        codigoP.agregarAlFinal(";", 59);
                        temp.eliminarUltimosDos();
                        temp.agregarAlFinal("temp"+i, 800);
                        for(int j = i+1; j < linea.CuentaElementos(); j++){
                            String iV = linea.RecorreUno(j)[0];
                            int iA = Integer.parseInt(linea.RecorreUno(j)[1]);
                            temp.agregarAlFinal(iV, iA);
                        }
                        resolverLinea(temp);
                    ;break;
                    case 42:
                        String op7 = linea.RecorreUno(i-2)[0];
                        String op8 = linea.RecorreUno(i-1)[0];
                        codigoP.agregarAlFinal("XOR eax, eax", 10);
                        codigoP.agregarAlFinal(";", 59);
                        codigoP.agregarAlFinal("MOVE edx, "+ op7, 1);
                        codigoP.agregarAlFinal(";", 59);
                        codigoP.agregarAlFinal("MOVE ecx, "+ op8, 1);
                        codigoP.agregarAlFinal(";", 59);
                        codigoP.agregarAlFinal("MUL ecx", 6);
                        codigoP.agregarAlFinal(";", 59);
                        codigoP.agregarAlFinal("MOV temp, eax", 1);
                        codigoP.agregarAlFinal(";", 59);
                        temp.eliminarUltimosDos();
                        temp.agregarAlFinal("temp"+i, 800);
                        for(int j = i+1; j < linea.CuentaElementos(); j++){
                            String iV = linea.RecorreUno(j)[0];
                            int iA = Integer.parseInt(linea.RecorreUno(j)[1]);
                            temp.agregarAlFinal(iV, iA);
                        }
                        resolverLinea(temp);
                    ;break;
                    case 61:
                        String op9 = linea.RecorreUno(i-2)[0];
                        String op10 = linea.RecorreUno(i-1)[0];
                        codigoP.agregarAlFinal("MOV "+op9+", "+ op10, 1);
                        codigoP.agregarAlFinal(";", 59);
                        System.out.println("Si leyo el igual");
                        temp.limpiar();
                        cont = false;
                    ;break;
                }
            }else{
                temp.agregarAlFinal(itemValue, itemAtributo);
            }
        }
        }else{
            
        }

    }
}