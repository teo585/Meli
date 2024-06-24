package com.meli.controller;

import com.meli.model.Pedido;
import org.apache.logging.log4j.message.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.Session;
import java.net.PasswordAuthentication;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/pedido")
public class PedidoController {

    private final String WEATHER_API_KEY = "886fb2a9b6c742e297c175059242406"; // Replace with your actual API key
    private final String WEATHER_API_URL = "https://api.weatherapi.com/v1/forecast.json";

    private final List<String> codigosPronostico = List.of("1186", "1189", "1192", "1195");

    @PostMapping("/crear")
    public ResponseEntity<?> crearPedido(@Valid @RequestBody Pedido pedido, BindingResult result) {
        System.out.println("Api respondiendo");

        // Verificar si hay errores de validación
        if (result.hasErrors()) {
            // Construir mensaje de error
            StringBuilder errorMessage = new StringBuilder();
            result.getFieldErrors().forEach(fieldError -> {
                errorMessage.append(fieldError.getDefaultMessage()).append(". ");
            });
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage.toString());
        }

        // Consultar el clima usando la API de weatherapi.com
        try {
            String weatherUrl = buildWeatherApiUrl(pedido.getLatitud(), pedido.getLongitud());
            RestTemplate restTemplate = new RestTemplate();
            Map<String, Object> weatherResponse = restTemplate.getForObject(new URI(weatherUrl), Map.class);

            // Verificar si el JSON de respuesta contiene uno de los códigos de pronóstico
            if (containsCodigoPronostico(weatherResponse)) {
                // Realizar la acción específica si se encuentra uno de los códigos de pronóstico
                System.out.println("Se encontró uno de los códigos de pronóstico en la respuesta JSON");
                // Puedes realizar cualquier acción específica aquí
            }

            return ResponseEntity.ok(weatherResponse);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al consultar el clima");
        }
    }

    private String buildWeatherApiUrl(Double latitud, Double longitud) {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("key", WEATHER_API_KEY);
        queryParams.put("q", latitud + "," + longitud);
        StringBuilder sb = new StringBuilder(WEATHER_API_URL);
        sb.append("?");
        queryParams.forEach((key, value) -> sb.append(key).append("=").append(value).append("&"));
        sb.setLength(sb.length() - 1);
        return sb.toString();
    }

    private boolean containsCodigoPronostico(Map<String, Object> jsonResponse) {
        // Verificar si el JSON de respuesta contiene uno de los códigos de pronóstico
        if (jsonResponse.containsKey("current")) {
            Map<String, Object> current = (Map<String, Object>) jsonResponse.get("current");
            if (current.containsKey("condition")) {
                Map<String, Object> condition = (Map<String, Object>) current.get("condition");
                if (condition.containsKey("code")) {
                    Integer code = (Integer) condition.get("code"); // El código es un Integer en el JSON
                    String codeString = String.valueOf(code); // Convertir a String para comparar con la lista de códigos
                    if (codigosPronostico.contains(codeString)) {
                        // Enviar correo electrónico
                        //sendEmail(emailDestinatario, (String) condition.get("text"));
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /*
    private void sendEmail(String toEmail, String forecastText) {
        // Configurar las propiedades para la conexión SMTP
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587"); // Puerto SMTP para Gmail

        // Configurar la sesión de correo
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("tu_correo@gmail.com", "tu_contraseña_de_aplicacion");
            }
        });

        try {
            // Crear un mensaje de correo
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("tu_correo@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Actualización de entrega de paquete");

            // Construir el contenido del mensaje
            String mensaje = "Hola! Tenemos programada la entrega de tu paquete para mañana, en la dirección de entrega esperamos un día con "
                    + forecastText + " y por esta razón es posible que tengamos retrasos. Haremos todo a nuestro alcance para cumplir con tu entrega.";
            message.setText(mensaje);

            // Enviar mensaje de correo electrónico
            Transport.send(message);

            System.out.println("Correo electrónico enviado exitosamente a: " + toEmail);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
    */
}
