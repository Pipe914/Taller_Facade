package com.canessa.maven.test.oferta;

import com.canessa.maven.test.Componente;

public class TipoContratoOferta extends ModificadorOferta{

    private String tipoContrato;

    public TipoContratoOferta(Componente nuevaOferta, String tipoContrato) {
        super(nuevaOferta);
        this.tipoContrato = tipoContrato;
    }

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
