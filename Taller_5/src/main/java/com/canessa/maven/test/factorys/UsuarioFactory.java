package com.canessa.maven.test.factorys; 

import java.util.HashMap;

import com.canessa.maven.test.Usuario;

public class UsuarioFactory{
    private HashMap listUsuario = new HashMap();

    public void saveUsuario(String key, Usuario user)
    {
        listUsuario.put(key, user);

    }
    public Usuario getUsuario(String key){
        return (Usuario)listUsuario.get(key); 
    }
}