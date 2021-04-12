package com.canessa.maven.test.proxy;

import com.canessa.maven.test.Usuario;

public interface ILogin {
    public String verifyLogin(Usuario user, String username, String password);
}