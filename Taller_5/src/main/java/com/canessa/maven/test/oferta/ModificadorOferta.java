package com.canessa.maven.test.oferta;

import com.canessa.maven.test.Componente;

public abstract class ModificadorOferta implements Componente{
    
    protected Componente nuevaOferta;

    public ModificadorOferta (Componente nuevaOferta){
        this.nuevaOferta = nuevaOferta;
    }

    public String imprimirOferta(){
        return nuevaOferta.imprimirOferta();
    }
}
