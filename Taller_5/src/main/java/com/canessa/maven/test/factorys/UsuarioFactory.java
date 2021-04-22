package com.canessa.maven.test.factorys; 

import java.util.HashMap;

import com.canessa.maven.test.Usuario;

public class UsuarioFactory{
    private HashMap listUsuario = new HashMap();

    public void saveUsuario(String username, Usuario user)
    {
        listUsuario.put(username, user);

    }
    public Usuario getUsuario(String username){
        return (Usuario)listUsuario.get(username); 
    }
}