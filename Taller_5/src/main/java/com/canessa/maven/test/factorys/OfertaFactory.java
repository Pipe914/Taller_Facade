package com.canessa.maven.test.factorys;

import java.util.HashMap;

import com.canessa.maven.test.Componente;

public class OfertaFactory {
    private HashMap list = new HashMap();

    public void saveOferta(String id, Componente oferta) {
        list.put(id, oferta);
    }
    public Componente getOferta(String id){
        return (Componente) list.get(id);
    }
    public int getSize(){
        return list.size();
    }
}
