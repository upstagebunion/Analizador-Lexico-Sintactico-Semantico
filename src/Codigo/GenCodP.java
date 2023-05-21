package Codigo;


public class GenCodP {
    // DECLARACION DE VARIABLES
    Listas pf, linea, codigoP;
    Boolean cont;

    public GenCodP(Listas pf){
        // SE INICIAN LAS VARIABLES
        this.pf = pf;
        linea = new Listas();
        codigoP = new Listas();
        //VARIABLE USADA PARA DETENER RECURSIVIDAD EN CÓDIGO
        cont = true;
        //Se llama a la función que genera el código
        Generar();
    }

    public void Generar(){
        //Ciclo. se repetira por cada elemento guardado en la notación posfija
        for (int i = 0; i < pf.CuentaElementos(); i++) {
            //Variables que almacenan el valor y atributo del item actual
            String itemValue = pf.RecorreUno(i)[0];
            int itemAtributo = Integer.parseInt(pf.RecorreUno(i)[1]);
            //si el atributo es un ; determina que ahí acaba la linea y la manda a convertir
            if(itemAtributo == 59){
                linea.agregarAlFinal(itemValue, itemAtributo); //guarda el ; al final de la linea
                cont = true; //se activa la recursividad
                ConvLineaCodP(linea); //llama a la funcion de conversion con la linea actual
                linea.limpiar(); //se limpia la linea ya convertida
            }else{  //si no es el final de la linea
                linea.agregarAlFinal(itemValue, itemAtributo); //se guarda ese item en la linea
            }

        }
    }

    public void ConvLineaCodP(Listas linea){
            //Guarda el primer item de la línea
            String itemValue = linea.RecorreUno(0)[0];
            int itemAtributo = Integer.parseInt(linea.RecorreUno(0)[1]);
            System.out.println("Vamos en: "+itemValue);

            if(itemAtributo == 401){ //si el primer item es "Inicio"
                System.out.println("Se detectó el inicio");
                codigoP.agregarAlFinal(itemValue, itemAtributo);    //Se agrega tal cual a la salida de CodigoP
                codigoP.agregarAlFinal(";", 59);    //se le agrega el final de linea 

            }else if(linea.contieneProd(403) || linea.contieneProd(402)){ //Si encuentra una declaración en la línea actual (palabra reservada)
                System.out.println("Se detectó una declaración");
                for (int j = 0; j < linea.vueltaNeeded; j++) {  //Se cicla por cada valor anterior a la declaración (recordamos lee notacion posfija)
                    String it = linea.RecorreUno(j)[0]; //guarda el valor del item
                    codigoP.agregarAlFinal("MOV "+ it + ", 0", 1);  //Agrega al codigoP la declaración de la variable actual
                    codigoP.agregarAlFinal(";", 59); //Termina la linea en codigoP
                }
                
            }else if(linea.contieneProd(404) || linea.contieneProd(405)){ //Si encuentra una acctión en la línea (Palabra reservada como leectura o escritura)
                System.out.println("Se detectó una acción");
                for (int j = 0; j < linea.vueltaNeeded; j++) {  //Cicla por cada variable anterior a la acción
                    String it = linea.RecorreUno(j)[0]; //Guarda el valor de la variable
                    String orden = linea.RecorreUno(linea.vueltaNeeded)[0]; //Guarda el valor de la acción encontrada
                    codigoP.agregarAlFinal(orden + " " + it, 1);    //Guarda en codigoP la orden y el valor de la variable
                    codigoP.agregarAlFinal(";", 59);    //Termina la línea en codigoP
                }
                
            }else if(itemAtributo == 406){  //Si detecta la palabra Final //ESTO PUEDE SER OPTIMIZADO
                System.out.println("Se detectó el final");
                codigoP.agregarAlFinal(itemValue, itemAtributo); //La agrega directo al codigoP
                codigoP.agregarAlFinal(";", 59);    //Agrega el final de linea
                
            }else{  //Si no fue nada de lo anterior, debe ser una asignación o una operación, o nada
                System.out.println("Entamos en recursividad");
                resolverLinea(linea);   //Se manda la linea a ser resuelta
                
            }
    }

    public void resolverLinea(Listas linea){
        
        Listas temp = new Listas(); //cada vez que se manda a llamar la funcion se crea una lista temp nueva (util para la recursividad)

        for (int i = 0; i < linea.CuentaElementos(); i++) { //Por cada item en la linea
            if(cont){  //Si la recursividad está habilitada
                //Guarda los valores del item actual
                String itemValue = linea.RecorreUno(i)[0];
                int itemAtributo = Integer.parseInt(linea.RecorreUno(i)[1]);
                System.out.println("estamos en: "+itemValue);
                if(itemAtributo < 450){ //Si el atributo del item es menor de 450, significa que no es ni varialbe ni número, por lo que debe ser un operador
                    switch(itemAtributo){
                        case 45:    //En caso de que sea un "-" Resta
                            String op1 = linea.RecorreUno(i-2)[0]; //guarda el valor del operando 1, que se deberia encontrar 2 items atras del operador
                            int op1A = Integer.parseInt(linea.RecorreUno(i-2)[1]); //guarda el atributo del operando 1
                            String op2 = linea.RecorreUno(i-1)[0];  //guarda el operando 2, que deberia de estar justo antes del operador
                            int op2A = Integer.parseInt(linea.RecorreUno(i-1)[1]); //guarda el atributo del operando 2
                            if(op1A == 800 || op2A == 800){ //si el operando 1 o 2 es una variable temporal
                                if(op1A == 800){ //si el operando 1 es la variable temporal
                                    codigoP.agregarAlFinal("SUB "+op1+", "+ op2, 3); //la variable temporal va a la izquierda, pues ahí puede guardarse el valor de la resta
                                    codigoP.agregarAlFinal(";", 59); //se finaliza la linea en codigoP
                                }else{ //si el operando 2 es la variable temporal
                                    codigoP.agregarAlFinal("SUB "+op2+", "+ op1, 3); //la variable temporal va a la izquierda, pues ahí puede guardarse el valor de la resta
                                    codigoP.agregarAlFinal(";", 59); //se finaliza la linea en codigoP
                                }
                            }else{ //si los dos operadondos no son variables temporales
                                codigoP.agregarAlFinal("MOVE temp"+i+", "+ op1, 2); //se crea una variable temporal y se le asigna el valor del primer operando
                                codigoP.agregarAlFinal(";", 59); //se finaliza la linea
                                codigoP.agregarAlFinal("SUB temp"+i+", "+ op2, 4); //se realiza la resta y se guarda en la variable temp
                                codigoP.agregarAlFinal(";", 59);// finaliza la línea
                            }
                            temp.eliminarUltimosDos(); //se borran de la lista temporal los ultimos dos valores (los operandos ya procesados) pues serán reemplazados por una variable temporal que guarda el resultado de la operación
                            if(op1A == 800 || op2A == 800){ //si alguno de los operandos es temporal
                                if(op1A == 800){
                                    temp.agregarAlFinal(op1, 800);  //se agrega esa variable temporal en lugar de los operandos y el operador en la linea (ya procesado)
                                }else{
                                    temp.agregarAlFinal(op2, 800);
                                }
                            }else{ //si no hay variables temporales
                                temp.agregarAlFinal("temp"+i, 800); //se guarda una nueva variable temporal en lugar de los operandos y operador de la linea (ya procesado)
                            }
                            for(int j = i+1; j < linea.CuentaElementos(); j++){ //por cads item en la linea despues del operador ya procesado
                                String iV = linea.RecorreUno(j)[0]; //almacena el valor del item actual
                                int iA = Integer.parseInt(linea.RecorreUno(j)[1]); //almacena el atributo del item actual
                                temp.agregarAlFinal(iV, iA); //los agrega al final de la nueva linea a procesar
                            }
                            resolverLinea(temp); //se manda de forma recursiva la linea resultante despues de ser procesada a procesar el sigueinte operador
                        ;break;
                        case 43: //hace lo mismo que antes, pero con el "+", SUMA
                            String op3 = linea.RecorreUno(i-2)[0];
                            int op3A = Integer.parseInt(linea.RecorreUno(i-2)[1]); 
                            String op4 = linea.RecorreUno(i-1)[0];
                            int op4A = Integer.parseInt(linea.RecorreUno(i-1)[1]); 
                            if(op3A == 800 || op4A == 800){
                                if(op3A == 800){ 
                                    codigoP.agregarAlFinal("ADD "+op3+", "+ op4, 3);
                                    codigoP.agregarAlFinal(";", 59);
                                }else{
                                    codigoP.agregarAlFinal("ADD "+op4+", "+ op3, 3);
                                    codigoP.agregarAlFinal(";", 59);
                                }
                            }else{
                                codigoP.agregarAlFinal("MOVE temp"+i+", "+ op3, 2);
                                codigoP.agregarAlFinal(";", 59);
                                codigoP.agregarAlFinal("ADD temp"+i+", "+ op4, 3);
                                codigoP.agregarAlFinal(";", 59);
                            }
                            temp.eliminarUltimosDos();
                            if(op3A == 800 || op4A == 800){
                                if(op3A == 800){
                                    temp.agregarAlFinal(op3, 800);
                                }else{
                                    temp.agregarAlFinal(op4, 800);
                                }
                            }else{
                                temp.agregarAlFinal("temp"+i, 800);
                            }
                            for(int j = i+1; j < linea.CuentaElementos(); j++){
                                String iV = linea.RecorreUno(j)[0];
                                int iA = Integer.parseInt(linea.RecorreUno(j)[1]);
                                temp.agregarAlFinal(iV, iA);
                            }
                            resolverLinea(temp);
                        ;break;
                        case 47: //Lo mismo pero con la Multiplicación "*"
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
                            codigoP.agregarAlFinal("MOV temp"+i+", eax", 1);
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
                        case 42: //Lo mismo pero con la Division "/"
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
                            codigoP.agregarAlFinal("MOV temp"+i+", eax", 1);
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
                        case 61: //Si es un "="
                            String op9 = linea.RecorreUno(i-2)[0]; //guarda el valor del primer operando (dos lugares atras del =)
                            String op10 = linea.RecorreUno(i-1)[0]; // guarda el valor del segundo operando (un lugar antes del =)
                            codigoP.agregarAlFinal("MOV "+op9+", "+ op10, 1); //guarda la operación de asignación en codigo P
                            codigoP.agregarAlFinal(";", 59); // finaliza la linea en codigoP
                            System.out.println("Si leyo el igual");
                        ;break;
                        default:    //Si no fue nada de lo anterior, es un valor no reconocido o es un ; (final de linea)
                            cont=false; //se cancela la recursividad
                            System.out.println("Se detecto otra cosa");
                            temp.limpiar();//se limpia la linea temporal
                        ;break;
                    }
                }else{
                    temp.agregarAlFinal(itemValue, itemAtributo); //Por cada valor que no sea un operador se guarda el item en la nueva línea generada despues de ser procesada
                }
            }
        }

    }
}
