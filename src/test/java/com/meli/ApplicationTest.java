package com.meli;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ApplicationTest {

    @Test
    void contextLoads() {
        // Este método vacío sirve como indicador de que el contexto de la aplicación se carga correctamente
    }

    @Test
    void mainMethodStartsApplication() {
        // Verificar que la ejecución del método main inicie la aplicación correctamente
        assertDoesNotThrow(() -> Application.main(new String[]{}));
    }
}
