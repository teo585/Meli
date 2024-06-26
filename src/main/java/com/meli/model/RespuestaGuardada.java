package com.meli.model;

public class RespuestaGuardada {
    private String forecastCode;
    private String forecastDescription;
    private String buyerNotification;

    // Getters y Setters
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
}
