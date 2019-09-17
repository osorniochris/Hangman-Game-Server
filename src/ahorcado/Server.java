package ahorcado;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class Server {

    public static String easyWords [];
    public static String mediumWords [];
    public static String impossibleWords [];
    
    public static int port = 9600;
    
    public static void main(String[] args) {
        
        //local variables
        String word = "", line = "", aux="";
        Random rand = new Random();
        int pos = 0;
        
        //Initialize words' arrays
        initializeEW();
        initializeMW();
        initializeIW();
        
        try {
            
            ServerSocket s = new ServerSocket(port);
            System.out.println("Server iniciado en el puerto " + port );
            
            
            while ( true ){
                //client connected
                Socket cl = s.accept();
                System.out.println("\nCliente conectado desde " + cl.getInetAddress() + ":" + cl.getPort());
                
                int mistakes = 0 ;
                
                PrintWriter pw = new PrintWriter(cl.getOutputStream(), true);
                BufferedReader br= new BufferedReader(new InputStreamReader(cl.getInputStream()));
                
                int difficulty = Integer.parseInt( br.readLine() );

                //random word
                pos = rand.nextInt(20);

                
                if( difficulty == 1 ){
                    //easy
                    word = easyWords[pos];
                    System.out.println("Dificultad : Fácil");
                }
                else if( difficulty == 2 ){
                    //easy
                    word = mediumWords[pos];
                    System.out.println("Dificultad : Media");
                } 
                else if( difficulty == 3 ){
                    //easy
                    word = impossibleWords[pos];
                    System.out.println("Dificultad : Imposible");
                } 
                        
                System.out.println("Palabra : " + word);
                
                //line is compared with word, aux just an auxiliar to print
                line = wToLine(word,0);
                aux = wToLine(line,3);

                System.out.println("Progreso : "+ aux);
                
                pw.println(aux);
                pw.flush();
                pw.println(""+0);
                pw.flush();
                
                String character = "";

                while ( true ){

                    character = br.readLine().toUpperCase();
                    
                    if( character.equals("-1") ){
                        //cliente tries to type more than one char
                        
                        System.out.println("Más de una letra");
                        aux = wToLine(line,3);
                        System.out.println("Progreso : "+ aux);
                        
                        pw.println(aux);
                        pw.flush();
                        pw.println(mistakes);
                        pw.flush();
                    }
                    else{
                        if( word.contains(character)){
                            //check the char received and update line
                            line = replaceCharInLine(word, line, character);
                            aux = wToLine(line,3);
                            System.out.println("Progreso : "+ aux);
                            if( line.equals(word) ){
                                break;
                            }
                            pw.println(aux);
                            pw.flush();
                            pw.println(mistakes);
                            pw.flush();
                        }
                        else{
                            //the typed char in not in the word
                            mistakes = mistakes + 1;
                            if( mistakes == 5){
                                break;
                            }
                            aux = wToLine(line,3);
                            System.out.println("Progreso : "+ aux);
                            pw.println(aux);
                            pw.flush();
                            pw.println(mistakes);
                            pw.flush();
                        }
                    }
                }
                
                pw.println("<end>");
                pw.flush();
                
                if( mistakes == 5){
                    //client wins
                    pw.println("<fail>");
                    pw.flush();
                    
                    pw.println(word);
                    pw.flush();
                    
                    System.out.println("D E R R O T A");
                }
                else if(line.equals(word)){
                    //client loses
                    pw.println("<win>");
                    pw.flush();
                    
                    pw.println(word);
                    pw.flush();
                    
                    System.out.println("V I C T O R I A");
                }
                
                //close client connection
                br.close();
                pw.close();
                cl.close();
            }
        }
        catch ( Exception e ){
            e.printStackTrace();
        }
        
    }
    
    private static String wToLine(String word, int spaces){
        String aux = "";
        
        if( spaces == 1 ){
            aux = word.replaceAll("\\s", "    "); 
            aux = aux.replaceAll("[A-Z]", "_ ");
        }
        else if( spaces == 0 ){
            aux = word.replaceAll("[A-Z]", "_");
        }
        else if( spaces == 2 ){
            aux = word.replaceAll("\\s", "    "); 
        }
        else if( spaces == 3 ){
            for (int i = 0; i < word.length(); i++) {
                aux = aux + word.charAt(i) + " ";
            }
        }
        return aux;
    }
    
    private static String replaceCharInLine(String word, String line, String l){
        String aux = "";
        for (int i = 0; i < word.length(); i++) {
            if( word.charAt(i) == l.charAt(0)){
                aux = aux +l.charAt(0);
            }
            else {
                aux = aux + line.charAt(i);
            }
        }
        
        return aux;
    }
    
    private static void initializeEW(){
        
        //less than threee syllables 
        
        easyWords = new String [20];
        
        easyWords [0] = "MUJER";
        easyWords [1] = "HOMBRE";
        easyWords [2] = "GENTE";
        easyWords [3] = "AMOR";
        easyWords [4] = "CARA";
        easyWords [5] = "PIE";
        easyWords [6] = "BRAZO";
        easyWords [7] = "CODO";
        easyWords [8] = "ALMA";
        easyWords [9] = "HIJO";
        easyWords [10] = "VIDA";
        easyWords [11] = "PLAYA";
        easyWords [12] = "GATO";
        easyWords [13] = "PERRO";
        easyWords [14] = "FRUTA";
        easyWords [15] = "CARNE";
        easyWords [16] = "FECHA";
        easyWords [17] = "MES";
        easyWords [18] = "LUNES";
        easyWords [19] = "NORTE";
    }
    
    private static void initializeMW(){
        
        //more then 3 syllables
        
        mediumWords = new String [20];
        
        mediumWords [0] = "CALABAZA";
        mediumWords [1] = "CONSUMIDOR";
        mediumWords [2] = "HERRAMIENTAS";
        mediumWords [3] = "COMENTARIOS";
        mediumWords [4] = "PARLAMENTO";
        mediumWords [5] = "COMPUTADORA";
        mediumWords [6] = "NOMENCLATURA";
        mediumWords [7] = "DESMANTELAMIENTO";
        mediumWords [8] = "LICENCIATURA";
        mediumWords [9] = "INTELIGENCIA";
        mediumWords [10] = "EXPERIENCIA";
        mediumWords [11] = "REGRIGERADOR";
        mediumWords [12] = "AFICIONADO";
        mediumWords [13] = "CAPITALISMO";
        mediumWords [14] = "ORDENADOR";
        mediumWords [15] = "SECADORA";
        mediumWords [16] = "CONGELADOR";
        mediumWords [17] = "COMPLICADO";
        mediumWords [18] = "LIMPIEZA";
        mediumWords [19] = "VENTILADOR";
    }
    
    private static void initializeIW(){
        
        //sentences
        
        impossibleWords = new String [20];

        impossibleWords [0] = "TE VOY A REGALAR UN LIBRO DE COCINA";
        impossibleWords [1] = "NO CONOZCO A NADIE EN ESTE LUGAR";
        impossibleWords [2] = "EN EL TRABAJO NUEVO ME PAGAN MEJOR QUE EN EL ANTERIOR";
        impossibleWords [3] = "TENGO GRANDES PROYECTOS PARA ESTE AÑO";
        impossibleWords [4] = "TE ESTUVE ESPERANDO DURANTE TODA LA TARDE";
        impossibleWords [5] = "EL ACUSADO Y SU ABOGADA ABANDONARON LA SALA";
        impossibleWords [6] = "LAS ACCIONES DE LA EMPRESA AUMENTARON ESTA SEMANA";
        impossibleWords [7] = "ESTE MES COMIENZO LA FACULTAD";
        impossibleWords [8] = "EL INTENDENTE FUE REELECTO AYER";
        impossibleWords [9] = "ALEJANDRA NO QUISO PARTICIPAR";
        impossibleWords [10] = "ANA TUVO SUERTE AYER";
        impossibleWords [11] = "ANTONIA HIZO LAS COMPRAS HOY";
        impossibleWords [12] = "CUIDADO CON EL PERRO";
        impossibleWords [13] = "EL RESTAURANTE ESTABA LLENO";
        impossibleWords [14] = "EZEQUIEL TIENE ENTRENAMIENTO EL SIGUIENTE VIERNES";
        impossibleWords [15] = "KARINA DEBE TRABAJAR HOY";
        impossibleWords [16] = "LA CALLE ESTABA MOJADA";
        impossibleWords [17] = "LA CIUDAD SE ENCONTRABA EN LLAMAS";
        impossibleWords [18] = "LA GENTE NO PERMITE EL DESCENSO DEL TRANSPORTE";
        impossibleWords [19] = "LOS ANIMALES SON BASTANTE AGRESIVOS";
    }
    
    
    
}
