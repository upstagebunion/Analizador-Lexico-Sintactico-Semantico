package Codigo;

public class AnalizadorSemantico{

    Listas tokens, simbolos, temporal;
    boolean cont;

    public AnalizadorSemantico(Listas tk, Listas sm){
        tokens = tk; //se guarda la lista de tokens
        simbolos = sm; //Se guarda la lista de simbolos
        temporal = new Listas(); //Se crea una lista temporal que guardará los tokens de las variables que se encuentren en la linea
        cont = true; //Variable de continuidad
        Inicia(); //Se inicia la funcion de validación de tipos
    }

    public void Inicia(){
        int c = tokens.CuentaElementos(); //Se cuentan los elementos de la lista de tokens
        Nodo item;
        boolean variable = false, habComp = false; //Variables para comprobar si ya se encontro una variable y otra que habilita la comparación de tipos entre variables
        for(int i = 0; i < c; i++){
            if(cont){
                item = tokens.getNodo(i);

                if(item.descripcion.equals("Identificador") && variable != true){ //Si el token actual es una variable y no se ha encntrado una variable antes
                    variable = true; //Ya se encontro una variable
                    simbolos.contiene(item.dato); //comprueba si la lista de simbolos contiene el token
                    temporal.agregarAlFinal(item.dato, item.atributo, simbolos.getNodo(simbolos.vueltaNeeded).tipoDato); //Se agrega en la lista temporal la variable y su tipo de dato
                }

                if(variable && item.atributo == 61){ //Si ya se encontro una variable y el token actual es "=" entonces las variables que siguen deben ser del mismo tipo
                    habComp = true; //Se habilita la comprobacion de los tokens en la variable temporal, lo que es la linea
                }

                if(habComp && item.atributo >= 450 && item.atributo < 800){ //Si esta habilitada la comprobacion y el token es una variable
                    simbolos.contiene(item.dato); //Se compruba si el token actual existe en la talblade simbolos
                    temporal.agregarAlFinal(item.dato, item.atributo, simbolos.getNodo(simbolos.vueltaNeeded).tipoDato); //Se guarda el token actual con el valor de su tipo de dato
                }else if(item.atributo == 59){ //Si se encuentra un ";" se termino la linea y se verifica que todo lo que se ecuentra en la lista temporal sea compatible en tipo de dato
                    habComp = false; //Se vuelve a desactivar la comprobacion
                    variable = false; //Se resetea el haber encontrado una variable
                    Comparar(item.linea); //Se manda a comparar todos los valores en temporal y la linea en al que estamos analizando
                    System.out.println("Final de Verificacion en linea: " + item.linea);
                    temporal.limpiar(); //Se limpia la variable temporal para continuar a la linea que sigue
                }
            }
            else{
                break; //Si no se continua se rompe el ciclo y para la verificación
            }

        }
    }

    public void Comparar(int linea){
        int x = temporal.CuentaElementos(); //Se cuentan los elementos en la temporal
        System.out.println("Elemntos en lista para verificar: " + x);
        Nodo itemAct, siguiente; //Variables que guardan los tokens actual y el que sigue para compararlos

        for(int i = 0; i < x; i++){ //se itera en los elementos de la lista temporal
            System.out.println("Iteración: " + i);
            System.out.println(temporal.getNodo(i).dato);
            if(cont){ 
                itemAct = temporal.getNodo(i); //se guarda el nodo actual
                System.out.println(itemAct.tipoDato);
                if(i + 1 < x){  //Si aun existe un nodo siguiente
                    siguiente = temporal.getNodo(i+1); //se guarda el token que sigue
                }else{
                    break; //si no existe otro nodo mas se rompe el ciclo y termina la comparación
                }

                if(itemAct.tipoDato.equals(siguiente.tipoDato)){ //se verifica si el tipo de dato del token actual es igual (Compatible) con el siguiente token
                    System.out.println("Tipo de dato compatibles"); //Son compatibles
                }else{ //No fueron compatibles 
                    //Se imprime el problema de compatiblidad y la linea en donde sucedio
                    System.out.println("Error Semantico, tipo de datos no compatibles: " + itemAct.dato+"("+itemAct.tipoDato+")" + " con: " + siguiente.dato+"("+siguiente.tipoDato+") En Línea: " + linea);
                    cont = false;  //se para la continuacion del programa de verificacion de datos
                    break; //Se rompe el ciclo y sale
                }
            }
        }
    }

}