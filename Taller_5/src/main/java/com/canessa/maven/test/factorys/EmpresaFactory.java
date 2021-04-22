package com.canessa.maven.test.factorys;

import java.util.HashMap;

import com.canessa.maven.test.Empresa.Empresa;

public class EmpresaFactory {
    private HashMap list = new HashMap();

    public void saveEmpresa(int index, Empresa empresa) {
        list.put(index, empresa);
    }
    public Empresa getEmpresa(int index){
        return (Empresa) list.get(index);
    }
}
