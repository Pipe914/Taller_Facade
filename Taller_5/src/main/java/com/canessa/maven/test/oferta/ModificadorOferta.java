package com.canessa.maven.test.oferta;

import com.canessa.maven.test.Componente;

// Clase modificadora de oferta
public abstract class ModificadorOferta implements Componente{
// Variables globales
    protected Componente nuevaOferta;

// Constructor
    public ModificadorOferta (Componente nuevaOferta){
        this.nuevaOferta = nuevaOferta;
    }

// Metodos de clase
    public String imprimirOferta(){
        return nuevaOferta.imprimirOferta();
    }
    public String optionalGetId(){
        return nuevaOferta.optionalGetId();
    }
}
