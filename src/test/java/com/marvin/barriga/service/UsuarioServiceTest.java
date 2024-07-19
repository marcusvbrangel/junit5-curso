package com.marvin.barriga.service;

import com.marvin.barriga.domain.Usuario;
import com.marvin.barriga.domain.builder.UsuarioBuilder;
import com.marvin.barriga.domain.exception.ValidationException;
import com.marvin.barriga.service.repositories.UsuarioRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Usuário - Service")
@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService underTest;

//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }

    @Test
    @DisplayName("Deve retornar um Usuário Vazio quando buscar por um Email Inexistente")
    public void deveRetornarUmUsuarioVazioQuandoBuscarPorEmailInexistente() {

//        UsuarioRepository usuarioRepository = mock(UsuarioRepository.class);
//        sut = new UsuarioService(usuarioRepository);

        when(usuarioRepository.findByEmail("laranja@terra.com")).thenReturn(Optional.empty());

        assertTrue(underTest.findByEmail("laranja@terra.com").isEmpty());

        verifyNoMoreInteractions(usuarioRepository);

    }

    @Test
    @DisplayName("Deve retornar um Usuário quando buscar por um Email Existente")
    public void deveRetornarUmUsuarioQuandoBuscarPorEmailExistente() {

        final String EMAIL_USUARIO = "laranja@terra.com";

//        UsuarioRepository usuarioRepository = mock(UsuarioRepository.class);
//        sut = new UsuarioService(usuarioRepository);

        when(usuarioRepository.findByEmail(EMAIL_USUARIO))
                .thenReturn(Optional.of(UsuarioBuilder.criar().comEmail("laranja@terra.com").usar()));

        assertTrue(underTest.findByEmail(EMAIL_USUARIO).isPresent());

        assertEquals(EMAIL_USUARIO, underTest.findByEmail(EMAIL_USUARIO).get().email());

        verifyNoMoreInteractions(usuarioRepository);

        verifyNoMoreInteractions(usuarioRepository);

    }

    // ---------------------------------------------

    @Test
    @DisplayName("Deve retornar todos os Usuários")
    public void deveRetornarTodosOsUsuarios() {
        List<Usuario> usuarios = List.of(
                UsuarioBuilder.criar().comEmail("usuario1@terra.com").usar(),
                UsuarioBuilder.criar().comEmail("usuario2@terra.com").usar()
        );
        when(usuarioRepository.findAll()).thenReturn(usuarios);

        List<Usuario> resultado = underTest.findAll();

        assertEquals(2, resultado.size());
        assertEquals("usuario1@terra.com", resultado.get(0).email());
        assertEquals("usuario2@terra.com", resultado.get(1).email());

        verifyNoMoreInteractions(usuarioRepository);
    }

    @Test
    @DisplayName("Deve retornar uma lista vazia quando não houver usuários")
    public void deveRetornarUmaListaVaziaQuandoNaoHouverUsuarios() {
        when(usuarioRepository.findAll()).thenReturn(List.of());

        List<Usuario> resultado = underTest.findAll();

        assertTrue(resultado.isEmpty());

        verifyNoMoreInteractions(usuarioRepository);
    }

    @Test
    @DisplayName("Deve salvar um novo Usuário quando o email não está cadastrado")
    public void deveSalvarUmNovoUsuarioQuandoEmailNaoEstaCadastrado() {
        Usuario novoUsuario = UsuarioBuilder.criar().comEmail("novo@terra.com").usar();
        when(usuarioRepository.findByEmail(novoUsuario.email())).thenReturn(Optional.empty());
        when(usuarioRepository.save(novoUsuario)).thenReturn(novoUsuario);

        Usuario usuarioSalvo = underTest.save(novoUsuario);

        assertNotNull(usuarioSalvo);
        assertEquals(novoUsuario.email(), usuarioSalvo.email());

        verifyNoMoreInteractions(usuarioRepository);
    }

    @Test
    @DisplayName("Deve lançar ValidationException quando o email já está cadastrado")
    public void deveLancarValidationExceptionQuandoEmailJaEstaCadastrado() {
        Usuario usuarioExistente = UsuarioBuilder.criar().comEmail("existente@terra.com").usar();
        when(usuarioRepository.findByEmail(usuarioExistente.email())).thenReturn(Optional.of(usuarioExistente));

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            underTest.save(usuarioExistente);
        });

        assertEquals("Email já cadastrado", exception.getMessage());

        verifyNoMoreInteractions(usuarioRepository);
    }

    @Test
    @DisplayName("Deve criar uma nova instância de UsuarioService")
    public void deveCriarNovaInstanciaDeUsuarioService() {
        underTest = new UsuarioService(usuarioRepository);
        assertNotNull(underTest);

        verifyNoMoreInteractions(usuarioRepository);
    }

    @Test
    @DisplayName("Deve retornar todos os Usuários quando houver usuários cadastrados")
    public void deveRetornarTodosOsUsuariosQuandoHouverUsuariosCadastrados() {
        List<Usuario> usuarios = List.of(
                UsuarioBuilder.criar().comEmail("usuario1@terra.com").usar(),
                UsuarioBuilder.criar().comEmail("usuario2@terra.com").usar()
        );
        when(usuarioRepository.findAll()).thenReturn(usuarios);

        List<Usuario> resultado = underTest.findAll();

        assertEquals(2, resultado.size());
        assertEquals("usuario1@terra.com", resultado.get(0).email());
        assertEquals("usuario2@terra.com", resultado.get(1).email());

        verifyNoMoreInteractions(usuarioRepository);
    }

    @Test
    @DisplayName("Deve retornar uma lista vazia quando não houver usuários cadastrados")
    public void deveRetornarUmaListaVaziaQuandoNaoHouverUsuariosCadastrados() {
        when(usuarioRepository.findAll()).thenReturn(List.of());

        List<Usuario> resultado = underTest.findAll();

        assertTrue(resultado.isEmpty());

        verifyNoMoreInteractions(usuarioRepository);
    }

    // -------------------------------------------------------------

    @Test
    @DisplayName("Deve retornar um Usuário quando buscar por um Email Existente (Mock Verify Repository)")
    public void deveRetornarUmUsuarioQuandoBuscarPorEmailExistenteComMockVerifyRepository() {

        final String EMAIL_USUARIO = "laranja@terra.com";

        when(usuarioRepository.findByEmail(EMAIL_USUARIO))
                .thenReturn(Optional.of(UsuarioBuilder.criar().comEmail("laranja@terra.com").usar()));

        assertTrue(underTest.findByEmail(EMAIL_USUARIO).isPresent());
        assertEquals(EMAIL_USUARIO, underTest.findByEmail(EMAIL_USUARIO).get().email());

        verify(usuarioRepository, times(2)).findByEmail(EMAIL_USUARIO);
        // or...
        verify(usuarioRepository, atLeastOnce()).findByEmail(EMAIL_USUARIO);
        // tip...
        verify(usuarioRepository, never()).findByEmail("desconhecido@hotmail.com");
        // top tip...
        verifyNoMoreInteractions(usuarioRepository);

    }

    @Test
    @DisplayName("Deve salvar um usuário com sucesso")
    public void deveSalvarUmUsuarioComSucesso() {
        Usuario usuarioParaSalvar = UsuarioBuilder.criar().comId(null).usar();

//        when(usuarioRepository.findByEmail(usuarioParaSalvar.email())).thenReturn(Optional.empty());
        when(usuarioRepository.save(usuarioParaSalvar)).thenReturn(UsuarioBuilder.criar().usar());

        Usuario usuarioSalvo = underTest.save(usuarioParaSalvar);

        assertNotNull(usuarioSalvo);
        assertEquals(usuarioParaSalvar.email(), usuarioSalvo.email());

        verify(usuarioRepository).findByEmail(usuarioParaSalvar.email());
        verify(usuarioRepository).save(usuarioParaSalvar);

    }

    @Test
    @DisplayName("Deve rejeitar um usuário existente ao tentar salvar")
    public void deveRejeitarUmUsuarioExistenteAoTentarSalvar() {
        Usuario usuarioParaSalvar = UsuarioBuilder.criar().comId(null).usar();

        when(usuarioRepository.findByEmail(usuarioParaSalvar.email()))
                .thenReturn(Optional.of(UsuarioBuilder.criar().usar()));

        ValidationException validationException =
                assertThrows(ValidationException.class, () -> underTest.save(usuarioParaSalvar));

        assertTrue(validationException.getMessage().contains("Email já cadastrado"));

        verify(usuarioRepository).findByEmail(usuarioParaSalvar.email());
        verify(usuarioRepository, never()).save(usuarioParaSalvar);
        verifyNoMoreInteractions(usuarioRepository);

    }

}
