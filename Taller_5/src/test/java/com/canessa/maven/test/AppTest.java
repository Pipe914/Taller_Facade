package com.canessa.maven.test; 

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.canessa.maven.test.cuentas.Admin;
import com.canessa.maven.test.proxy.AESEncript;
import com.canessa.maven.test.proxy.Login;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    //Objetos a usar
    Facade controlador = Facade.getFacade();
    Login proxy = Login.getLogin();
    Usuario admin = new Admin("Andres", "123");
    String key1 = proxy.verifyLogin(admin, "Andres", "123");
    
    @Test
    public void testLogOutFallida(){
        assertEquals("", AESEncript.decrypt(controlador.action(AESEncript.encrypt("2/" + "asda"))));
    }
}
