package com.marvin.barriga.service;

import com.marvin.barriga.domain.Usuario;
import com.marvin.barriga.domain.builder.UsuarioBuilder;
import com.marvin.barriga.domain.exception.ValidationException;
import com.marvin.barriga.service.repositories.UsuarioRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@DisplayName("Usuário - Service")
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService sut;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve retornar um Usuário Vazio quando buscar por um Email Inexistente")
    void deveRetornarUmUsuarioVazioQuandoBuscarPorEmailInexistente() {

//        UsuarioRepository usuarioRepository = mock(UsuarioRepository.class);
//        sut = new UsuarioService(usuarioRepository);

        when(usuarioRepository.findByEmail("laranja@terra.com")).thenReturn(Optional.empty());

        Assertions.assertTrue(sut.findByEmail("laranja@terra.com").isEmpty());

    }

    @Test
    @DisplayName("Deve retornar um Usuário quando buscar por um Email Existente")
    void deveRetornarUmUsuarioQuandoBuscarPorEmailExistente() {

        final String EMAIL_USUARIO = "laranja@terra.com";

//        UsuarioRepository usuarioRepository = mock(UsuarioRepository.class);
//        sut = new UsuarioService(usuarioRepository);

        when(usuarioRepository.findByEmail(EMAIL_USUARIO))
                .thenReturn(Optional.of(UsuarioBuilder.criar().comEmail("laranja@terra.com").usar()));

        Assertions.assertTrue(sut.findByEmail(EMAIL_USUARIO).isPresent());

        Assertions.assertEquals(EMAIL_USUARIO, sut.findByEmail(EMAIL_USUARIO).get().email());

    }

    // ---------------------------------------------

    @Test
    @DisplayName("Deve retornar todos os Usuários")
    void deveRetornarTodosOsUsuarios() {
        List<Usuario> usuarios = List.of(
                UsuarioBuilder.criar().comEmail("usuario1@terra.com").usar(),
                UsuarioBuilder.criar().comEmail("usuario2@terra.com").usar()
        );
        when(usuarioRepository.findAll()).thenReturn(usuarios);

        List<Usuario> resultado = sut.findAll();

        Assertions.assertEquals(2, resultado.size());
        Assertions.assertEquals("usuario1@terra.com", resultado.get(0).email());
        Assertions.assertEquals("usuario2@terra.com", resultado.get(1).email());
    }

    @Test
    @DisplayName("Deve retornar uma lista vazia quando não houver usuários")
    void deveRetornarUmaListaVaziaQuandoNaoHouverUsuarios() {
        when(usuarioRepository.findAll()).thenReturn(List.of());

        List<Usuario> resultado = sut.findAll();

        Assertions.assertTrue(resultado.isEmpty());
    }

    @Test
    @DisplayName("Deve salvar um novo Usuário quando o email não está cadastrado")
    void deveSalvarUmNovoUsuarioQuandoEmailNaoEstaCadastrado() {
        Usuario novoUsuario = UsuarioBuilder.criar().comEmail("novo@terra.com").usar();
        when(usuarioRepository.findByEmail(novoUsuario.email())).thenReturn(Optional.empty());
        when(usuarioRepository.save(novoUsuario)).thenReturn(novoUsuario);

        Usuario usuarioSalvo = sut.save(novoUsuario);

        Assertions.assertNotNull(usuarioSalvo);
        Assertions.assertEquals(novoUsuario.email(), usuarioSalvo.email());
    }

    @Test
    @DisplayName("Deve lançar ValidationException quando o email já está cadastrado")
    void deveLancarValidationExceptionQuandoEmailJaEstaCadastrado() {
        Usuario usuarioExistente = UsuarioBuilder.criar().comEmail("existente@terra.com").usar();
        when(usuarioRepository.findByEmail(usuarioExistente.email())).thenReturn(Optional.of(usuarioExistente));

        ValidationException exception = Assertions.assertThrows(ValidationException.class, () -> {
            sut.save(usuarioExistente);
        });

        Assertions.assertEquals("Email já cadastrado", exception.getMessage());
    }


}
