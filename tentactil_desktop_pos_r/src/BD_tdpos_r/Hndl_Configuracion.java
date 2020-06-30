package BD_tdpos_r;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *clase conectora a la tabala de SQL de configuracion.
 * Esta clase implementa todas las funciones
 * @author esteban
 */
public class Hndl_Configuracion extends BDHandler{
    
    
    /**
     * metodo que retorna la configuracion identifiada con idconf.
     * @param idconf
     * @return 
     * @throws java.lang.ClassNotFoundException 
     * @throws java.sql.SQLException 
     */
    public String getConfiguracion(String idconf) 
            throws ClassNotFoundException, SQLException{
        String r =  "";
        
        super.SetConnection();
        super.pst = (PreparedStatement) this.link.prepareStatement(SQL_Configuracion.SELECT);
        
        super.pst.setString(1, idconf);
        super.rs = pst.executeQuery();
        while(rs.next()){
            r = super.rs.getString(SQL_Configuracion.COL_VALOR);
        }
        
        super.CerrarTodo();
        return r;
    }
    
    
    /**
     * metodo que se usa para establecer valores string para las configuraciones
     * en la BD negocio_r
     * @param idconf
     * @param valor
     * @throws java.lang.ClassNotFoundException
     * @throws java.sql.SQLException
     */
    public void updateConf(String idconf, String valor) 
            throws ClassNotFoundException, SQLException{
        
        super.SetConnection();
        super.pst = (PreparedStatement) super.link.prepareStatement(SQL_Configuracion.UPDATE);
        
        super.pst.setString(1, valor);
        super.pst.setString(2, idconf);
        super.pst.executeUpdate();
        
        super.CerrarUpdate();
    }
    
    
    
    /**
     * 
     * genera una clave dinamica y la guarda en la tabla de configuracion.
     * de 1000 a 9999.
     * 
     * se debe invocar cada que se hace una operacion sensible para el sistema.
     * 
     */
    public void generarClaveDinamica()
            throws ClassNotFoundException, SQLException{
        Hndl_Cupon hndcu = new Hndl_Cupon();
        String claveDinamica = Integer.toString(hndcu.generarClaveAleatoria());
        
        // se guarda la clave dinamica en la BD
        this.updateConf(SQL_Configuracion.PRT_DYN_PASS, claveDinamica);
    }
    
    
    /**
     * metodo que recibe un asunto y un mensaje y los envia
     * al correo electronico especificado.
     * se debe asegurar de no ejecutar este metodo si no hay un 
     * correo electronico debidamente configurado.
     */
    public void EnviarTextEmail
        (String gcuenta, String pass, String destino[], String subject, String body) 
                throws AddressException, MessagingException{
        Properties props = System.getProperties();
        String host = "smtp.gmail.com";
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", gcuenta);
        props.put("mail.smtp.password", pass);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(props);
        MimeMessage message = new MimeMessage(session);
        
        
        message.setFrom(new InternetAddress(gcuenta));
        InternetAddress[] toAddress = new InternetAddress[destino.length];
        
        // To get the array of addresses
        for( int i = 0; i < destino.length; i++ ) {
            toAddress[i] = new InternetAddress(destino[i]);
        }
        
        for( int i = 0; i < toAddress.length; i++) {
            message.addRecipient(Message.RecipientType.TO, toAddress[i]);
        }
        
        message.setSubject(subject);
        message.setText(body);
        Transport transport = session.getTransport("smtps");
        transport.connect(host, gcuenta, pass);
        transport.sendMessage(message, message.getAllRecipients());
        
        transport.close();
        
    }
        

    /**
     * metodo que envia un email tomando  los datos ya configurados de la
     * tabla de configuracion.
     * @param subject
     * @param body 
     */
    public void SendConfiguredEmail(String subject, String body) 
            throws ClassNotFoundException, SQLException, MessagingException, AddressException{
        String gcuenta = this.getConfiguracion(SQL_Configuracion.PRT_SENDER_MAIL);
        String pass = this.getConfiguracion(SQL_Configuracion.PRT_SENDER_PASS);
        String[] destino = { this.getConfiguracion(SQL_Configuracion.PRT_DESTINATION_MAIL) };
        
        this.EnviarTextEmail(gcuenta, pass, destino, subject, body);
    }
    
}
