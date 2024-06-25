package com.meli.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class CodigosPronosticoUtilTest {

    @Test
    void contieneCodigo_DeberiaRetornarTrue_CuandoCodigoExisteEnLista() {
        // Arrange
        String codigoExistente = "1189";

        // Act
        boolean resultado = CodigosPronosticoUtil.contieneCodigo(codigoExistente);

        // Assert
        assertTrue(resultado);
    }

    @Test
    void contieneCodigo_DeberiaRetornarFalse_CuandoCodigoNoExisteEnLista() {
        // Arrange
        String codigoNoExistente = "1234";

        // Act
        boolean resultado = CodigosPronosticoUtil.contieneCodigo(codigoNoExistente);

        // Assert
        assertFalse(resultado);
    }

    @Test
    void codigosPronostico_DeberiaTenerCuatroElementos() {
        // Arrange y Act
        List<String> codigos = CodigosPronosticoUtil.CODIGOS_PRONOSTICO;

        // Assert
        assertEquals(4, codigos.size());
    }
}
