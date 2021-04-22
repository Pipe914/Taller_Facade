package com.canessa.maven.test.oferta;

import com.canessa.maven.test.Componente;

// Clase propiedad oferta: Descripcion
public class TipoContratoOferta extends ModificadorOferta{
// Variables globales
    private String tipoContrato;

// Constructor
    public TipoContratoOferta(Componente nuevaOferta, String tipoContrato) {
        super(nuevaOferta);
        this.tipoContrato = tipoContrato;
    }

// Metodos de clase
    public String imprimirOferta(){
        return nuevaOferta.imprimirOferta() + addTipoContrato();
    }

    private String addTipoContrato(){
        return "El tipo de contrato es: " + this.tipoContrato + "\n";
    }
    public String optionalGetId(){
        return nuevaOferta.optionalGetId();
    }
    
}
