package com.canessa.maven.test.oferta;

import com.canessa.maven.test.Componente;

public class DescripcionOferta extends ModificadorOferta{
    
    private String descripcion;

    public DescripcionOferta(Componente nuevaOferta, String descripcion){
        super(nuevaOferta);
        this.descripcion = descripcion;
    }

    public String imprimirOferta(){
        return nuevaOferta.imprimirOferta() + addDescripcion();
    }

    private String addDescripcion(){
        return "\nLa descripción es: "+ this.descripcion +"\n";
    }
    public String optionalGetId(){
        return nuevaOferta.optionalGetId();
    }

}
