package com.canessa.maven.test.proxy;

import com.canessa.maven.test.Facade;
import com.canessa.maven.test.Usuario;

public class Login implements ILogin {
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
        int a=2, b=100, numero; 
        while(true)
                {
                 numero =(int) (Math.random()*(b-a+1)+a);   
                 if(verificarNum(numero)&& numero!=0){
                   break;  
                 }
                }
        return String.valueOf(numero);
    }
    public boolean verificarNum(int n){
        int suma=0; 
        for (int i = 2; i < n; i++) {
           if(n%i==0)
           {
               suma++;
            break;
           }
        }
        if(suma==0)
            return true;
        
        else
            return false; 
    }
}
