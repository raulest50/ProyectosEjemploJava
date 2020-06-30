/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ping_app;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.swing.JLabel;
import javax.swing.JTextArea;

/**
 *
 * @author esteban
 */
public class SinglePing extends Thread{
    
    String ip;
    String id;
    int tm;
    JTextArea jt;
    JLabel jl;
    String fin;
    
    public SinglePing(String ip, String id, int tm, JTextArea jt, JLabel jl, String fin){
        this.ip = ip;
        this.id = id;
        this.tm = tm;
        this.jt = jt;
        this.jl = jl;
        this.fin = fin;
    }
    
    @Override
    public void run(){
        ping(ip);
    }
    
    public void ping(String ipAddress){
        try
        {
            InetAddress inet = InetAddress.getByName(ipAddress);
            boolean status = inet.isReachable(tm); //Timeout = tm milli seconds
            if (status) jt.setText(jt.getText()+'\n'+ipAddress+"---> is reachable");
            if (fin.equals(id)) jl.setText("barrido terminado :)");
            //System.out.println(ipAddress+"---> is reachable");
            //else System.out.println(ipAddress+"---> is not reachable");
            
        }
        catch (UnknownHostException e){
            System.err.println("Host does not exists");
        }
        catch (IOException e){
            System.err.println("Error in reaching the Host");
        }
    }
            
}
