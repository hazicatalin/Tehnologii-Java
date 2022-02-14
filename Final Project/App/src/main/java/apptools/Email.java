/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package apptools;

import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;



/**
 *
 * @author hazi_
 */
public class Email {
    private static Email instance = new Email();
    private final String gmailEmail = "document.recognition2021@gmail.com";
    private final String gmailPassword = "OcrDoc2021";

    private Email() {
    }
    
    public static Email getInstance(){
      return instance;
   }
    
    public String gethello(){
        return "Hello from email!";
    }
    
    public void sendEmail(String sendToEmail, String subject, String emailMessage){

        System.out.println("SimpleEmail Start");
		
	    String smtpHostServer = "smtp.google.com";
	    
	    Properties props = System.getProperties();

	    props.put("mail.smtp.host", smtpHostServer);

	    Session session = Session.getInstance(props, null);
	    
            try
	    {
	        MimeMessage msg = new MimeMessage(session);
	      //set message headers
	        msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
	        msg.addHeader("format", "flowed");
	        msg.addHeader("Content-Transfer-Encoding", "8bit");

	        msg.setFrom(new InternetAddress(gmailEmail, "App"));

	        msg.setReplyTo(InternetAddress.parse(gmailEmail, false));

	        msg.setSubject(subject, "UTF-8");

	        msg.setText(emailMessage, "UTF-8");

	        msg.setSentDate(new Date());

	        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(sendToEmail, false));
	        System.out.println("Message is ready");
                Transport.send(msg);  

	        System.out.println("EMail Sent Successfully!!");
	    }
	    catch (Exception e) {
	        e.printStackTrace();
	    }
	}
}
