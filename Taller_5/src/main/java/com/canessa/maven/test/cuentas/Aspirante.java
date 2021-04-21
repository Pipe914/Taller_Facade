package com.canessa.maven.test.cuentas;

import com.canessa.maven.test.Usuario;

public class Aspirante extends Usuario {
    public Aspirante(String user, String pass){
        super(user, pass);
    };
//Metodos de Clase

    public String login(String user, String contra){
        if (username.equals(user) && password.equals(contra)){
            return ("Ha ingresado correctamente al sistema. Aspirante: " + user);
        }else{
            return ("Los datos ingresados son incorrectos. Intente nuevamente");
        }
    }

//Getters
    public String getTipoUsuario(){
        return "Tipo: "+getClass().getSimpleName();
    }
    
}
