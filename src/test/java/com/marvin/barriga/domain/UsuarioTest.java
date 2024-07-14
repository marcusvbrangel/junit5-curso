package com.marvin.barriga.domain;

import com.marvin.Calculadora;
import com.marvin.barriga.domain.builder.UsuarioBuilder;
import com.marvin.barriga.domain.exception.ValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Dominio Usuário")
class UsuarioTest {

    private static final Long ID_VALIDO = 1L;
    private static final String NOME_VALIDO = "Fulano";
    private static final String EMAIL_VALIDO = "fulano@gmail.com";
    private static final String SENHA_VALIDA = "fds#1234";

    @Test
    @DisplayName("Criar usuário com dados válidos")
    void criarUsuarioComDadosValidos() {
        Usuario usuario = UsuarioBuilder.novoUsuario().criar();
        assertAll("usuario",
                () -> assertEquals(ID_VALIDO, usuario.id(), "id invalido"),
                () -> assertEquals(NOME_VALIDO, usuario.nome(), "nome invalido"),
                () -> assertEquals(EMAIL_VALIDO, usuario.email(), "email invalido"),
                () -> assertEquals(SENHA_VALIDA, usuario.senha(), "senha invalida")
        );
    }

    @Test
    @DisplayName("Ao tentar criar usuário sem nome, deve lançar uma exception")
    void criarUsuarioSemNomeLancarException() {
        ValidationException validationException = assertThrows(ValidationException.class, () ->
                UsuarioBuilder.novoUsuario().comNome(null).criar());
        assertEquals("Nome é obrigatório", validationException.getMessage());
    }

    @Test
    @DisplayName("Ao tentar criar usuário sem email, deve lançar uma exception")
    void criarUsuarioSemEmailLancarException() {
        ValidationException validationException = assertThrows(ValidationException.class, () ->
                UsuarioBuilder.novoUsuario().comEmail(null).criar());
        assertEquals("Email é obrigatório", validationException.getMessage());
    }

    @Test
    @DisplayName("Ao tentar criar usuário com email invalido, deve lançar uma exception")
    void criarUsuarioComEmailInvalidoLancarException() {
        ValidationException validationException = assertThrows(ValidationException.class, () ->
                UsuarioBuilder.novoUsuario().comEmail("fulanogmail.com").criar());
        assertEquals("Email inválido", validationException.getMessage());
    }

    @Test
    @DisplayName("Ao tentar criar usuário sem senha, deve lançar uma exception")
    void criarUsuarioSemSenhaLancarException() {
        ValidationException validationException = assertThrows(ValidationException.class, () ->
                UsuarioBuilder.novoUsuario().comSenha(null).criar());
        assertEquals("Senha é obrigatória", validationException.getMessage());
    }

    @Test
    @DisplayName("Ao tentar criar usuário com senha invalida (menor que 8 caracteres), deve lançar uma exception")
    void criarUsuarioComSenhaInvalidaMenorQueOitoCaracteresLancarExceptionX() {
        ValidationException validationException = assertThrows(ValidationException.class, () ->
                UsuarioBuilder.novoUsuario().comSenha("123456").criar());
        assertEquals("Senha deve ter no mínimo 8 caracteres", validationException.getMessage());
    }

    @Test
    @DisplayName("Ao tentar criar usuário com senha invalida (sem letras, números e/ou caracteres especiais), " +
            "deve lançar uma exception")
    void criarUsuarioComSenhaInvalidaSemCaracteresExpeciaisLancarExceptionX() {
        ValidationException validationException = assertThrows(ValidationException.class, () ->
                UsuarioBuilder.novoUsuario().comSenha("12345678").criar());
        assertEquals("Senha deve ter letras números e caracteres especiais", validationException.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"jan", "fev", "mar", "abr"})
    void testStrings(String param) {
        System.out.println(param);
        assertNotNull(param);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "6, 2, 3",
            "6, -2, -3",
            "10, 3, 3.33333325",
            "0, 2, 0",
            "10, 0, Infinity"
    })
    void testDivisao(int numerador, int denominador, double resultado) {
        Calculadora calculadora = new Calculadora();
        float resultadoEsperado = calculadora.dividirDoisNumeros(numerador, denominador);
        assertEquals(resultado, resultadoEsperado, 0.001);
    }

    // Ao tentar criar usuário sem nome, deve lançar uma exception...
    // Ao tentar criar usuário sem email, deve lançar uma exception...
    // Ao tentar criar usuário com email invalido, deve lançar uma exception...
    // Ao tentar criar usuário sem senha, deve lançar uma exception...
    // Ao tentar criar usuário com senha invalida (menor que 8 caracteres), deve lançar uma exception...
    // Ao tentar criar usuário com senha invalida (sem letras, números e/ou caracteres especiais),
    //   deve lançar uma exception...

    @ParameterizedTest(name = "| {index} | - {4}")
    @CsvSource(value = {
            "1, NULL, fulano@gmail.com, 123456, Nome é obrigatório",
            "1, joão, NULL, 123456, Email é obrigatório",
            "1, joão, joao.com, 123456, Email inválido",
            "1, joão, joao@gmail.com, NULL, Senha é obrigatória",
            "1, joão, joao@gmail.com, 123456, Senha deve ter no mínimo 8 caracteres",
            "1, joão, joao@gmail.com, 12345678, Senha deve ter letras números e caracteres especiais"
    }, nullValues = "NULL")
    @DisplayName("Deve validar todos os campos")
    void deveValidarTodosOsCampos(Long id, String nome, String email, String senha, String mensagem) {
        ValidationException validationException = assertThrows(ValidationException.class, () ->
                UsuarioBuilder.novoUsuario()
                        .comId(id)
                        .comNome(nome)
                        .comEmail(email)
                        .comSenha(senha)
                        .criar());
        assertEquals(mensagem, validationException.getMessage());
    }











}
