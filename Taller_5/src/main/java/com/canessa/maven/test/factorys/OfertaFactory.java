package com.canessa.maven.test.factorys;

import java.util.HashMap;

import com.canessa.maven.test.Componente;

public class OfertaFactory {
    private HashMap list = new HashMap();

    public void saveEmpresa(int index, Componente oferta) {
        list.put(index, oferta);
    }
    public Componente getEmpresa(int index){
        return (Componente) list.get(index);
    }
}
