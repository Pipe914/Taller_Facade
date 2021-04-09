package com.canessa.maven.test.cuentas;

import com.canessa.maven.test.Usuario;

public class Admin extends Usuario {

    public Admin(String user, String pass) {
        super(user, pass);
    }
// Metodos Clase

    public String login(String user, String contra){
        if (username.equals(user) && password.equals(contra)){
            return("Ha ingresado correctamente al sistema. Empresa: " + user);
        }else{
            return("Los datos ingresados son incorrectos. Intente nuevamente");
        }
    }

    public String getTipoUsuario(){
        return ("Tipo: Empresa");
    }
    
}
