package newgeneration_barcodeprinter;

import java.util.ArrayList;

/**
 *
 * @author esteban
 * 
 * clase para encapsular todos los metodos relaciones con el manejo de
 * codigos de barras.
 * 
 * Metodos para implementar el siguiente de un codigo de barras
 * con caracteres numericos y letras.
 * 
 * tambien metodos para hacer calculos para que el codigo de barras quede en
 * las dimesiones correctas entre otros.
 */
public class BarCodeLogic {
    
    
    
    
    public BarCodeLogic(){
        
    }
    
    /**
     * el metodo getNextProducto permite obteneer el siguiente de un codigo
     * pero no tiene en cuenta el caso en el que cod="". este metodo se 
     * agrega para tener en cuenta ese caso y ademas se hacen privados los
     * 2 metodos anteriores para evitar que se usen por error y forzar el uso
     * de esta vercion ampliada mas segura.
     * los dos 
     * @param cod
     * @return 
     */
    public String getNextProductoCodeBlinded(String cod){
        if(cod.toCharArray().length == 0){
            return "1";
        }
        else{
            return this.getNextProductCode(cod);
        }
    }
    
    
    /**
     * Cuando se deja el campo de texto para establecer el codigo de un poroducto en la 
     * secion de codificacion de productos se hace uso de este metodo para establecer 
     * de manera automatica el siguiente codigo.
     * consiste en buscar en la tabla de confiuguracion el ultimo codigo
     * empleado y usar un metodo que a partir de ese String determinad e manera automatica
     * y deacuerdo a una logic establecida cual es el siguiente codigo qu se debe emplear.
     * @param cod
     * @return 
     */
    private String getNextProductCode(String cod){
        String r = "";
        
        // se pasa el String a arreglo char.
        //    x    x    x          x     ---> String
        // { [0], [1], [2], ... [n-1]} -__-> char array
        char[] cod_array = cod.toCharArray(); // se pasa a array
        
        ArrayList re = new ArrayList(); // para guardar la respuesta de cada ivocacion
        // del metodo que retorna el char+1 en index 0 y el carry como boolean en index 1.
        // se barre el arreglo char desde el ultimo caracter hastas el caracter 0.
        int len = cod_array.length-1;
        while(len >=0){
            re = NextChar(cod_array[len]); // por defecto siempre se va a calcular minimo
            // el siguiente del caracter menos significativo.
            cod_array[len] = (char) re.get(0); // se reemplaza por su char + 1 correspondiente.
            if( !((boolean) re.get(1)) )  len=-2; // si no hay carry entonces no se hace itera mas
            len--; // se resta 1 para continuar la iteracion del caracter mas significativo.
        }
        
        r = String.valueOf(cod_array);
        
        // ya habiendo iterado hasta que no hubiese mas carry entonces se revisa si se itero hasta
        // el caracter mas significativo y si se genero crry por prte de ese caracter.
        if(len==-1){ // si len es == -1 significa que se itero hasta el 0 que es el mas significativo.
            if( (boolean) re.get(1) ){ // si hay carry y se itero hasta el caracter mas significativo entonces
                // se debe agregar un caracter adicional aun mas significativo, que por defecto sera '1'
                r = "1" + r;
            } 
        }
        // finalmente se retorna en forma String el codigo siguiente
        return r;
    }
    
    /**
     * Este metodo se emplea para la logica que determina el siguiente codigo de un producto.
     * Tiene en cuenta que el char se puede ser un numero 0-9 o una letra aA-zZ. y para cualquiera de
     * los dos casos determina la equivalencia de un +1. en el caso de los numeros es trivial
     * pero en el caso de las letras consiste en la siguiente. Ejemplo. a+1=A, A+1=b, b+1=B, B+1=c...
     * ademas cuando se llega al caracter maximo ( 9 para numeros Z para las letras) se reporta un carry,
     * que indica que debe sumarse +1 al siguiente digito de mayor magnitud. Ej:
     * a9 +1 = A0, ya que 9+1 = 0 y ocurre un carry para 'a'. siempre que haya carry se debera sumar 
     * sucesivamente. si hay un carry y ya no quedan mas digitos entonces se debe agregar un nuevo digito.
     * EJ: Z99 + 1 = a000. En resumen este metodo entrega en el indice 0 del arraylist el valor siguiente del
     * caracter 'e', argumento de esta funcion, y en el indice 1 un boolean = true si hay carry y
     * true en caso contrario. Esta funcionalidad es la que permite cumplir con la especificacion 
     * descrita al comienzo, lo cual se implementa junto con la funcion getNextProductCode()
     * @param e
     * @return 
     */
    private ArrayList NextChar(char e){
        ArrayList r = new ArrayList();
        
        
        char strr = 0; // para guardar el siguiente caracter
         
        boolean carry = false; // para guardar si hay o no carry.
         
        switch(e){
        
            // para el caso de las letras. -->
            case 'a':
                strr = 'b';
                break;
            case 'A':
                strr = 'B';
                break;
            case 'b':
                strr = 'c';
                break;
            case 'B':
                strr = 'C';
                break;
            case 'c':
                strr = 'd';
                break;
            case 'C':
                strr = 'D';
                break;
            case 'd':
                strr = 'e';
                break;
            case 'D':
                strr = 'E';
                break;
            case 'e':
                strr = 'f';
                break;
            case 'E':
                strr = 'F';
                break;
            case 'f':
                strr = 'g';
                break;
            case 'F':
                strr = 'G';
                break;
            case 'g':
                strr = 'h';
                break;
            case 'G':
                strr = 'H';
                break;
            case 'h':
                strr = 'i';
                break;
            case 'H':
                strr = 'I';
                break;
            case 'i':
                strr = 'j';
                break;
            case 'I':
                strr = 'J';
                break;
            case 'j':
                strr = 'k';
                break;
            case 'J':
                strr = 'K';
                break;
            case 'k':
                strr = 'l';
                break;
            case 'K':
                strr = 'L';
                break;
            case 'l':
                strr = 'm';
                break;
            case 'L':
                strr = 'M';
                break;
            case 'm':
                strr = 'n';
                break;
            case 'M':
                strr = 'N';
                break;
            case 'n':
                strr = 'o';
                break;
            case 'N':
                strr = 'O';
                break;
            case 'o':
                strr = 'p';
                break;
            case 'O':
                strr = 'P';
                break;
            case 'p':
                strr = 'q';
                break;
            case 'P':
                strr = 'Q';
                break;
            case 'q':
                strr = 'r';
                break;
            case 'Q':
                strr = 'R';
                break;
            case 'r':
                strr = 's';
                break;
            case 'R':
                strr = 'S';
                break;
            case 's':
                strr = 't';
                break;
            case 'S':
                strr = 'T';
                break;
            case 't':
                strr = 'u';
                break;
            case 'T':
                strr = 'U';
                break;
            case 'u':
                strr = 'v';
                break;
            case 'U':
                strr = 'V';
                break;
            case 'v':
                strr = 'w';
                break;
            case 'V':
                strr = 'W';
                break;
            case 'w':
                strr = 'x';
                break;
            case 'W':
                strr = 'X';
                break;
            case 'x':
                strr = 'y';
                break;
            case 'X':
                strr = 'Y';
                break;
            case 'y':
                strr = 'z';
                break;
            case 'Y':
                strr = 'Z';
                break;
            case 'z':
                strr = 'a';
                break;
            case 'Z': // ocurre carry por letra.
                strr = 'a';
                carry = true; // se indica que ha ocurrido carry
                break;
                
                // para el caso de numeros.
            case '0':
                strr = '1';
                break;
            case '1':
                strr = '2';
                break;
            case '2':
                strr = '3';
                break;
            case '3':
                strr = '4';
                break;
            case '4':
                strr = '5';
                break;
            case '5':
                strr = '6';
                break;
            case '6':
                strr = '7';
                break;
            case '7':
                strr = '8';
                break;
            case '8':
                strr = '9';
                break;
            case '9':
                strr = '0'; // hay carry por numero.
                carry = true; // se indica que ha ocurrido carry.
                break;       
        }
        
        // se ponen los valores en el arrayList de respuesta
        
        r.add(strr);
        r.add(carry);
        
        return r;
    }
    
    
    /**
     * metodo que pasa de milimetros a pixeles.
     * @param mm
     * @param dpi
     * @return 
     */
    public int Mili2Pix(double mm, int dpi){
        return (int) Math.ceil(mm*dpi/25.4);
    }
    
    /**
     * metodo que pasa de pixeles a milimetros.
     * @param pix
     * @param dpi
     * @return 
     */
    public double Pix2Mili(int pix, int dpi){ // se agrega *100d y / 100d para hacer redondeo de hasta 2 decimales
        // ya que round solo redondea a entero.
        return (double) Math.round((pix * 25.4/dpi)*100d)/100d;
    }
    
    /**
     * metodo que calula el valor de u para ingresar en la linea:
     * barcode128Bean.setModuleWidth(UnitConv.in2mm(1.0f / dpi));
     * que es de la funcion: private void GenerarEan128PNG(String codigo, String path).
     * al numerador 1.0f le di el nombre de u, el cual es un valor que permite
     * ajustar el ancho del codigo de barras. Mediante experimentacion
     * halle que la funcion que relaciones el numero de digitos y u con el 
     * ancho en pixeles del codigo de barras es.
     * F(n, u) = 46*u + (n-1)*11*u. donde n corresponde al numero de digitos
     * del codigo de barras.
     * 
     * @param codigo
     * @param labelWidth
     * @return 
     */
    public float Ucalculation(String codigo, double labelWidth){
        float des_pix = (float) this.Mili2Pix(labelWidth, 96);
        int n = codigo.length();
        return des_pix/(46 + (n-1)*11);
    }
    
    
    /**
     * metodo que transforma de precio a texto
     * @param price
     * @return 
     */
    public String Price2Text(int price){
        String r="";
        
        String sPrice = Integer.toString(price);
        // para cada numero se concatena en r su correpsondiente 
        // en letra de acuerdi a la funcion Num2Letter.
        for(int i=0; i<sPrice.length(); i++){
            r += this.Num2Letter(sPrice.substring(i, i+1));
        }
        return r;
    }
    
    
    /**
     * metodo que asigna a cada numero del 0-9 una letra.
     * este metodo es usado por el metodo que transforma un 
     * precio en texto.
     * @param num
     * @return 
     */
    public String Num2Letter(String num){
        String r="";
        
        switch(num){
            case "0":
                r="O";
                break;
            case "1":
                r="U";
                break;
            case "2":
                r="D";
                break;
            case "3":
                r="T";
                break;
            case "4":
                r="C";
                break;
            case "5":
                r="V";
                break;
            case "6":
                r="S";
                break;
            case "7":
                r="Z";
                break;
            case "8":
                r="H";
                break;
            case "9":
                r="N";
                break;
        }
        return r;
    }
    
    
    /**
     * metodo que recibe 2 codigos de barras. retorna true si A
     * es mayor o igual a B, false en caso contrario
     * @param A
     * @param B
     * @return 
     */
    public boolean Comp_Cods_A_may_B(String A, String B){
        boolean r=false;
        
        // si A tiene mas caracteres que B entonces es mayor
        if(A.length() > B.length()) r=true;
        
        // si B tiene mas caracteres que A entonces B es mayor que A.
        if(A.length() < B.length()) r=false;
        
        // las dimenciones de los codigos son iguales
        if(A.length() == B.length()){
            int An = Integer.parseInt(A);
            int Bn = Integer.parseInt(B);
            r = An >= Bn;
        }
        return r;
    }
    
}
