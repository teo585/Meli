package com.meli.util;

import java.util.List;

public class CodigosPronosticoUtil {

    // Lista estática final de códigos de pronóstico
    public static final List<String> CODIGOS_PRONOSTICO = List.of("1186", "1189", "1192", "1195");

    // Método para verificar si un código de pronóstico está contenido en la lista
    public static boolean contieneCodigo(String codigo) {
        System.out.println("codigoPronsotico util");
        System.out.println(codigo);
        return CODIGOS_PRONOSTICO.contains(codigo);
    }
}
