package com.meli.service;

import com.meli.model.RespuestaGuardada;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RespuestaGuardadaRegistro {

    private List<RespuestaGuardada> respuestasGuardadas = new ArrayList<>();

    public void agregarRespuestaGuardada(RespuestaGuardada respuestaGuardada) {
        respuestasGuardadas.add(respuestaGuardada);
    }

    public List<RespuestaGuardada> obtenerRespuestasGuardadas() {
        return respuestasGuardadas;
    }
}