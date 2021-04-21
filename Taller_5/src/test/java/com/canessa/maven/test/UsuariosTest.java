package com.canessa.maven.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import com.canessa.maven.test.cuentas.Admin;
import com.canessa.maven.test.cuentas.Aspirante;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class UsuariosTest 
{
    /**
     * Rigorous Test :-)
     */

    //Objetos a usar
    Usuario aspirante = new Aspirante("Andres", "1234");
    Usuario admin =  new Admin("Sabana", "1234");

    @Test
    public void testCreaAspirante(){
        String nombre = aspirante.getUsername();
        String password = aspirante.getPassword();
        assertEquals(nombre,"Andres");
        assertEquals(password, "1234");
    }

    @Test
    public void testGetUsernameAspirante(){
        assertEquals(aspirante.getUsername(), "Andres");
    }

    @Test
    public void testGetPasswordAspirante(){
        assertEquals(aspirante.getPassword(), "1234");
    }

    @Test
    public void testSetUsernameAspirante(){
        aspirante.setUsername("Juan");
        assertNotEquals("Andres", "Juan");
    }

    @Test 
    public void testSetPasswordAspirante(){
        aspirante.setPassword("123");
        assertNotEquals("1234", "1233");
    }

    @Test 
    public void testGetTipoUsuarioAspirante(){
        assertEquals(aspirante.getTipoUsuario(),"Tipo: Aspirante");
    }

    @Test
    public void testLoginAspirante(){
        String respuesta = aspirante.login("Andres", "1234");
        assertEquals(respuesta, "Ha ingresado correctamente al sistema. Aspirante: " + aspirante.getUsername());
    }

    @Test
    public void testCreaAdmin(){
        String nombre = admin.getUsername();
        String password = admin.getPassword();
        assertEquals(nombre,"Sabana");
        assertEquals(password, "1234");
    }

    @Test
    public void testGetUsernameAdmin(){
        assertEquals(admin.getUsername(), "Sabana");
    }

    @Test
    public void testGetPassWordAdmin(){
        assertEquals(admin.getPassword(), "1234");
    }

    @Test
    public void testSetUsernameAdmin(){
        admin.setUsername("Andes");
        assertNotEquals("Sabana", "Andes");
    }

    @Test 
    public void testSetPasswordAdmin(){
        admin.setPassword("123");
        assertNotEquals("1234", "1233");
    }

    @Test 
    public void testGetTipoUsuarioAdmin(){
        assertEquals(admin.getTipoUsuario(), "Tipo: Empresa");
        
    }

    @Test
    public void testLoginAdmin(){
        String respuesta = admin.login("Sabana", "1234");
        assertEquals(respuesta, "Ha ingresado correctamente al sistema. Empresa: " + admin.getUsername());
    }
}
