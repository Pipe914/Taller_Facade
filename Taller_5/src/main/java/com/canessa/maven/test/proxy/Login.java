package com.canessa.maven.test.proxy;

import com.canessa.maven.test.Facade;
import com.canessa.maven.test.Usuario;

public class Login implements ILogin{
    private static Login instance;

    public static Login getLogin() {
        if (instance == null){
            instance = new Login();
            System.out.println("Creado y cargado");
        }else{
            System.out.println("Existe, y cargado");
        }
        
        return instance;
    }
    // Constructors
    private Login() {
        
    }

    public String verifyLogin(Usuario user, String username, String password){
        
        if(user.getUsername().equals(username)&&user.getPassword().equals(password)){
            String key = createKey();
            Facade controlador = Facade.getFacade();
            System.out.println(user.login(username, password));
            controlador.login(user,key);
            return key;
        }else{
            System.out.println("Los credenciales utilizados son incorrectos, intente nuevamente");
            return "";
        }
    }

    private String createKey(){
        int numero = (int) Math.random()*50+1; 
        return String.valueOf(numero);
    }

}
