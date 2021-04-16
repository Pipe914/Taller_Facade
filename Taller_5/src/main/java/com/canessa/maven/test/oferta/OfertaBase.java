package com.canessa.maven.test.oferta;

import com.canessa.maven.test.Componente;

public class OfertaBase implements Componente{
    private String id;

    public OfertaBase(String iD){
        id = iD;
    }
    
    public void setId(String iD){
        id = iD;
    }

    public String getId(){
        return id;
    }
    @Override
    public String imprimirOferta() {
        return "La oferta laboral consta de:\n ";
    }
    public String optionalGetId(){
        return getId();
    }
}
