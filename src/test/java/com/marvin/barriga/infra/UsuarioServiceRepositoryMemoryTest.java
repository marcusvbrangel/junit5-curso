package com.marvin.barriga.infra;

import com.marvin.barriga.domain.Usuario;
import com.marvin.barriga.domain.builder.UsuarioBuilder;
import com.marvin.barriga.domain.exception.ValidationException;
import com.marvin.barriga.service.UsuarioService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Dominio Usuário - Service")
public class UsuarioServiceRepositoryMemoryTest {

    private UsuarioService usuarioService;

    public UsuarioServiceRepositoryMemoryTest() {
        this.usuarioService = new UsuarioService(new UsuarioMemoryRepository());
    }

    @Test
    @DisplayName("Deve salvar um usuário válido")
    void deveSalvarUmUsuarioValido() {
        Usuario usuarioSalvo = usuarioService.save(UsuarioBuilder.criar().comId(null).usar());
        Assertions.assertNotNull(usuarioSalvo.id());
    }

    @Test
    @DisplayName("Não deve salvar um usuário com um email já existente")
    void naoDeveSalvarUmUsuarioComUmEmailJaExistente() {
        Usuario usuarioSalvo = usuarioService.save(UsuarioBuilder.criar()
                .comId(null)
                .comEmail("usuariopadrao@gmail.com")
                .usar());

        String exceptionMessage = Assertions.assertThrows(ValidationException.class, () ->
                usuarioService.save(UsuarioBuilder.criar()
                        .comId(2L)
                        .comEmail(usuarioSalvo.email())
                        .usar())).getMessage();
        Assertions.assertEquals("Email já cadastrado", exceptionMessage);
    }

}
