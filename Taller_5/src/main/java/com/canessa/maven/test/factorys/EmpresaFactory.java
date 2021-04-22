package com.canessa.maven.test.factorys;

import java.util.HashMap;

import com.canessa.maven.test.Usuario;
import com.canessa.maven.test.Empresa.Empresa;

public class EmpresaFactory {
    private HashMap list = new HashMap();

    public void saveEmpresa(String nit, Empresa empresa) {
        list.put(nit, empresa);
    }
    public Empresa getEmpresa(String nit){
        return (Empresa) list.get(nit);
    }
}
 