package com.marvin.barriga.domain;

import com.marvin.barriga.domain.builder.ContaBuilder;
import com.marvin.barriga.domain.builder.UsuarioBuilder;
import com.marvin.barriga.domain.exception.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

@DisplayName("Dominio Conta")
public class ContaTest {

    @Test
    @DisplayName("Deve criar uma conta válida")
    void deveCriarUmaContaValida() {
        Conta conta = ContaBuilder.criar().usar();
        Assertions.assertAll("conta",
                () -> Assertions.assertEquals(1L, conta.id()),
                () -> Assertions.assertEquals("Conta Prime", conta.nome()),
                () -> Assertions.assertEquals(UsuarioBuilder.criar().usar(), conta.usuario()
        ));
    };

    @ParameterizedTest(name = "| {index} | - {3}")
    @MethodSource(value = "dataProvider")
    @DisplayName("Deve rejeitar uma conta inválida")
    void deveRejeitarUmaContaInvalida(Long id, String nome, Usuario usuario, String mensagem) {
        String exceptionMessage = Assertions.assertThrows(ValidationException.class, () ->
                ContaBuilder.criar()
                        .comId(id)
                        .comNome(nome)
                        .comUsuario(usuario)
                        .usar()).getMessage();
        Assertions.assertEquals(mensagem, exceptionMessage);
    }

    private static Stream<Arguments> dataProvider() {
        return Stream.of(
                Arguments.of(1L, null, UsuarioBuilder.criar().usar(), "Nome é obrigatório"),
                Arguments.of(1L, "Conta Prime", null, "Usuário é obrigatório")
        );
    }

}
