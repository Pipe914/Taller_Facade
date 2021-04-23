package com.canessa.maven.test.oferta;

import com.canessa.maven.test.Componente;

// Clase propiedad oferta: Sueldo Oferta
public class SueldoMensualOferta extends ModificadorOferta{
// Variables globales
    private String sueldo;

// Constructor
    public SueldoMensualOferta(Componente nuevaOferta, String sueldo){
        super(nuevaOferta);
        this.sueldo = sueldo;
    }

// Metodos de clase
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
