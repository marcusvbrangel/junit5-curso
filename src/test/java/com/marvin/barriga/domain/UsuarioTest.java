package com.marvin.barriga.domain;

import com.marvin.barriga.domain.builder.UsuarioBuilder;
import com.marvin.barriga.domain.exception.ValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Dominio Usuário")
class UsuarioTest {

    private static final Long ID_VALIDO = 1L;
    private static final String NOME_VALIDO = "Fulano";
    private static final String EMAIL_VALIDO = "fulano@gmail.com";
    private static final String SENHA_VALIDA = "fds#1234";

//    @Test
//    @DisplayName("Criar usuário com dados válidos")
//    void criarUsuarioComDadosValidos() {
//        Usuario usuario = USUARIO_VALIDO;
//        assertEquals(1L, usuario.id());
//        assertEquals("Fulano", usuario.nome());
//        assertEquals("fulano@gmail.com", usuario.email());
//        assertEquals("fds#1234", usuario.senha());
//    }

//    @Test
//    @DisplayName("Criar usuário com dados válidos")
//    void criarUsuarioComDadosValidos() {
//        Usuario usuario = new Usuario(USUARIO_ID, USUARIO_NOME, USUARIO_EMAIL, USUARIO_SENHA);
//        assertAll("usuario",
//                () -> assertEquals(1L, usuario.id(), "id invalido"),
//                () -> assertEquals("Fulano", usuario.nome(), "nome invalido"),
//                () -> assertEquals("fulano@gmail.com", usuario.email(), "email invalido"),
//                () -> assertEquals("fds#1234", usuario.senha(), "senha invalida")
//        );
//    }

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
        assertEquals("Senha deve ter letras, números e caracteres especiais", validationException.getMessage());
    }

}
