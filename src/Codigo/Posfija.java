package Codigo;

import java.util.Stack;

public class Posfija {

    Listas tk;
    String postfija;
    Stack<String> pila;  // Pila para almacenar los operadores

    public Posfija(Listas tk){
        this.tk = tk;
        pila = new Stack<>();
        dividir();
    }

    public void dividir(){
        int c = tk.CuentaElementos(); //Se cuentan los elementos de la lista de tokens
        Nodo item;
        Listas temp = new Listas();

        for(int i = 0; i < c; i++){
            item = tk.getNodo(i);
            if(item.atributo != 59){                
                temp.agregarAlFinal(item.dato, item.atributo);
            }else{
                modificarLinea(temp);
                temp.limpiar();
            }
        }
    }

    public void modificarLinea(Listas linea){
        for (int i = 0; i < linea.CuentaElementos(); i++) {
            System.out.println("Vamos en: "+ linea.RecorreUno(i)[0]);
            // Si el caracter es un operando, se anade a la salida
            if (Integer.parseInt(linea.RecorreUno(i)[1])>=400) {
                postfija += linea.RecorreUno(i)[0];
            }
            
            // Si el caracter es un parentesis de apertura, se anade a la pila
            else if (Integer.parseInt(linea.RecorreUno(i)[1])==40) {
                pila.push(linea.RecorreUno(i)[0]);
            }
            
            // Si el caracter es un parentesis de cierre, se sacan los operadores de la pila y se anaden a la salida
            // hasta encontrar el parentesis de apertura correspondiente
            else if (Integer.parseInt(linea.RecorreUno(i)[1])==41) {
                while (!pila.isEmpty() && pila.peek() != "(") {
                    postfija+= pila.pop();
                }
                if (!pila.isEmpty() && pila.peek() == "(") {
                    pila.pop();  // saca el parentesis de apertura de la pila
                }
            }
            
            // Si el caracter es un operador, se sacan los operadores de la pila y se anaden a la salida
            // mientras tengan mayor o igual prioridad que el operador actual
            else {
                switch(Integer.parseInt(linea.RecorreUno(i)[1])){
                    case 61:
                    case 43:
                    case 45:
                    case 47:
                        while (!pila.isEmpty() && prioridad(pila.peek()) >= prioridad(linea.RecorreUno(i)[0])) {
                            postfija += pila.pop();
                        }
                        pila.push(linea.RecorreUno(i)[0]);  // Anade el operador actual a la pila
                    ;break;
                }
                
            }
        }
        
        // Saca los operadores restantes de la pila y los anade a la salida
        while (!pila.isEmpty()) {
            postfija += pila.pop();
        }
        
        System.out.println(postfija);  // Devuelve la cadena en notacion postfija
    }

    public static int prioridad(String operador) {
        switch (operador) {
            case "+":
            case "-":
                return 1;
            case "*":
            case "/":
                return 2;
            case "^":
                return 3;
            default:
                return -1;
        }
    }

}
