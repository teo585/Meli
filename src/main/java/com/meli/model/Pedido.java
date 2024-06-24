package com.meli.model;

import javax.validation.constraints.NotNull;

public class Pedido {

    @NotNull(message = "La latitud no puede ser nula")
    private Double latitud;

    @NotNull(message = "La longitud no puede ser nula")
    private Double longitud;

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
}
