package com.meli.controller;

import com.meli.model.RespuestaGuardada;
import com.meli.service.RespuestaGuardadaRegistro;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class RespuestaGuardadaControllerTest {

    @Mock
    private RespuestaGuardadaRegistro respuestaGuardadaRegistro;

    @InjectMocks
    private RespuestaGuardadaController controller;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testObtenerRespuestasGuardadas() {
        // Datos de ejemplo
        RespuestaGuardada respuesta1 = new RespuestaGuardada("123", "Descripción 1", "True");
        RespuestaGuardada respuesta2 = new RespuestaGuardada("456", "Descripción 2", "False");
        List<RespuestaGuardada> respuestas = new ArrayList<>();
        respuestas.add(respuesta1);
        respuestas.add(respuesta2);

        // Configurar el comportamiento simulado del servicio
        when(respuestaGuardadaRegistro.obtenerRespuestasGuardadas()).thenReturn(respuestas);

        // Llamar al método del controlador
        ResponseEntity<List<RespuestaGuardada>> responseEntity = controller.obtenerRespuestasGuardadas();

        // Verificar el estado de la respuesta
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // Verificar los datos devueltos
        List<RespuestaGuardada> respuestasDevueltas = responseEntity.getBody();
        assertEquals(respuestas.size(), respuestasDevueltas.size());
        assertEquals(respuesta1.getForecastCode(), respuestasDevueltas.get(0).getForecastCode());
        assertEquals(respuesta2.getForecastDescription(), respuestasDevueltas.get(1).getForecastDescription());
        // Asegúrate de verificar otros campos según sea necesario
    }
}
