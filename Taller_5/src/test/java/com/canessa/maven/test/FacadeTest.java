package com.canessa.maven.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


import com.canessa.maven.test.cuentas.Admin;
import com.canessa.maven.test.cuentas.Aspirante;
import com.canessa.maven.test.proxy.AESEncript;
import com.canessa.maven.test.proxy.Login;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class FacadeTest 
{
    /**
     * Rigorous Test :-)
     */

    //Objetos a usar
    Facade controlador = Facade.getFacade();
    Login proxy = Login.getLogin();
    Usuario admin = new Admin("Andres", "123");
    Usuario aspirante = new Aspirante("Juan", "123");
    String key1 = proxy.verifyLogin(admin, "Andres", "123");
    String key2 = proxy.verifyLogin(aspirante, "Juan", "123");

    @Test
    public void testLogOut(){
        assertEquals("Su sesion ha sido cerrada correctamente, vuelva pronto", AESEncript.decrypt(controlador.action(AESEncript.encrypt("2/" + key1))));
    }

    @Test
    public void testLogOutFallida(){
        assertNotEquals("No se ha podido cerrar la sesion. Intente nuevamente", AESEncript.decrypt(controlador.action(AESEncript.encrypt("2/" + key1))));
    }

    @Test
    public void testCrearEmpresa(){
        assertEquals("La empresa se ha creado exitosamente", AESEncript.decrypt(controlador.action(AESEncript.encrypt("3/"+key1+"/123524/KFC/Calle siempre viva"))));
    }

    @Test
    public void testCrearEmpresaNoAcceso(){
        assertEquals("Usted no tiene acceso para realizar esta acción!!!",AESEncript.decrypt(controlador.action(AESEncript.encrypt("3/"+key2+"/123524/KFC/Calle siempre viva"))));
    }

    @Test
    public void testCrearEmpresaNoExisteSesion(){
        assertNotEquals("La sesion no existe, ha caducado.", AESEncript.decrypt(controlador.action(AESEncript.encrypt("3/"+key2+"/123524/KFC/Calle siempre viva"))));
    }

    @Test
    public void testCrearOfertaBase(){
        assertEquals("La oferta base, ha sido creada correctamente. El ID de la oferta es: "
        + "1", AESEncript.decrypt(controlador.action(AESEncript.encrypt("4/"+key1))));
    }

    @Test
    public void testCrearOfertaBaseNoAcceso(){
        assertEquals("Usted no tiene acceso para realizar esta acción!!!", AESEncript.decrypt(controlador.action(AESEncript.encrypt("4/"+key2))));
    }

    @Test
    public void testCrearOfertaBaseNoExisteSesion(){
        assertNotEquals("La sesion no existe, ha caducado.", AESEncript.decrypt(controlador.action(AESEncript.encrypt("4/"+key2))));
    }

    @Test
    public void testCrearOfertaCompleta(){
        assertEquals("La oferta completa, ha sido creada correctamente. El ID de la oferta es: "
        + "1", AESEncript.decrypt(controlador.action(AESEncript.encrypt("5/"+key1+"/Trabajo/Servicios/Indefinido/500000"))));
    }

    @Test
    public void testCrearOfertaCompletaNoAcceso(){
        assertEquals("Usted no tiene acceso para realizar esta acción!!!", 
        AESEncript.decrypt(controlador.action(AESEncript.encrypt("5/"+key2+"/Trabajo/Servicios/Indefinido/500000"))));
    }

    @Test
    public void testCrearOfertaCompletaNoExisteSesion(){
        assertNotEquals("La sesion no existe, ha caducado.", AESEncript.decrypt(controlador.action(AESEncript.encrypt("5/"+key1+"/Trabajo/Servicios/Indefinido/500000"))));
    }
    

    @Test
    public void testAddPropiedadOferta(){
        AESEncript.decrypt(controlador.action(AESEncript.encrypt("4/"+key1)));
        assertEquals("Se ha añadido la propiedad correctamente",AESEncript.decrypt(controlador.action(AESEncript.encrypt("6/"+key1+"/1/1/Muchas cosas wuu!!"))));
    }


    @Test
    public void testAddPropiedadOfertaErrorSeleccion(){
        AESEncript.decrypt(controlador.action(AESEncript.encrypt("4/"+key1)));
        assertEquals("Escoja una eleccion valida", AESEncript.decrypt(controlador.action(AESEncript.encrypt("6/"+key1+"/5/1/Muchas cosas wuu!!"))));
    }

    @Test 
    public void testAddPropiedadOfertaNoAcceso(){
        assertEquals("Usted no tiene acceso para realizar esta acción!!!",
        AESEncript.decrypt(controlador.action(AESEncript.encrypt("6/"+key2+"/1/1/Muchas cosas wuu!!"))));
    }

    @Test
    public void testAddPropiedadOfertaNoExisteSesion(){
        assertNotEquals("La sesion no existe, ha caducado.", AESEncript.decrypt(controlador.action(AESEncript.encrypt("6/"+key1+"/1/1/Muchas cosas wuu!!"))));
    }

    @Test
    public void testAddEmpresa(){
        AESEncript.decrypt(controlador.action(AESEncript.encrypt("3/"+key1+"/123524/KFC/Calle siempre viva")));
        AESEncript.decrypt(controlador.action(AESEncript.encrypt("3/"+key1+"/12689564/Arturo/Calle siempre viva")));
        assertEquals("Se ha añadido la empresa correctamente", AESEncript.decrypt(controlador.action(AESEncript.encrypt("7/"+key1+"/123524/12689564"))));
    }

    @Test
    public void testAddEmpresaErrorInfoNoRegistrada(){
        AESEncript.decrypt(controlador.action(AESEncript.encrypt("3/"+key1+"/123524/KFC/Calle siempre viva")));
        AESEncript.decrypt(controlador.action(AESEncript.encrypt("3/"+key1+"/12689564/Arturo/Calle siempre viva")));
        assertEquals("La informacion de las empresas que ha ingresado no se encuentra registrada. Intente nuevamente", AESEncript.decrypt(controlador.action(AESEncript.encrypt("7/"+key1+"/12352/1268954"))));
    }

    @Test
    public void testAddEmpresaNoAcceso(){
        AESEncript.decrypt(controlador.action(AESEncript.encrypt("3/"+key1+"/123524/KFC/Calle siempre viva")));
        AESEncript.decrypt(controlador.action(AESEncript.encrypt("3/"+key1+"/12689564/Arturo/Calle siempre viva")));
        assertEquals("Usted no tiene acceso para realizar esta acción!!!", AESEncript.decrypt(controlador.action(AESEncript.encrypt("7/"+key2+"/123524/12689564"))));
    }

    @Test
    public void testAddEmpresaNoExisteSesion(){
        AESEncript.decrypt(controlador.action(AESEncript.encrypt("3/"+key1+"/123524/KFC/Calle siempre viva")));
        AESEncript.decrypt(controlador.action(AESEncript.encrypt("3/"+key1+"/12689564/Arturo/Calle siempre viva")));
        assertNotEquals("La sesion no existe, ha caducado.", AESEncript.decrypt(controlador.action(AESEncript.encrypt("7/"+key1+"/123524/12689564"))));
    }

    @Test
    public void testAddOferta(){
        AESEncript.decrypt(controlador.action(AESEncript.encrypt("4/"+key1)));
        AESEncript.decrypt(controlador.action(AESEncript.encrypt("3/"+key1+"/123524/KFC/Calle siempre viva")));
        assertEquals("Se ha añadido la oferta correctamente", AESEncript.decrypt(controlador.action(AESEncript.encrypt("8/"+key1+"/123524/1"))));
    }

    @Test
    public void testAddOfertaErrorInfoNoRegistrada(){
        AESEncript.decrypt(controlador.action(AESEncript.encrypt("4/"+key1)));
        AESEncript.decrypt(controlador.action(AESEncript.encrypt("3/"+key1+"/123524/KFC/Calle siempre viva")));
        assertEquals("La informacion de las empresas que ha ingresado no se encuentra registrada. Intente nuevamente", AESEncript.decrypt(controlador.action(AESEncript.encrypt("8/"+key1+"/12352/1"))));
    }

    @Test
    public void testAddOfertaNoAcceso(){
        AESEncript.decrypt(controlador.action(AESEncript.encrypt("4/"+key1)));
        AESEncript.decrypt(controlador.action(AESEncript.encrypt("3/"+key1+"/123524/KFC/Calle siempre viva")));
        assertEquals("Usted no tiene acceso para realizar esta acción!!!", AESEncript.decrypt(controlador.action(AESEncript.encrypt("8/"+key2+"/123524/1"))));
    }

    @Test 
    public void testAddOfertaNoExisteSesion(){
        AESEncript.decrypt(controlador.action(AESEncript.encrypt("4/"+key1)));
        AESEncript.decrypt(controlador.action(AESEncript.encrypt("3/"+key1+"/123524/KFC/Calle siempre viva")));
        assertNotEquals("La sesion no existe, ha caducado.",  AESEncript.decrypt(controlador.action(AESEncript.encrypt("8/"+key1+"/123524/1"))));
    }

    @Test
    public void testImprimirOferta(){
        AESEncript.decrypt(controlador.action(AESEncript.encrypt("4/"+key1)));
        AESEncript.decrypt(controlador.action(AESEncript.encrypt("3/"+key1+"/123524/KFC/Calle siempre viva")));
        assertNotNull(AESEncript.decrypt(controlador.action(AESEncript.encrypt("9/"+key1+"/123524"))));
    }

    @Test
    public void testImprimirOfertaEmpresaNoRegistrada(){
        AESEncript.decrypt(controlador.action(AESEncript.encrypt("4/"+key1)));
        AESEncript.decrypt(controlador.action(AESEncript.encrypt("3/"+key1+"/123524/KFC/Calle siempre viva")));
        assertEquals("La empresa no se encuentra registrada.", AESEncript.decrypt(controlador.action(AESEncript.encrypt("9/"+key1+"/12524"))));
    }

    @Test
    public void testImprimirOfertaNoExisteSesion(){
        assertNotEquals("La sesion no existe, ha caducado.",  AESEncript.decrypt(controlador.action(AESEncript.encrypt("9/"+key1+"/123524"))));
    }

}
