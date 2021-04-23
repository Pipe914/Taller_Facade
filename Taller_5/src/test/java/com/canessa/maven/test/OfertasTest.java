package com.canessa.maven.test;



import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.canessa.maven.test.oferta.DescripcionOferta;
import com.canessa.maven.test.oferta.ModificadorOferta;
import com.canessa.maven.test.oferta.OfertaBase;
import com.canessa.maven.test.oferta.SueldoMensualOferta;
import com.canessa.maven.test.oferta.TiempoOferta;
import com.canessa.maven.test.oferta.TipoContratoOferta;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class OfertasTest
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
    Componente oferta = new OfertaBase("1");
    
    @Test
    public void testDescripcionOferta(){
        Componente oferta1 = new DescripcionOferta(oferta, "Probando");
        String mensaje = "La oferta laboral consta de:\n\nLa descripción es: Probando\n";
        System.out.println(mensaje);
        System.out.println(oferta1.imprimirOferta());
        assertEquals(mensaje, oferta1.imprimirOferta());
    }


    @Test
    public void testSueldoMensualOferta(){
        String sueldo = "50000";
        Componente oferta1 = new SueldoMensualOferta(oferta, sueldo);
        String mensaje = "La oferta laboral consta de:\nEl sueldo mensual del contrato es: " + sueldo + "\n";
        assertEquals(mensaje, oferta1.imprimirOferta());
    }

    @Test
    public void testTiempoOferta(){
        String tiempo = "1 año";
        Componente oferta1 = new TiempoOferta(oferta, tiempo);
        String mensaje = "La oferta laboral consta de:\nEl tiempo del contrato es: " + tiempo + "\n";
        assertEquals(mensaje, oferta1.imprimirOferta());
    }

    @Test
    public void testTipoContratoOferta(){
        String tipo = "Fijo";
        Componente oferta1 = new TipoContratoOferta(oferta, tipo);
        String mensaje = "La oferta laboral consta de:\nEl tipo de contrato es: " + tipo + "\n";
        assertEquals(mensaje, oferta1.imprimirOferta());
    }
    

}
