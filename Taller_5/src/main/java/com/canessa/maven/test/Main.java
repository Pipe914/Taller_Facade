package com.canessa.maven.test;

import com.canessa.maven.test.proxy.Login;

public class Main {
    public static void main(String[] args) {
        // Creacion de Users
        Facade controlador = Facade.getFacade();
        Login login = Login.getLogin();
        Usuario admin = controlador.createUser(1, "Sabana", "1234");
        Usuario aspirante = controlador.createUser(2, "Andres", "1234");
        // Verificacion Login
        String key1 = login.verifyLogin(admin, "Sabana", "1234");
        String key2 = login.verifyLogin(aspirante, "Andres", "1234");

        // Creacion empresas
        controlador.createEmpresa(key1, "123524", "KFC", "Calle siempre viva");
        controlador.createEmpresa(key1, "12689564", "PPC", "Donde vive su madre");
        // Creacion oferta
        // Full
        controlador.createOferta(key1, "Trabajo", "Servicios", "Indefinido", "500000");
        // Base + Propiedades
        controlador.createOfertaBase(key1);
        controlador.addPropiedadOferta(1, key1, "2", "Muchas cosas wuu!!");
        // Guardar Empresa/Oferta
        controlador.addEmpresa(key1, "123524", "12689564");
        controlador.addOferta(key1, "123524", "1");
        controlador.addOferta(key1, "12689564", "2");
        // Imprimir Ofertas
        controlador.imprimirOferta(key1, "123524");

    }
}

// Los llamados solo pueden ser por un metodos, despues se sub divide
// Encriptado de key y llamados
// Verificar funciones a las que es posible acceder cada usuario
