package com.meli.model;

import javax.validation.constraints.NotNull;

public class Pedido {

    @NotNull(message = "La latitud no puede ser nula")
    private Double latitud;

    @NotNull(message = "La longitud no puede ser nula")
    private Double longitud;

    @NotNull(message = "El correo electronico no puede ser nulo")
    String correoElectronico;

    // Getters y setters
    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }
}
