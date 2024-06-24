package com.meli.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class Pedido {

    @NotBlank(message = "El correo electrónico no puede estar en blanco")
    @Email(message = "Debe ingresar un correo electrónico válido")
    private String correoElectronico;

    @NotBlank(message = "La latitud no puede estar en blanco")
    private String latitud;

    @NotBlank(message = "La longitud no puede estar en blanco")
    private String longitud;

    // Getters y Setters
    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }
}
