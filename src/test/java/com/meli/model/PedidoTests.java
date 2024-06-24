package com.meli.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PedidoTests {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testLatitudNotNull() {
        Pedido pedido = new Pedido();
        pedido.setLongitud(10.0);
        pedido.setCorreoElectronico("correo@example.com");

        Set<ConstraintViolation<Pedido>> violations = validator.validate(pedido);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("La latitud no puede ser nula");
    }

    @Test
    public void testLongitudNotNull() {
        Pedido pedido = new Pedido();
        pedido.setLatitud(20.0);
        pedido.setCorreoElectronico("correo@example.com");

        Set<ConstraintViolation<Pedido>> violations = validator.validate(pedido);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("La longitud no puede ser nula");
    }

    @Test
    public void testCorreoElectronicoNotNull() {
        Pedido pedido = new Pedido();
        pedido.setLatitud(20.0);
        pedido.setLongitud(10.0);

        Set<ConstraintViolation<Pedido>> violations = validator.validate(pedido);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("El correo electronico no puede ser nulo");
    }

    @Test
    public void testPedidoValido() {
        Pedido pedido = new Pedido();
        pedido.setLatitud(20.0);
        pedido.setLongitud(10.0);
        pedido.setCorreoElectronico("correo@example.com");

        Set<ConstraintViolation<Pedido>> violations = validator.validate(pedido);

        assertThat(violations).isEmpty();
    }

    @Test
    public void testTodosLosCamposNulos() {
        Pedido pedido = new Pedido();

        Set<ConstraintViolation<Pedido>> violations = validator.validate(pedido);

        assertThat(violations).hasSize(3); // Latitud, longitud y correoElectronico deben ser nulos
    }

    @Test
    public void testGettersYSetters() {
        Pedido pedido = new Pedido();
        pedido.setLatitud(20.0);
        pedido.setLongitud(10.0);
        pedido.setCorreoElectronico("correo@example.com");

        assertThat(pedido.getLatitud()).isEqualTo(20.0);
        assertThat(pedido.getLongitud()).isEqualTo(10.0);
        assertThat(pedido.getCorreoElectronico()).isEqualTo("correo@example.com");
    }
}
