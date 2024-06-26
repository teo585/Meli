package com.meli.controller;

import com.meli.model.RespuestaGuardada;
import com.meli.service.RespuestaGuardadaRegistro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/respuestas-guardadas")
public class RespuestaGuardadaController {

    @Autowired
    private RespuestaGuardadaRegistro respuestaGuardadaRegistro;

    // Endpoint para consultar todas las respuestas guardadas
    @GetMapping
    public ResponseEntity<List<RespuestaGuardada>> obtenerRespuestasGuardadas() {
        List<RespuestaGuardada> respuestasGuardadas = respuestaGuardadaRegistro.obtenerRespuestasGuardadas();
        return ResponseEntity.ok(respuestasGuardadas);
    }
}