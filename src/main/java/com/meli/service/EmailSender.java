package com.meli.service;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class EmailSender {

    private final String username;
    private final String password;
    private final Properties props;

    public EmailSender(String username, String password) {
        this.username = username;
        this.password = password;

        // Configurar propiedades para el servidor SMTP (ejemplo para Gmail)
        props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
    }

    public void sendEmail(String recipientEmail, String subject, String content) throws MessagingException {
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject(subject);

            // Configurar el contenido del mensaje como HTML
            message.setContent(content, "text/html; charset=utf-8");

            Transport.send(message);
            System.out.println("Correo enviado satisfactoriamente a " + recipientEmail);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
