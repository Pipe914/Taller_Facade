package com.canessa.maven.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.canessa.maven.test.Empresa.Empresa;
import com.canessa.maven.test.cuentas.Aspirante;
import com.canessa.maven.test.factorys.EmpresaFactory;
import com.canessa.maven.test.factorys.OfertaFactory;
import com.canessa.maven.test.factorys.RelacionadorFactory;
import com.canessa.maven.test.factorys.UsuarioFactory;
import com.canessa.maven.test.oferta.DescripcionOferta;
import com.canessa.maven.test.oferta.OfertaBase;
import com.canessa.maven.test.oferta.SueldoMensualOferta;
import com.canessa.maven.test.oferta.TiempoOferta;
import com.canessa.maven.test.oferta.TipoContratoOferta;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class FactorysTest
{
    /**
     * Rigorous Test :-)
     */

    //Objetos a usar
    UsuarioFactory user = new UsuarioFactory();
    EmpresaFactory empr = new EmpresaFactory();
    OfertaFactory  ofer = new OfertaFactory();
    RelacionadorFactory rl = new RelacionadorFactory();
    Usuario aspirante = new Aspirante("Andres", "1234");
    Empresa empresa = new Empresa("123", "KFC", "Calle vive sola");
    Componente oferta = new SueldoMensualOferta(
        new TiempoOferta(
                new TipoContratoOferta(
                        new DescripcionOferta(new OfertaBase(String.valueOf("123")), "esta es una empresa de pollo"), "Indefinido"),
                "1 año"),
        "Gratis");

    
    @Test
    public void testSaveUsuarioAndGetUsuario(){
        user.saveUsuario("Andres", aspirante);
        assertNotNull(user.getUsuario("Andres"));
    }

    @Test 
    public void testSaveEmpresaAndGetEmpresa(){
        empr.saveEmpresa("123", empresa);
        assertNotNull(empr.getEmpresa("123"));
    }

    @Test
    public void testSaveOfertaAndGetOferta(){
        ofer.saveOferta("123", oferta);
        assertNotNull(ofer.getOferta("123"));
    }

    @Test
    public void testSaveAndGetKeyUsuario(){
        rl.saveKeyUsuario("1", "prueba");
        assertNotNull(rl.getKeyUsuario("1"));
    }
    
    @Test
    public void testDeleteKeyUsuario(){
        rl.saveKeyUsuario("1", "prueba");
        boolean result = rl.deleteKeyUsuario("1");
        assertTrue("Test Correcto", result);
    }

    @Test
    public void testSaveAndGetUsuarioEmpresa(){
        rl.saveUsuarioEmpresa("Juan", "123");
        assertNotNull(rl.getUsuarioEmpresa("Juan"));
    }

    @Test
    public void testSaveAndGetOferta(){
        rl.saveUsuarioOferta("Juan", "1");
        assertNotNull(rl.getUsuarioOferta("Juan"));
    } 

}
