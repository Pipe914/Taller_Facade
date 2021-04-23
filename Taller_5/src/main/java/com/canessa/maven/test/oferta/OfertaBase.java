package com.canessa.maven.test.oferta;

import com.canessa.maven.test.Componente;

// Clase base oferta
public class OfertaBase implements Componente{
// Variables globales
    private String id;

// Constructor
    public OfertaBase(String iD){
        id = iD;
    }
    
// Metodos de clase
    public void setId(String iD){
        id = iD;
    }

    public String getId(){
        return id;
    }
    @Override
    public String imprimirOferta() {
        return "La oferta laboral consta de:\n";
    }
    public String optionalGetId(){
        return getId();
    }
}
