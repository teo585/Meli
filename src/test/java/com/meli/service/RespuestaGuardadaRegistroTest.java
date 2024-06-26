package com.meli.service;

import com.meli.model.RespuestaGuardada;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

public class RespuestaGuardadaRegistroTest {

    @Spy
    private List<RespuestaGuardada> respuestasGuardadas; // Uso de Spy para verificar métodos en la lista

    @InjectMocks
    private RespuestaGuardadaRegistro servicio;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAgregarRespuestaGuardada() {
        RespuestaGuardada respuestaGuardada = new RespuestaGuardada("123", "Descripción 1", "True");

        servicio.agregarRespuestaGuardada(respuestaGuardada);

        // Verificar que se agregó la respuesta guardada a la lista
        verify(respuestasGuardadas).add(respuestaGuardada);
    }
}
