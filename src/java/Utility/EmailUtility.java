package Utility;

import java.util.Properties;
import jakarta.mail.*;
import jakarta.mail.internet.*;

public class EmailUtility {

    public static void sendEmail(String toEmail, String subject, String message) throws MessagingException {
        String fromEmail = "quysy.quizpracticingsystem@gmail.com";
        String password = "hfbi rjoc uikr jzzt";

        //create a Properties object to set SMTP attributes
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        //Verify account and password
        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        };

        //New session
        Session session = Session.getInstance(props, auth);

        //MimeMessage create form the above session, use for sending email
        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(fromEmail));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
        msg.setSubject(subject);
        msg.setText(message);

        Transport.send(msg);
    }

    // New method to send HTML email
    public static void sendHtmlEmail(String toEmail, String subject, String htmlMessage) throws MessagingException {
        String fromEmail = "quysy.quizpracticingsystem@gmail.com";
        String password = "hfbi rjoc uikr jzzt";

        // Create a Properties object to set SMTP attributes
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        // Verify account and password
        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        };

        // New session
        Session session = Session.getInstance(props, auth);

        // Create a MimeMessage
        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(fromEmail));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
        msg.setSubject(subject);

        // Set the email content to HTML format
        msg.setContent(htmlMessage, "text/html; charset=utf-8");

        // Send the email
        Transport.send(msg);
    }
}
