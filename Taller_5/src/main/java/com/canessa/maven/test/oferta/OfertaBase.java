package com.canessa.maven.test.oferta;

import com.canessa.maven.test.Componente;

public class OfertaBase implements Componente{
    public String createUser(String user, String password){
        return "";
    }
    @Override
    public String imprimirOferta() {
        return "La oferta laboral consta de:\n ";
    }
}
