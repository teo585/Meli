package com.meli.controller;

import com.meli.model.Pedido;
import org.apache.logging.log4j.spi.LoggerRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/pedido")
public class PedidoController {

    @PostMapping("/crear")
    public ResponseEntity<String> crearPedido(@Valid @RequestBody Pedido pedido, BindingResult result) {
        System.out.println("Hola, crear pedido bienvenido");
        if (result.hasErrors()) {
            // Si hay errores de validación, retornamos un mensaje con los errores
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error en los datos recibidos");
        }

        // Aquí puedes procesar el pedido, por ejemplo, guardarlo en una base de datos
        // o enviarlo a otro servicio.

        // Por ahora, solo retornamos un mensaje de éxito
        return ResponseEntity.status(HttpStatus.CREATED).body("Pedido creado exitosamente");
    }
}
