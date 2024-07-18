package com.marvin;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.ArrayList;

@DisplayName("Calculadora")
class CalculadoraTest {

    private Calculadora calculadora = new Calculadora();

    @Test
    public void testarSomarDoisNumeros() {
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

    @Test
    public void testarDividirDoisNumerosDeveRetornarNumeroInteiro() {
        float resultado = calculadora.dividirDoisNumeros(6, 2);
        Assertions.assertEquals(3, resultado);
    }

    @Test
    public void testarDividirDoisNumerosDeveRetornarNumeroNegativo() {
        float resultado = calculadora.dividirDoisNumeros(6, -2);
        Assertions.assertEquals(-3, resultado);
    }

    @Test
    public void testarDividirDoisNumerosDeveRetornarNumeroDecimal() {
        float resultado = calculadora.dividirDoisNumeros(10, 3);
//        Assertions.assertEquals(3.3333332538604736, resultado);
        Assertions.assertEquals(3.33, resultado, 0.01);
    }

    @Test
    public void testarDividirDoisNumerosComDenominadorZero() {
        ArithmeticException arithmeticException = Assertions.assertThrows(ArithmeticException.class, () -> {
//            calculadora.dividirDoisNumeros(10, 0);
            float resultado = 10 / 0;
        });
        Assertions.assertEquals("/ by zero", arithmeticException.getMessage());
    }






}