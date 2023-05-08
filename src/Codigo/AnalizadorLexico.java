
package Codigo;

public class AnalizadorLexico {
    String[][] reservPal = {{"Inicio", "Entero", "Real", "Leer", "Escribir", "Fin"},    //Palabras reservadas en una array de  2 dimensiones donde se encuentra tambien su identificador
                             {"401", "402", "403", "404", "405", "406"}};
    String[] clasificaciones = {"Identificador", "Palabra Reservada", "Número Entero", "Número Real", "Caracter Simple"};   //Clasificaciones detectables
    int inicio, fin, linea;
    String cadena, token;
    Boolean termina;
    public Listas lsimbolos, lpalReservadas, ltokens, lerrores; //Listas categorizadas en publico para acceder a ellas desde fuera
    
    AnalizadorLexico(){
        inicio = fin = linea = 0; //El cabezal de lectura se resetea a 0 la igual que la linea de lectura
        cadena = "";
        token = "";
        termina = false;
        lsimbolos = new Listas();
        lpalReservadas = new Listas();
        ltokens = new Listas();
        lerrores = new Listas();
    }
    
    public void inicia(String cadena){
        linea ++; //Se aumenta en 1 el contador de linea 
        inicio = fin = 0; //Se resetea el cabezal a 0
        evaluaAutomata(cadena); //Se manda al Automata la cadena (linea)
    }
    
    void evaluaAutomata(String cadena){
        this.cadena = cadena; //Se guarda la cadena en una variable global de clase
        token = "";
        termina = false;
        int estado = 0; //Se comienza en el estado 0
        Boolean continuar = true; //Se declara la variable de bucle

        if(cadena.charAt(fin) != ' ' && cadena.charAt(fin) != '\n' && cadena.charAt(fin) != '\t'){ //Si no es una linea vacia, un espacio o un salto de linea continua
            do{ //El bucle se repite siempre hasta terminar de leer la línea entera
                switch(estado){
                    case 0: if(fin < cadena.length() && isAlphMm(cadena.charAt(fin))){ //Si es un caracter alfanumerico mayusculo o minusculo
                                estado = 1; fin++; //va al estado 1 para continuar y se recorre el cabezal
                            }else if(fin < cadena.length() && isNum(cadena.charAt(fin))){ //Si es un numero
                                estado = 4; fin++;
                            }else if(fin < cadena.length() && isSimpleChar(cadena.charAt(fin)) && (fin - inicio == 0)){ //si es un caracter simple
                                estado = 15; 
                                if(fin == cadena.length() - 1){ //Si ya estamo a un caracter antes del fin de linea no se mueve el cabezal
                                }else{
                                    fin++; //Si aun nos queda mas en la linea se mueve el cabezal
                                }
                            }else {
                                estado = 2;
                            }
                        break;
                    case 1: if(fin < cadena.length() && isAlphMm(cadena.charAt(fin))){ //Si es un alfanumerico mayusculo o minusculo
                                if(fin == cadena.length() - 1){
                                    estado = 2;
                                    termina = true;
                                }else{
                                    fin++;
                                }
                            }else if(fin < cadena.length() && isNum(cadena.charAt(fin))){
                                fin++;
                            }else if(fin < cadena.length() && cadena.charAt(fin) == '_'){
                                fin++;
                            }else{
                                fin--;
                                estado = 2;
                            }
                        break;
                    case 2: 
                            //fin--;
                            if(esReservada()){ // Se verifica si el token detectado es una palabra reservada
                                estado = 18; //si lo es se manda a generar un token de palabra reservada
                            }else{
                                generaToken(500); //Si no se manda a creara un token de variable (Identificador)
                                continuar = false;
                            }
                            break;
                    
                    case 4: if(fin < cadena.length() && isNum(cadena.charAt(fin))){
                                fin++;
                            }else if(fin < cadena.length() && cadena.charAt(fin) == '.'){
                                estado = 8; fin++; //tiene punto, es flotante, se marca como real
                            }else{
                                fin--;
                                estado = 7; //Generar Entero
                            }
                        break;
                    case 7: generaToken(450); //Se manda a generar un token de numero entero
                            continuar = false;
                        break;
                    case 8: if(fin < cadena.length() && isNum(cadena.charAt(fin))){ //continua registrando el numero flotante despuesdel punto
                                fin++;
                            }else{
                                fin--;
                                estado = 9; //Genera Num Real
                            }
                        break;
                    case 9: 
                            generaToken(451); //Genera un token de tipo numero flotante
                            continuar = false;
                        break;
                    case 15:
                        if(fin < cadena.length() && isSimpleChar(cadena.charAt(fin)) && (fin - inicio == 0)){
                            //Es un caracter doble
                            estado = 16;
                        }else{
                            //No es caracter
                            fin--;
                        }
                        break;
                    case 16:
                            generaToken((int) cadena.charAt(fin)); //Se genera un token de caracter simple
                            continuar = false;
                        break;
                    case 17: 
                            errores(); //Se genera un error y se guarda en la lista de errores
                            continuar = false;
                        break;
                    case 18: 
                            palabraReservada();//Se genera el token de palabra reservada
                            continuar = false;
                        break;
                }

            }while(continuar == true);
        }else{
            terminaLinea(); // ejecuta la funcion para terminar la línea actual
        }
        if(termina == false) //Si no se termina aun la linea se vuelve a mandar el resto de la linea en recursivo
            evaluaAutomata(this.cadena);
    }
    
    //---------------------------------- FUNCIONES DE DETECCIÓN DE CARACTERES Y CLASIFICACION ----------------------------------
    Boolean isNum(char i){ //Verifica si el caracter que entra es un numero del 0 al 9
        Boolean es = false;
        if(i >= '0' && i <= '9'){
            es = true;
        }
        return es;
    }
    
    Boolean isAlphMm(char i){ //Verifica si el caracter que entra es un caracter de entre la A a la Z minuscula o mayuscula
        Boolean es = false;
        if(i >= 'A' && i <= 'Z' || i >= 'a' && i <= 'z'){
            es = true;
        }
        return es;
    }
    
    Boolean isSimpleChar(int i){ //Verifica si el caracter que entra es un simbolo especial comparandolo consu valor ASCII
        Boolean es = false;
        if(i == 59 | i == 61 | i == 43 | i == 45 | i == 47 | i == 42 | i == 44 | i == 123 | i == 125 | i == 40 | i == 41 | i == 58){
            es = true;
        }
        return es;
    }
    
    void generaToken(int atributo){ //Función para generar los tokens y guardarlos en su lista
        int ci = inicio;
        int repeticion = 1;
        do{
            token = token + cadena.charAt(ci);
            ci++;
        }while(ci <= fin); //bucle que recorre la cadena desde el cabezal de inicio hasta fin y lo almacena en un token


        if(atributo == 500){ //Si el atributo es 500 se toma como token de variable o identificador
            
            if(lsimbolos.contiene(token) == false){ //si la lista de simbolos aun no contiene el token
                if(!lsimbolos.estaVacia()){ //si la lista de simbolos no esta vacia
                    atributo = Integer.parseInt(lsimbolos.RecorreUno(lsimbolos.CuentaElementos()-1)[1])+1; //obtiene el valor del ultimo atributo guardado y le suma uno para no repetir el identificador de cada variable
                }
                lsimbolos.agregarAlFinal(token, atributo, repeticion, linea, getDesc(atributo)); //Se agrega el token a la lista de simbolos
            }else{
                ltokens.contiene(token); //Busca el token el la lista de tokens repetidos
                atributo = Integer.parseInt(ltokens.RecorreUno(ltokens.vueltaNeeded)[1]); //guarda el numero de atributo de la ultima repeticion del token
                repeticion = ltokens.cuantos(token) + 1; //Le suma uno al valor del atributo para no repetir identificador
                lsimbolos.contiene(token);//Busca el token en la lista de simbolos
                lsimbolos.getNodo(lsimbolos.vueltaNeeded).repeticiones ++; //se le suma al valor de repeticiones del token en la lista de simbolos 1
            }
        }
        ltokens.agregarAlFinal(token, atributo, repeticion, linea, getDesc(atributo)); //Guarda el nuevo token en la lista de tokens
        terminaLinea(); //Se termina la verificacion del token y continua al que sigue
    }
    
    void palabraReservada(){ //generador de tokens para las plabras reservadas
        Boolean es = true;
        int repeticion = 1;
        int ci = inicio;
        do{
            token = token + cadena.charAt(ci);
            ci++;
        }while(ci <= fin);
        
        if(lpalReservadas.contiene(token) == false){
            for(int i = 0; i < reservPal[0].length; i++){
                if(reservPal[0][i].equals(token)){
                    es = true;
                    int atributo = Integer.parseInt(reservPal[1][i]);
                    lpalReservadas.agregarAlFinal(token, atributo, repeticion, linea, getDesc(atributo));
                    ltokens.agregarAlFinal(token, atributo, repeticion, linea, getDesc(atributo));
                    break;
                }else{ 
                    es = false;
                }
            }
        }else{
            lpalReservadas.contiene(token);
            Nodo temp = lpalReservadas.getNodo(lpalReservadas.vueltaNeeded);
            temp.repeticiones++;
            ltokens.agregarAlFinal(token, temp.atributo, temp.repeticiones, linea, getDesc(temp.atributo));
        }
        
        if(es == false) {
            lerrores.agregarAlFinal(token, 800, repeticion, linea, getDesc(800));
        }
        
        terminaLinea();
    }
    
    void terminaLinea(){    //Se termina el token actual y si hay mas contenido en la linea continua, si no termina
        if(fin < cadena.length() - 1){
            fin++;
            inicio = fin;
        }else{
            termina = true;
        }
    }
    
    void errores(){ //Generado de tokens para la lista de errores
        int ci = inicio;
        do{
            token = token + cadena.charAt(ci);
            ci++;
        }while(ci <= fin);
        lerrores.agregarAlFinal(token, 800, 1, linea, getDesc(800));
        terminaLinea();
    }
    
    boolean esReservada(){ //Verificador de palabra reservada
        Boolean es = true;
        int ci = inicio;
        do{
            token = token + cadena.charAt(ci);
            ci++;
        }while(ci <= fin);
        
        for(int i = 0; i < reservPal[0].length; i++){
            if(reservPal[0][i].equals(token)){
                //Se verifica que si es reservada
                es = true;
                break;
            }else{ 
                //Se verifica que no es reservada
                es = false;
            }
        }
        token = "";
        return es;
    }

    String getDesc(int atributo){ //Funcion que clasifica y regresa el string de su descripcion
        if(atributo <= 255){
            return clasificaciones[4];
        }else if(atributo >= 500){
            return clasificaciones[0]; //Variable
        }else if(atributo >= 400 && atributo <= 406){
            return clasificaciones[1];
        }else if(atributo == 450){
            return clasificaciones[2]; //Numero Entero
        }else if(atributo == 451){
            return clasificaciones[3]; //Numero Real
        }
        
        return "";
    }
}
