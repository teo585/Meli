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

    public void sendEmail(String recipientEmail, String subject, String codigoPronosticoYTexto) throws MessagingException {
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
            // Ejemplo de contenido HTML para el correo electrónico
            String htmlContent = "<!DOCTYPE html>\n" +
                    "<html lang=\"es\">\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                    "    <title>Notificación de Entrega</title>\n" +
                    "    <style>\n" +
                    "        body {\n" +
                    "            font-family: 'Arial', sans-serif;\n" +
                    "            background-color: #f0f2f5;\n" +
                    "            margin: 0;\n" +
                    "            padding: 0;\n" +
                    "            display: flex;\n" +
                    "            justify-content: center;\n" +
                    "            align-items: center;\n" +
                    "            height: 100vh;\n" +
                    "        }\n" +
                    "        .container {\n" +
                    "            background: #ffffff;\n" +
                    "            padding: 20px 40px;\n" +
                    "            border-radius: 10px;\n" +
                    "            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);\n" +
                    "            max-width: 600px;\n" +
                    "            text-align: center;\n" +
                    "        }\n" +
                    "        h1 {\n" +
                    "            color: #4CAF50;\n" +
                    "            font-size: 24px;\n" +
                    "            margin-bottom: 20px;\n" +
                    "        }\n" +
                    "        p {\n" +
                    "            font-size: 16px;\n" +
                    "            color: #333333;\n" +
                    "            line-height: 1.5;\n" +
                    "        }\n" +
                    "        .highlight {\n" +
                    "            font-weight: bold;\n" +
                    "            color: #FF5722;\n" +
                    "        }\n" +
                    "        .footer {\n" +
                    "            margin-top: 30px;\n" +
                    "            font-size: 14px;\n" +
                    "            color: #777777;\n" +
                    "        }\n" +
                    "    </style>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "    <div class=\"container\">\n" +
                    "        <h1>Notificación de Entrega</h1>\n" +
                    "        <p>Hola!</p>\n" +
                    "        <p>Tenemos programada la entrega de tu paquete para mañana, en la dirección de entrega esperamos un día con <span class=\"highlight\">"+codigoPronosticoYTexto+"</span> y por esta razón es posible que tengamos retrasos.</p>\n" +
                    "        <p>Haremos todo a nuestro alcance para cumplir con tu entrega.</p>\n" +
                    "        <div class=\"footer\">\n" +
                    "            <p>Gracias por tu comprensión.</p>\n" +
                    "            <p>Atentamente, Mercadolibre</p>\n" +
                    "        </div>\n" +
                    "    </div>\n" +
                    "</body>\n" +
                    "</html>\n";
            message.setContent(htmlContent, "text/html; charset=utf-8");
            Transport.send(message);
            System.out.println("Correo enviado satisfactoriamente a " + recipientEmail);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
