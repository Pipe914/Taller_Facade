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
public class ProxyTest
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    //Objetos a usar
    AESEncript encripta = new AESEncript();
    Login proxy = Login.getLogin();
    Usuario admin = new Admin("Andres", "123");

    @Test
    public void testEncryptandDecrypt(){
        String mensaje = "Soy Juan quien testea";
        String result1 = encripta.encrypt(mensaje);
        String result2 = encripta.decrypt(result1);

        assertEquals(mensaje, result2);
    }

    @Test
    public void testVerifyLogin(){
        String result = proxy.verifyLogin(admin, "Andres", "123");
        boolean correcto = false;
        if(Integer.parseInt(result) >= 2 && Integer.parseInt(result) <= 100){
             correcto = true;
        }

        assertTrue("Test correcto", correcto);
    }



}
