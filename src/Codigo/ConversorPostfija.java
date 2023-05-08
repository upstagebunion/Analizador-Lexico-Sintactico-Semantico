package Codigo;

import java.util.*;

public class ConversorPostfija {

    //Conversor de infija a postfija
    public static String convertirInfijaPostfija(String infija) {
        String postfija = ""; //Declaración de la variable de salida

        Stack<Character> pila = new Stack<>();  // Pila para almacenar los operadores

        infija = infija.replaceAll(" ", ""); //Quita los espacios de la notacion de entrada

        // Recorre cada caracter de la expresion infija
        for (int i = 0; i < infija.length(); i++) {
            char c = infija.charAt(i);
            
            // Si el caracter es un operando, se anade a la salida
            if (Character.isLetterOrDigit(c)) {
                postfija += c;
            }
            
            // Si el caracter es un parentesis de apertura, se anade a la pila
            else if (c == '(') {
                pila.push(c);
            }
            
            // Si el caracter es un parentesis de cierre, se sacan los operadores de la pila y se anaden a la salida
            // hasta encontrar el parentesis de apertura correspondiente
            else if (c == ')') {
                while (!pila.isEmpty() && pila.peek() != '(') {
                    postfija+= pila.pop();
                }
                if (!pila.isEmpty() && pila.peek() == '(') {
                    pila.pop();  // saca el parentesis de apertura de la pila
                }
            }
            
            // Si el caracter es un operador, se sacan los operadores de la pila y se anaden a la salida
            // mientras tengan mayor o igual prioridad que el operador actual
            else {
                while (!pila.isEmpty() && prioridad(pila.peek()) >= prioridad(c)) {
                    postfija += pila.pop();
                }
                pila.push(c);  // Anade el operador actual a la pila
            }
        }
        
        // Saca los operadores restantes de la pila y los anade a la salida
        while (!pila.isEmpty()) {
            postfija += pila.pop();
        }
        
        return postfija.toString();  // Devuelve la cadena en notacion postfija
    }
    
    // Devuelve la prioridad de un operador
    public static int prioridad(char operador) {
        switch (operador) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            case '^':
                return 3;
            default:
                return -1;
        }
    }
    
    //EJECUTAR - CLASE MAIN //
    public static void main(String[] args) {
        String infija = "r=2+3-6*4/10"; //OBTIENE LA EXPRESION
        String postfija = convertirInfijaPostfija(infija);  //La pasa para convertir
        System.out.println(postfija);  // imrpime la notacion postfija de la expresion
    }
}




