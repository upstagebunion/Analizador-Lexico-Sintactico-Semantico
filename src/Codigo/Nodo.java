
package Codigo;

public class Nodo {
    public String dato;
    public int atributo;
    public Nodo siguiente;
    public Listas conjunto;
    public int repeticiones;
    public int linea;
    public String descripcion;
    public Object value;
    public String tipoDato;
	
    public Nodo(String d, int a){
        dato = d;
        atributo = a;
        siguiente = null;
    }

    public Nodo(String d, int a, String tp){
        dato = d;
        atributo = a;
        tipoDato = tp;
    }
	
    public Nodo(String d, int a, Nodo n){
        dato = d;
        atributo = a;
        siguiente = n;
    }
    
    public Nodo(String d, int a, Listas c){
        dato = d;
        atributo = a;
        conjunto = c;
        siguiente = null;
    }
    
    public Nodo(String d, int a, Nodo n, Listas c){
        dato = d;
        atributo = a;
        siguiente = n;
        conjunto = c;
    }

    public Nodo(String d, int a, int r, int l, String des){
        dato = d;
        atributo = a;
        repeticiones = r;
        linea = l;
        descripcion = des;
    }

    public Nodo(String d, int a, int r, int l, Object v){
        dato = d;
        atributo = a;
        repeticiones = r;
        linea = l;
        value = v;
    }

    public Nodo(String d, int a, int r, int l){
        dato = d;
        atributo = a;
        repeticiones = r;
        linea = l;
    }

    public String getDato() {
        return dato;
    }

    public int getAtributo() {
        return atributo;
    }
    
    public Listas getConjunto(){
        return conjunto;
    };

    public void SetRepeticion(int r){
        repeticiones = r;
    }
}
