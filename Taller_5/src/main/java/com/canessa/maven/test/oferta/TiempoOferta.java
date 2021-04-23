package com.canessa.maven.test.oferta;

import com.canessa.maven.test.Componente;

// Clase propiedad oferta: Descripcion
public class TiempoOferta extends ModificadorOferta{
// Variables globales
    private String tiempo;

// Constructor
    public TiempoOferta(Componente nuevaOferta, String tiempo) {
        super(nuevaOferta);
        this.tiempo = tiempo;
    }

// Metodos de clase
    public String imprimirOferta(){
        return nuevaOferta.imprimirOferta() + addTiempoOferta();
    }

    private String addTiempoOferta(){
        return "El tiempo del contrato es: " + this.tiempo + "\n";
    }
    public String optionalGetId(){
        return nuevaOferta.optionalGetId();
    }
}
