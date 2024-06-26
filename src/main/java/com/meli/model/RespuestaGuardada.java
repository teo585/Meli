package com.meli.model;

import java.time.LocalDateTime;

public class RespuestaGuardada {

    private String forecastCode;
    private String forecastDescription;
    private String buyerNotification;
    private LocalDateTime fechaHoraRegistro;

    // Constructor
    public RespuestaGuardada(String forecastCode, String forecastDescription, String buyerNotification) {
        this.forecastCode = forecastCode;
        this.forecastDescription = forecastDescription;
        this.buyerNotification = buyerNotification;
        this.fechaHoraRegistro = LocalDateTime.now(); // Inicializa con la fecha y hora actual
    }

    public RespuestaGuardada() {
        this.forecastCode = "";
        this.forecastDescription = "";
        this.buyerNotification = "";
        this.fechaHoraRegistro = LocalDateTime.now();
    }


    // Getters y setters
    public String getForecastCode() {
        return forecastCode;
    }

    public void setForecastCode(String forecastCode) {
        this.forecastCode = forecastCode;
    }

    public String getForecastDescription() {
        return forecastDescription;
    }

    public void setForecastDescription(String forecastDescription) {
        this.forecastDescription = forecastDescription;
    }

    public String getBuyerNotification() {
        return buyerNotification;
    }

    public void setBuyerNotification(String buyerNotification) {
        this.buyerNotification = buyerNotification;
    }

    public LocalDateTime getFechaHoraRegistro() {
        return fechaHoraRegistro;
    }

    public void setFechaHoraRegistro(LocalDateTime fechaHoraRegistro) {
        this.fechaHoraRegistro = fechaHoraRegistro;
    }
}
