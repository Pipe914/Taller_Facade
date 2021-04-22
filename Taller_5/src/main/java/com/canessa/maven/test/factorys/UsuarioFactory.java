package com.canessa.maven.test.factorys; 


import java.util.ArrayList;
import java.util.HashMap;

import com.canessa.maven.test.Usuario;

public class UsuarioFactory{
    private HashMap listUsuario = new HashMap();

    public void SaveAspirante(int key, Usuario user)
    {
        listUsuario.put(key, user);

    }
    public Usuario GetUsuario(int key){
        return (Usuario)listUsuario.get(key); 
    }
}