package com.canessa.maven.test.oferta;

import com.canessa.maven.test.Componente;

public class TiempoOferta extends ModificadorOferta{

    private String tiempo;

    public TiempoOferta(Componente nuevaOferta, String tiempo) {
        super(nuevaOferta);
        this.tiempo = tiempo;
    }

    public String imprimirOferta(){
        return nuevaOferta.imprimirOferta() + addColor();
    }

    private String addColor(){
        return "El tiempo del contrato es: " + this.tiempo + "\n";
    }
    public String optionalGetId(){
        return nuevaOferta.optionalGetId();
    }
}
