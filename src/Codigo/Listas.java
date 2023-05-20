
package Codigo;


public class Listas {
    protected Nodo inicio, fin;
    protected Listas lista;
    public int vueltaNeeded;
    
    public Listas(){
        inicio = null;
        fin = null;
    }
	
    public boolean estaVacia(){
        return inicio == null;
    }
	
    public void agregarAlFinal(String elemento, int atributo){
        if(!estaVacia()){
            fin.siguiente = new Nodo(elemento, atributo);
            fin = fin.siguiente;
        }else{
            inicio = fin = new Nodo(elemento, atributo);
        }
    }

    public void agregarAlFinal(String elemento, int atributo, String tipoDato){
        if(!estaVacia()){
            fin.siguiente = new Nodo(elemento, atributo, tipoDato);
            fin = fin.siguiente;
        }else{
            inicio = fin = new Nodo(elemento, atributo, tipoDato);
        }
    }
    
    public void agregarAlFinal(String elemento, int atributo, Listas lista){
        if(!estaVacia()){
            fin.siguiente = new Nodo(elemento, atributo, lista);
            fin = fin.siguiente;
        }else{
            inicio = fin = new Nodo(elemento, atributo, lista);
        }
    }

    public void agregarAlFinal(String elemento, int atributo, int repeticiones, int linea, String descripcion){
        if(!estaVacia()){
            fin.siguiente = new Nodo(elemento, atributo, repeticiones, linea, descripcion);
            fin = fin.siguiente;
        }else{
            inicio = fin = new Nodo(elemento, atributo, repeticiones, linea, descripcion);
        }
    }

    public void agregarAlFinal(String elemento, int atributo, int repeticiones, int linea){
        if(!estaVacia()){
            fin.siguiente = new Nodo(elemento, atributo, repeticiones, linea);
            fin = fin.siguiente;
        }else{
            inicio = fin = new Nodo(elemento, atributo, repeticiones, linea);
        }
    }
	
    public void agregarAlFinalS(String elemento, int atributo, int repeticiones, int linea, Object value){
        if(!estaVacia()){
            fin.siguiente = new Nodo(elemento, atributo, repeticiones, linea, value);
            fin = fin.siguiente;
        }else{
            inicio = fin = new Nodo(elemento, atributo, repeticiones, linea, value);
        }
    }

    public int CuentaElementos(){
        int c = 0;
        Nodo recorrer = inicio;
        while(recorrer != null){
            recorrer = recorrer.siguiente;
            c++;
        }
        return c;
    }
    
    public Boolean contiene(String token){
        String[] fila = new String[2];
        Boolean existe = false;
        int c = CuentaElementos();
        
        for(int i = 0; i < c; i++){
             fila = RecorreUno(i);
             if(fila[0].equals(token)){
                 existe = true;
                 vueltaNeeded = i;
                 break;
             }
        }
        return existe;
    }
    
    public Boolean contieneProd(int prod){
        String[] fila = new String[2];
        Boolean existe = false;
        int c = CuentaElementos();
        
        for(int i = 0; i < c; i++){
             fila = RecorreUno(i);
             if(Integer.parseInt(fila[1]) == prod){
                 existe = true;
                 vueltaNeeded = i;
                 break;
             }
        }
        return existe;
    }
    
    public String[] RecorreUno(int n){
        String[] fila = new String[2];
        
        if(!estaVacia()){
            Nodo recorrer = inicio;
            for(int i = 0; i < n; i++){
                recorrer = recorrer.siguiente;
            }
            
            fila[0] = recorrer.getDato();
            fila[1] = recorrer.getAtributo() + "";
        }
        return fila;
    }
    
    public Listas getList(int n){
        
        if(!estaVacia()){
            Nodo recorrer = inicio;
            for(int i = 0; i < n; i++){
                recorrer = recorrer.siguiente;
            }
            
            lista = recorrer.getConjunto();
        }
        return lista;
    }
    
    public void limpiar(){
        inicio = null;
        fin = null;
    }

    public Nodo getNodo(int n){
        Nodo recorrer;
        if(!estaVacia()){
            recorrer = inicio;
            for(int i = 0; i < n; i++){
                recorrer = recorrer.siguiente;
            }
        }else{
            recorrer = null;
        }
        return recorrer;
    }

    public int cuantos(String token){
        int contador = 0;
        String[] fila = new String[2];
        int c = CuentaElementos();
        
        for(int i = 0; i < c; i++){
             fila = RecorreUno(i);
             if(fila[0].equals(token)){
                 contador ++;
             }
        }
        return contador;
    }

    public void eliminarUltimosDos(){
        int c = CuentaElementos();
        Nodo recorrer;
        if(!estaVacia()){
            recorrer = inicio;
            for(int i = 0; i < c-3; i++){
                recorrer = recorrer.siguiente;
            }
            fin = recorrer;
        }else{
            recorrer = null;
        }
    }
}
