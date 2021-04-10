package com.canessa.maven.test.proxy;

import com.canessa.maven.test.Usuario;

public class Login implements ILogin{
    public int verifyLogin(Usuario user, String username, String password){
        
        if(user.getUsername().equals(username)&&user.getPassword().equals(password)){
            System.out.println(user.login(username, password));

            return createKey();
        }else{
            System.out.println("Los credenciales utilizados son incorrectos, intente nuevamente");
            return 0;
        }

        
    }

    public int createKey(){
        int key = 5;
        return key;
    }
}
