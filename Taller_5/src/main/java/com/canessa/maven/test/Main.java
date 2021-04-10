package com.canessa.maven.test;

import com.canessa.maven.test.Empresa.Empresa;

public class Main 
{
    public static void main( String[] args )
    {
    // Creacion de Users
        Facade controlador = Facade.getFacade();
        Usuario admin = controlador.createUser(1,"Sabana", "1234");
        Usuario aspirante = controlador.createUser(2, "Andres", "1234");
    // Verificacion Login
        controlador.login(admin, "Sabana", "1234");
        controlador.login(aspirante, "Andres", "1234");
    //Creacion empresas
        Empresa empresa1 = controlador.createEmpresa("123524", "KFC", "Calle siempre viva");
        Empresa empresa2 = controlador.createEmpresa("12689564", "PPC", "Donde vive su madre");
    //Creacion oferta
        // Full
            Componente oferta1 = controlador.createOferta("Trabajo", "Servicios", "Indefinido", "500000");
        // Base + Propiedades
            Componente oferta2 = controlador.createOfertaBase();
            oferta2 = controlador.addPropiedadOferta(1, oferta2, "Muchas cosas wuu!!");
    //Guardar Empresa/Oferta
        controlador.addOferta(empresa1, oferta1);
        controlador.addOferta(empresa2, oferta2);
        controlador.addEmpresa(empresa1, empresa2);
    //Imprimir Ofertas
        controlador.imprimirOferta(empresa1);

    }
}
// Los llamados solo pueden ser por un metodos, despues se sub divide
