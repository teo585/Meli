package com.meli.controller;

import com.meli.model.Pedido;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
            //map para devolver el json lo solicitado
            Map<String, Object> weatherResponse = restTemplate.getForObject(new URI(weatherUrl), Map.class);

            // Obtener el código de pronóstico y texto del pronóstico
            String codigoPronosticoYTexto = getCodigoPronosticoYTexto(weatherResponse);

            // Verificar si el JSON de respuesta contiene uno de los códigos de pronóstico
            if (codigoPronosticoYTexto != null) {
                // Construir el nuevo objeto Map con los campos específicos
                Map<String, Object> responseToSend = new HashMap<>();
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

                // Enviar solo los campos específicos como respuesta
                return ResponseEntity.ok(responseToSend);
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

    private String getCodigoPronosticoYTexto(Map<String, Object> jsonResponse) {
        // Verificar si el JSON de respuesta contiene el código de pronóstico en el forecastday
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
                                if (codigosPronostico.contains(codeString)) {
                                    String text = (String) condition.get("text");
                                    System.out.println("Encontró el código");
                                    System.out.println("Código: " + codeString);
                                    System.out.println("Texto del pronóstico: " + text);
                                    return "" + codeString + " " + text + ""; // Devuelve el código y el texto del pronóstico
                                }
                            }
                        }
                    }
                }
            }
        }
        return null; // Si no se encuentra ningún código de pronóstico válido
    }
}
