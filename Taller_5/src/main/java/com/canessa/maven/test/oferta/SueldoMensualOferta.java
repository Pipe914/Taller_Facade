package com.canessa.maven.test.oferta;

import com.canessa.maven.test.Componente;

public class SueldoMensualOferta extends ModificadorOferta{

    private String sueldo;

    public SueldoMensualOferta(Componente nuevaOferta, String sueldo){
        super(nuevaOferta);
        this.sueldo = sueldo;
    }

    public String imprimirOferta(){
        return nuevaOferta.imprimirOferta() + addSueldo();
    }

    private String addSueldo(){
        return "El sueldo mensual del contrato es: " + this.sueldo + "\n";
    }
    public String optionalGetId(){
        return nuevaOferta.optionalGetId();
    }
    
}
