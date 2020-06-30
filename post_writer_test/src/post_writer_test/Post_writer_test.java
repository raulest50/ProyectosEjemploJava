/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package post_writer_test;

import java.io.*;
import java.net.*;


public class Post_writer_test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        try{
            String requ="http://raulest50.pythonanywhere.com/post_test";
            URL url = new URL(requ);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            BufferedWriter out=
                    new BufferedWriter(new OutputStreamWriter(
                    conn.getOutputStream()));
            out.write("param=neon_font");
            out.flush();
            out.close();
            BufferedReader in =
                    new BufferedReader( new InputStreamReader(
                    conn.getInputStream()));
            String respuesta;
            
            while ( (respuesta = in.readLine()) != null){   
                System.out.println(respuesta);
            }
            in.close();
        }
        catch(MalformedURLException e){
        }
        catch(IOException e){
        }
    }
    
}
