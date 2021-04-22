package com.canessa.maven.test.factorys;

import java.util.HashMap;

import com.canessa.maven.test.Usuario;
import com.canessa.maven.test.Empresa.Empresa;

public class EmpresaFactory {
    private HashMap list = new HashMap();

    public void saveEmpresa(Usuario key, Empresa empresa) {
        list.put(key, empresa);
    }
    public Empresa getEmpresa(Usuario key){
        return (Empresa) list.get(key);
    }
}
