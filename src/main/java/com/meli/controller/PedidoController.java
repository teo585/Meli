package com.meli.controller;

import com.meli.config.AppConfig;
import com.meli.model.Pedido;
import com.meli.service.EmailSender;
import com.meli.util.CodigosPronosticoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/pedido")
public class PedidoController {

    private final String WEATHER_API_KEY = "886fb2a9b6c742e297c175059242406"; // Reemplaza con tu API key real
    private final String WEATHER_API_URL = "https://api.weatherapi.com/v1/forecast.json";

    @Autowired
    private AppConfig appConfig;

    @PostMapping("/crear")
    public ResponseEntity<?> crearPedido(@Valid @RequestBody Pedido pedido, BindingResult result) {
        System.out.println("API respondiendo");

        // Verificar errores de validación
        if (result.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            result.getFieldErrors().forEach(fieldError -> {
                errorMessage.append(fieldError.getDefaultMessage()).append(". ");
            });
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage.toString());
        }

        // Consultar clima usando API de weatherapi.com
        try {
            String weatherUrl = buildWeatherApiUrl(pedido.getLatitud(), pedido.getLongitud());
            RestTemplate restTemplate = new RestTemplate();
            Map<String, Object> weatherResponse = restTemplate.getForObject(new URI(weatherUrl), Map.class);

            // Construir nuevo objeto Map con campos específicos
            Map<String, Object> responseToSend = new HashMap<>();

            // Obtener código de pronóstico y texto del pronóstico
            String codigoPronosticoYTexto = getCodigoPronosticoYTexto(weatherResponse);

            // Verificar si el JSON de respuesta contiene uno de los códigos de pronóstico
            if (codigoPronosticoYTexto != null && CodigosPronosticoUtil.contieneCodigo(codigoPronosticoYTexto.split(" ")[0])) {
                System.out.println("Entrá verificación del json");
                responseToSend.put("forecast_code", codigoPronosticoYTexto.split(" ")[0]); // Suponiendo que el código está antes del primer espacio
                responseToSend.put("forecast_description", codigoPronosticoYTexto.substring(codigoPronosticoYTexto.indexOf(" ") + 1));
                responseToSend.put("buyer_notification", "True");

                // Enviar correo electrónico si se encuentra un código de pronóstico
                String emailDestinatario = pedido.getCorreoElectronico(); // Aquí deberías obtener el email del pedido
                String subject = "Actualización de entrega de paquete";
                String body = "Hola! Tenemos programada la entrega de tu paquete para mañana, en la dirección de entrega esperamos un día con "
                        + codigoPronosticoYTexto  + " y por esta razón es posible que tengamos retrasos. Haremos todo a nuestro alcance para cumplir con tu entrega.";

                System.out.println("emailDestinatario");
                System.out.println(emailDestinatario);
                System.out.println("subject");
                System.out.println(subject);
                System.out.println("body");
                System.out.println(body);

                // Configurar datos de email desde AppConfig
                String username = appConfig.getEmailUsername();
                String password = appConfig.getEmailPassword();

                EmailSender emailSender = new EmailSender(username, password);

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

                try {
                    emailSender.sendEmail(emailDestinatario, subject, htmlContent);
                    System.out.println("Correo enviado exitosamente!");
                } catch (MessagingException e) {
                    e.printStackTrace();
                    System.err.println("Error al enviar el correo: " + e.getMessage());
                }

                return ResponseEntity.ok(responseToSend);
            } else {
                return ResponseEntity.ok("No se envía notificación debido a que el código de pronóstico no está en la lista requerida para generarla.");
            }
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

    private String getCodigoPronosticoYTexto(Map<String, Object> jsonResponse) {
        if (jsonResponse.containsKey("forecast")) {
            Map<String, Object> forecast = (Map<String, Object>) jsonResponse.get("forecast");
            if (forecast.containsKey("forecastday")) {
                List<Map<String, Object>> forecastDays = (List<Map<String, Object>>) forecast.get("forecastday");
                for (Map<String, Object> forecastDay : forecastDays) {
                    if (forecastDay.containsKey("day")) {
                        Map<String, Object> day = (Map<String, Object>) forecastDay.get("day");
                        if (day.containsKey("condition")) {
                            Map<String, Object> condition = (Map<String, Object>) day.get("condition");
                            if (condition.containsKey("code") && condition.containsKey("text")) {
                                Integer code = (Integer) condition.get("code");
                                String codeString = String.valueOf(code);
                                if (CodigosPronosticoUtil.contieneCodigo(codeString)) {
                                    String text = (String) condition.get("text");
                                    System.out.println("Encontró el código");
                                    System.out.println("Código: " + codeString);
                                    System.out.println("Texto del pronóstico: " + text);
                                    return codeString + " " + text;
                                }
                            }
                        }
                    }
                }
            }
        }
        return null;
    }
}
