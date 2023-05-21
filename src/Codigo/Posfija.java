package Codigo;

import java.util.Stack;

public class Posfija {

    Listas tk, pf;
    String postfija;
    Stack<String> pila;  // Pila para almacenar los operadores

    public Posfija(Listas tk){
        this.tk = tk;
        pf = new Listas();
        pila = new Stack<>();
        postfija = "";
        dividir();        
    }

    public void dividir(){
        int c = tk.CuentaElementos(); //Se cuentan los elementos de la lista de tokens
        Nodo item;
        Listas temp = new Listas();

        for(int i = 0; i < c; i++){ //Por cada elemento en la lista de tokens
            item = tk.getNodo(i); //se guarda el nodo en el item actual
            if(item.atributo != 59){   //si no es final de linea ";"
                System.out.println("Se agrega: "+item.dato);
                if (item.atributo == 406){ //si la palabra reservada Fin
                    temp.agregarAlFinal(item.dato, item.atributo); //Se guarda en una lista temporal (de la linea actual)
                    modificarLinea(temp); //se pasa la lista (linea actual) a ser modificada a posfija
                    pf.agregarAlFinal(";", 59); //Se finaliza la linea en la notacion posfija
                    temp.limpiar(); //se limpia la linea despues de ser procesada
                    postfija = ""; //Se limpia la linea (usada para debbug)
                }else if(item.atributo == 401){ //si es la palabra Inicio, hace lo mismo que en fin
                    temp.agregarAlFinal(item.dato, item.atributo);
                    modificarLinea(temp);
                    pf.agregarAlFinal(";", 59);
                    temp.limpiar();
                    postfija = ""; //Se limpia la linea
                }else{ //si la palabra no es ninguna de las reservadas anteriores
                    temp.agregarAlFinal(item.dato, item.atributo); //se agrega a la linea actual
                }
                
            }else{ //Si es el fin de linea
                System.out.println("fin linea");
                modificarLinea(temp); //se manda la linea a ser convertida a Posfija
                System.out.println("Se agrega ; como marcador de fin linea");
                pf.agregarAlFinal(";", 59); //se finaliza la linea en la notacion posfija
                temp.limpiar(); //se limpia la linea
                postfija = ""; //Se limpia la linea (debbug)
            }
        }
    }

    public void modificarLinea(Listas linea){
        for (int i = 0; i < linea.CuentaElementos(); i++) { //por cada elemento de la linea
            //se guardan sus valores
            String itemValue = linea.RecorreUno(i)[0];
            int itemAtributo = Integer.parseInt(linea.RecorreUno(i)[1]);

            System.out.println("Vamos en: "+ itemValue);
            // Si el caracter es un operando, se anade a la salida
            if (itemAtributo>=450) {
                System.out.println("Se agregó un operando a la salida");
                pf.agregarAlFinal(itemValue, itemAtributo); //se agrega directo a la salida de posfija
                postfija += itemValue; //linea de resultado de debbug
            }else if(itemAtributo >= 400 && itemAtributo < 450){ //Si es palabra reservada, se salta la conversion
                boolean cont = false; //se cancela el continuar la iteraciond e la linea
                switch(itemAtributo){
                    case 401:
                    case 406:
                        pf.agregarAlFinal(itemValue, itemAtributo); //se guarda la palabra en la notacion posfija (Fin o Inicio)
                    ;break;
                    case 402:
                    case 403:
                    case 404:
                    case 405: //si la palabra es otra reservada
                        cont = true; //si continua con el ciclo
                        pila.push(itemValue); //mete la palabra a la pila, como operador (sirve para poner la palabra de accion al final de todas las varialbes)
                    ;break;
                }
                if(!cont){ 
                    break; //se rompe el ciclado
                }
            }else if (itemAtributo==40) { // Si el caracter es un parentesis de apertura, se anade a la pila
                System.out.println("Se mando un ( a la pila");
                pila.push(itemValue);
            }
            
            // Si el caracter es un parentesis de cierre, se sacan los operadores de la pila y se anaden a la salida
            // hasta encontrar el parentesis de apertura correspondiente
            else if (itemAtributo==41) {
                while (!pila.isEmpty() && !pila.peek().equals("(")) {
                    System.out.println("Se saca: "+ pila.peek()+" de la pila y se agrega a salida");
                    pf.agregarAlFinal(pila.peek(), DevAtri(pila.peek()));
                    postfija += pila.pop();
                }
                if (!pila.isEmpty() && pila.peek().equals("(")) {
                    System.out.println("Se saca el parentesis de apertura");
                    pila.pop();  // saca el parentesis de apertura de la pila
                }
            }
            
            // Si el caracter es un operador, se sacan los operadores de la pila y se anaden a la salida
            // mientras tengan mayor o igual prioridad que el operador actual
            else {
                switch(itemAtributo){
                    case 61:
                    case 43:
                    case 45:
                    case 47:
                    case 42:
                        System.out.println("Se detectó un operador");
                        while (!pila.isEmpty() && prioridad(pila.peek()) >= prioridad(itemValue)) {
                            System.out.println("se sacará: "+ pila.peek()+" y se agrega a salida");
                            pf.agregarAlFinal(pila.peek(), DevAtri(pila.peek()));
                            postfija += pila.pop();
                        }
                        System.out.println("Se metio: "+itemValue +" a la pila");
                        pila.push(itemValue);  // Anade el operador actual a la pila
                    ;break;
                }
                
            }
        }
        
        // Saca los operadores restantes de la pila y los anade a la salida
        while (!pila.isEmpty()) {
            System.out.println("Se saca: "+ pila.peek()+" de la pila y se agrega a salida");
            pf.agregarAlFinal(pila.peek(), DevAtri(pila.peek()));
            postfija += pila.pop();
        }
        
        System.out.println(postfija);  // Devuelve la cadena en notacion postfija
    }

    public static int prioridad(String operador) { //funcion controladora de prioridades, recibe el operador y devuelve su prioridad
        switch (operador) {
            case "(":
                return 0;
            case "+":
            case "-":
                System.out.println("prioridad de 1");
                return 1;
            case "*":
            case "/":
                System.out.println("prioridad de 2");
                return 2;
            case "^":
                return 3;
            default:
                return -1;
        }
    }

    public int DevAtri(String value){ //Funcion controladora de Atributos, entra el valor y sale su atributo correspondiente
        switch(value){
            case "Real":
                return 403;
            case "Leer":
                return 404;
            case "Escribir":
                return 405; 
            case "-":
                return 45;
            case "+":
                return 43;
            case "/":
                return 47;
            case "*":
                return 42;
            case "=":
                return 61;
            case "Entero":
                return 402;
            default:
                return 400;
        }
    }

}
