package Codigo;

public class TablaSimbolos{
Listas tokens, simbolos;
boolean declaracion;
String tipoDato;

//CLASE QUE GENERA LA TABLA DE SIMBOLOS

    public TablaSimbolos(Listas t){
        tokens = t; //Declara y asigna la lista de tokens que se generó antes
        simbolos = new Listas();
        declaracion = false; //Variable para saber si se encontro una declaración de variables
        GenerarLista(); //Se genera la lista
    }

    public void GenerarLista(){
        int c = tokens.CuentaElementos();  //cuenta los elementos guardados en la lista de tokens
        Nodo item;  //variable temrporal para guardar el token que se analiza actualmente 
        for(int i = 0; i < c; i++){ //Ciclo que itera sobre los elementos de la lista tokens

            item = tokens.getNodo(i); //Obtiene el token en el cual se encuentra el ciclo

            if(item.atributo == 402){ //Si el token leido es la palabra reservada Entero
                tipoDato = "Entero"; //Guarda el tipo de dato como un Entero
                declaracion = true; //Se marca que se encuentran los siguientes tokens bajo una declaración de variable
            }else if(item.atributo == 403){
                tipoDato = "Real";
                declaracion = true;
            }else if(item.atributo == 59){ //Si el token leído es un ; se deecta el final de las declaraciones
                declaracion = false; //La variable sale de la declaración de variables
            }

            if(item.atributo == 450){ //si el token es un numero entero
                System.out.println("es Numero: "+ item.dato);
                simbolos.agregarAlFinalS(item.dato, item.atributo, item.repeticiones, item.linea, item.dato);//Se agrega el token a la lista de simbolos
                int w = simbolos.CuentaElementos()-1;//Variable temporal que cuenta los tokens en la lista de simbolos
                Nodo temp = simbolos.getNodo(w); //Se guarda el valor que acabamos de agregar
                temp.tipoDato = "Entero"; //Se le asigna el valor de entero a su tipo de datos
            }else if (item.atributo == 451){ //Si el token es un numero Real
                simbolos.agregarAlFinalS(item.dato, item.atributo, item.repeticiones, item.linea, item.dato);
                int w = simbolos.CuentaElementos()-1;
                Nodo temp = simbolos.getNodo(w);
                temp.tipoDato = "Real";
            }else if(item.atributo >= 500 && item.atributo < 800){ //Si el token que se lee es un identificador o variable
                System.out.println("Vamos en: " + item.dato);
                if(simbolos.contieneProd(item.atributo)){ //Si la tabla de simbolos ya contiene este atributo
                    System.out.println("Ya existia, atributo: " + item.atributo);
                    simbolos.agregarAlFinal(item.dato, item.atributo, item.repeticiones, item.linea); //se agrega el token a la tabla de simbolos
                    int w = simbolos.CuentaElementos()-1; //Se obtiene el token que acabamos de agregar pero en la lista de simbolos
                    Nodo temp = simbolos.getNodo(w); //Se guarda el nodo
                    System.out.println("Nodo al que se le da valor: "+ temp.dato);
                    System.out.println("Nodo del que se saca el valor: " + simbolos.getNodo(simbolos.vueltaNeeded).dato);
                    tipoDato = simbolos.getNodo(simbolos.vueltaNeeded).tipoDato; //Se guarda el tipo de dato de el token que ya se habia agregado antes a la tabla de simbolos
                    System.out.println("Valor a darle: " + tipoDato);
                    temp.tipoDato = tipoDato; //Se le asigna el valor de tipo de dato del token que ya existia en la tabla al mismo token pero en la ultima repeticion en la que se agregó
                }else{ //En caso de que no existiese el token ya en la lista de simbolos
                    simbolos.agregarAlFinal(item.dato, item.atributo, item.repeticiones, item.linea); //se agrega el token actual a la lista de simbolos
                    if (declaracion){ //Si estamos dentro de una declaracionde variable
                        int w = simbolos.CuentaElementos()-1; //Se obtiene el token que acabamos de obtener en la lista de simbolos
                        Nodo temp = simbolos.getNodo(w);
                        temp.tipoDato = tipoDato; //Se le asigna el tipo de dato que se guardo en la variable del inicio de la declaración
                    }
                }
            }
        }
    }
}
