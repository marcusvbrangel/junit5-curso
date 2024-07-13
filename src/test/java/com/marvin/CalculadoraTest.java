package com.marvin;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.ArrayList;

class CalculadoraTest {

    @Test
    public void testarSomarDoisNumeros() {
        Calculadora calculadora = new Calculadora();
        Assertions.assertEquals(5, calculadora.somarDoisNumeros(2, 3));
    }

    @Test
    public void testarAssertions() {
        Assertions.assertEquals("Casa", "Casa");
        Assertions.assertNotEquals("Casa", "casa");
        Assertions.assertTrue("casa".equalsIgnoreCase("CASA"));
        Assertions.assertTrue("casa".endsWith("sa"));

        List<String> l1 = new ArrayList<>();
        List<String> l2 = new ArrayList<>();
        List<String> l3 = null;

        Assertions.assertEquals(l1, l2);   // valor...
        Assertions.assertNotSame(l1, l2);      // referência...
        Assertions.assertNull(l3);
//        Assertions.fail("falhou pelo motivo xyz...");


    }

}