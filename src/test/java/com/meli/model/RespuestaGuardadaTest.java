package com.meli.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

public class RespuestaGuardadaTest {

    @Test
    public void testConstructorConArgumentos() {
        String forecastCode = "123";
        String forecastDescription = "Descripción del pronóstico";
        String buyerNotification = "True";

        RespuestaGuardada respuestaGuardada = new RespuestaGuardada(forecastCode, forecastDescription, buyerNotification);

        assertEquals(forecastCode, respuestaGuardada.getForecastCode());
        assertEquals(forecastDescription, respuestaGuardada.getForecastDescription());
        assertEquals(buyerNotification, respuestaGuardada.getBuyerNotification());
        assertNotNull(respuestaGuardada.getFechaHoraRegistro());
    }

    @Test
    public void testConstructorSinArgumentos() {
        RespuestaGuardada respuestaGuardada = new RespuestaGuardada();

        assertEquals("", respuestaGuardada.getForecastCode());
        assertEquals("", respuestaGuardada.getForecastDescription());
        assertEquals("", respuestaGuardada.getBuyerNotification());
        assertNotNull(respuestaGuardada.getFechaHoraRegistro());
    }

    @Test
    public void testSettersAndGetters() {
        RespuestaGuardada respuestaGuardada = new RespuestaGuardada();

        String forecastCode = "456";
        String forecastDescription = "Otra descripción del pronóstico";
        String buyerNotification = "False";
        LocalDateTime fechaHoraRegistro = LocalDateTime.of(2024, 6, 30, 10, 30);

        respuestaGuardada.setForecastCode(forecastCode);
        respuestaGuardada.setForecastDescription(forecastDescription);
        respuestaGuardada.setBuyerNotification(buyerNotification);
        respuestaGuardada.setFechaHoraRegistro(fechaHoraRegistro);

        assertEquals(forecastCode, respuestaGuardada.getForecastCode());
        assertEquals(forecastDescription, respuestaGuardada.getForecastDescription());
        assertEquals(buyerNotification, respuestaGuardada.getBuyerNotification());
        assertEquals(fechaHoraRegistro, respuestaGuardada.getFechaHoraRegistro());
    }
}
