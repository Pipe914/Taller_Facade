package com.canessa.maven.test.factorys; 


import java.util.ArrayList;
import java.util.HashMap;

import com.canessa.maven.test.oferta.*;

public class OfertaFactory{
    private HashMap listOferta = new HashMap();

    public void SaveOferta(int key, Oferta oferta)
    {
        listOferta.put(key, oferta);

    }
    public Oferta GetOferta(int key){
        return (Oferta)listOferta.get(key); 
    }
}