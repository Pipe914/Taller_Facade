package com.canessa.maven.test.proxy;

import com.canessa.maven.test.Usuario;

// Interfaz para metodos proxy
public interface ILogin {
    // Metodo de verificacion de login
    public String verifyLogin(Usuario user, String username, String password);
    
}