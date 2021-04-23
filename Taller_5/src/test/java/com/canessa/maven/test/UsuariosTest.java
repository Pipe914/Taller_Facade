package com.canessa.maven.test;

import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

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
        assertEquals(aspirante.getUsername(),"Andres");
        assertEquals(aspirante.getPassword(), "1234");
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
        assertEquals(aspirante.getUsername(), "Juan");
    }

    @Test 
    public void testSetPasswordAspirante(){
        aspirante.setPassword("123");
        assertEquals(aspirante.getPassword(), "123");
    }

    @Test 
    public void testGetTipoUsuarioAspirante(){
        assertEquals(aspirante.getTipoUsuario(),"Tipo: Aspirante");
    }

    @Test
    public void testLoginAspirante(){
        assertEquals(aspirante.login("Andres", "1234"), "Ha ingresado correctamente al sistema. Aspirante: " + aspirante.getUsername());
    }

    @Test
    public void testCreaAdmin(){
        assertEquals(admin.getUsername(),"Sabana");
        assertEquals(admin.getPassword(), "1234");
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
        assertEquals(admin.getUsername(), "Andes");
    }

    @Test 
    public void testSetPasswordAdmin(){
        admin.setPassword("123");
        assertEquals(admin.getPassword(), "123");
    }

    @Test 
    public void testGetTipoUsuarioAdmin(){
        assertEquals(admin.getTipoUsuario(),"Tipo: Admin");
        
    }

    @Test
    public void testLoginAdmin(){
        assertEquals(admin.login("Sabana", "1234"), "Ha ingresado correctamente al sistema. Empresa: " + admin.getUsername());
    }
}
