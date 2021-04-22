package com.canessa.maven.test;

import com.canessa.maven.test.proxy.Login;
import com.canessa.maven.test.proxy.AESEncript;

public class Main {
    public static void main(String[] args) {
        // Creacion de Users
        Facade controlador = Facade.getFacade();
        Login proxy = Login.getLogin();

        Usuario admin = controlador.createUser("1", "Sabana", "1234");
        Usuario aspirante = controlador.createUser("2", "Andres", "1234");
        // Verificacion Login
        String key1 = proxy.verifyLogin(admin, "Sabana", "1234");
        String key2 = proxy.verifyLogin(aspirante, "Andres", "1234");

        // Prueba LogOut
        //System.out.println(AESEncript.decrypt(controlador.action(AESEncript.encrypt("2/" + key1))));
        // Creacion empresas
        System.out.println(AESEncript.decrypt(controlador.action(AESEncript.encrypt("3/"+key1+"/123524/KFC/Calle siempre viva"))));
        System.out.println(AESEncript.decrypt(controlador.action(AESEncript.encrypt("3/"+key1+"/12689564/PPC/Donde vive su madre"))));
        // Creacion oferta
            // Full
            System.out.println(AESEncript.decrypt(controlador.action(AESEncript.encrypt("5/"+key1+"/Trabajo/Servicios/Indefinido/500000"))));
            // Base + Propiedades
            System.out.println(AESEncript.decrypt(controlador.action(AESEncript.encrypt("4/"+key1))));
            System.out.println(AESEncript.decrypt(controlador.action(AESEncript.encrypt("6/"+key1+"/1/2/Muchas cosas wuu!!"))));
        // Guardar Empresa/Oferta
        System.out.println(AESEncript.decrypt(controlador.action(AESEncript.encrypt("7/"+key1+"/123524/12689564"))));
        System.out.println(AESEncript.decrypt(controlador.action(AESEncript.encrypt("8/"+key1+"/123524/1"))));
        System.out.println(AESEncript.decrypt(controlador.action(AESEncript.encrypt("8/"+key1+"/12689564/2"))));
        // Imprimir Ofertas
        System.out.println(AESEncript.decrypt(controlador.action(AESEncript.encrypt("9/"+key1+"/123524"))));
   
    }
    
}

// Implementar Fly
/*  1. Terminar DIseño (UML) V-E
    2. Terminanr funcionalidad completa y presentar funcionalidad
    3. Terminar pruebas para todos los metodos
    4. Cambiar username por ID
*/