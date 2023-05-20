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

        for(int i = 0; i < c; i++){
            item = tk.getNodo(i);
            if(item.atributo != 59){   
                System.out.println("Se agrega: "+item.dato);             
                temp.agregarAlFinal(item.dato, item.atributo);
            }else{
                System.out.println("fin linea");
                modificarLinea(temp);
                System.out.println("Se agrega ; como marcador de fin linea");
                pf.agregarAlFinal(";", 59);
                temp.limpiar();
                postfija = ""; //Se limpia la linea
            }
        }
    }

    public void modificarLinea(Listas linea){
        for (int i = 0; i < linea.CuentaElementos(); i++) {

            String itemValue = linea.RecorreUno(i)[0];
            int itemAtributo = Integer.parseInt(linea.RecorreUno(i)[1]);

            System.out.println("Vamos en: "+ itemValue);
            // Si el caracter es un operando, se anade a la salida
            if (itemAtributo>=450) {
                System.out.println("Se agregó un operando a la salida");
                pf.agregarAlFinal(itemValue, itemAtributo);
                postfija += itemValue;
            }else if(itemAtributo >= 400 && itemAtributo < 450){ //Si es palabra reservada, se salta la conversion
                boolean cont = false;
                switch(itemAtributo){
                    case 401:
                    case 406:
                        pf.agregarAlFinal(itemValue, itemAtributo);
                    ;break;
                    case 402:
                    case 403:
                    case 404:
                    case 405:
                        cont = true;
                        pila.push(itemValue);
                    ;break;
                }
                if(!cont){ 
                    break;
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
                    pf.agregarAlFinal(pila.peek(), 400);
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
                            pf.agregarAlFinal(pila.peek(), 400);
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
            pf.agregarAlFinal(pila.peek(), 400);
            postfija += pila.pop();
        }
        
        System.out.println(postfija);  // Devuelve la cadena en notacion postfija
    }

    public static int prioridad(String operador) {
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

}
