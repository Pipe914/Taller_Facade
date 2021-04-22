package com.canessa.maven.test.proxy;

import com.canessa.maven.test.Facade;
import com.canessa.maven.test.Usuario;

// Clase de login tambien llamado proxy
public class Login implements ILogin {
// Variables globales
    private static Login instance;

// Singlenton
    public static Login getLogin() {
        if (instance == null){
            instance = new Login();
            System.out.println("Creado y cargado");
        }else{
            System.out.println("Existe, y cargado");
        }
        
        return instance;
    }
// Constructor
    private Login() {
        
    }

// Metodos de Clase
    // Metodo de login
    public String verifyLogin(Usuario user, String username, String password){
        // Se verifica la informacion de login (username y password)
          if(user.getUsername().equals(username)&&user.getPassword().equals(password)){
            // Se crea la key de la sesion aleatoria
            String key = createKey();
            // Se llama la instancia del FaceIt
            Facade controlador = Facade.getFacade();
            // Se imprime el metodo login del usuario correspondiente
            System.out.println(user.login(username, password));
            // Se envia el usuario y la key generada al faceit para que sea almacenada
            controlador.login(user,key);
            // Se retorna al frontEnd la key
            return key;
        }else{
            System.out.println("Los credenciales utilizados son incorrectos, intente nuevamente");
            return "";
        }
    }

    // Metodo de creacion de key
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
