package com.marvin.barriga.service;

import com.marvin.barriga.domain.Conta;
import com.marvin.barriga.domain.builder.ContaBuilder;
import com.marvin.barriga.domain.exception.ValidationException;
import com.marvin.barriga.service.external.ContaEvent;
import com.marvin.barriga.service.repositories.ContaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Conta - Service")
@ExtendWith(MockitoExtension.class)
//@MockitoSettings(strictness = Strictness.LENIENT)
public class ContaServiceTest {

    @Mock
    private ContaRepository contaRepository;

    @Mock
    private ContaEvent contaEvent;

    @InjectMocks
    private ContaService underTest;

    @Test
    @DisplayName("Deve salvar um conta com sucesso")
    @MockitoSettings(strictness = Strictness.LENIENT)
    public void deveSalvarUmaContaComSucesso() {
        Conta contaParaSalvar = ContaBuilder.criar().usar();

        when(contaRepository.save(contaParaSalvar)).thenReturn(contaParaSalvar);

        doNothing().when(contaEvent).dispatch(contaParaSalvar, ContaEvent.EventType.CREATED);

        Conta contaSalva = underTest.save(contaParaSalvar);

        assertNotNull(contaSalva);
        assertEquals(contaParaSalvar.id(), contaSalva.id());

        verify(contaRepository).save(contaParaSalvar);
        verify(contaEvent).dispatch(contaParaSalvar, ContaEvent.EventType.CREATED);

    }

    @Test
    @DisplayName("Deve rejeitar uma conta repetida")
    public void deveRejeitarUmaContaRepetida() {

        final String MENSAGEM_ERRO_CONTA_REPETIDA = "Usuário já possui uma conta com este nome";

        Conta contaParaSalvar = ContaBuilder.criar().usar();

        when(contaRepository.obterContasPorUsuarioId(contaParaSalvar.usuario().id()))
                .thenReturn(List.of(contaParaSalvar));

        ValidationException validationException = assertThrows(ValidationException.class, () ->
            underTest.save(contaParaSalvar)
        );

        assertEquals(MENSAGEM_ERRO_CONTA_REPETIDA, validationException.getMessage());

        verify(contaRepository).obterContasPorUsuarioId(contaParaSalvar.usuario().id());
        verify(contaRepository, never()).save(contaParaSalvar);
        verifyNoMoreInteractions(contaRepository);

    }

    @Test
    @DisplayName("Deve rejeitar criar uma nova conta com nome de conta já existente para o mesmo usuário")
    public void deveRejeitarCriarUmaNovaContaComNomeDeContaJaExistenteParaOMesmoUsuario() {
        final String MENSAGEM_ERRO_CONTA_REPETIDA = "Usuário já possui uma conta com este nome";

        Conta contaParaSalvar = ContaBuilder.criar().usar();

        when(contaRepository.obterContasPorUsuarioId(contaParaSalvar.usuario().id()))
                .thenReturn(List.of(contaParaSalvar));

        // Note: According MockitoSettings above... This instruction below is not necessary...
//        when(contaRepository.save(contaParaSalvar)).thenReturn(contaParaSalvar);

        ValidationException validationException = assertThrows(ValidationException.class, () ->
                underTest.save(contaParaSalvar)
        );

        assertEquals(MENSAGEM_ERRO_CONTA_REPETIDA, validationException.getMessage());

        verify(contaRepository).obterContasPorUsuarioId(contaParaSalvar.usuario().id());
        verify(contaRepository, never()).save(contaParaSalvar);
        verifyNoMoreInteractions(contaRepository);

    }

    @Test
    @DisplayName("Deve criar uma nova conta com nome de conta já existente, más de um outro usuário")
    public void deveCriarUmaNovaContaComNomeDeContaJaExistenteMasDeUmOutroUsuario() {

        Conta contaParaSalvar = ContaBuilder.criar().usar();

        when(contaRepository.obterContasPorUsuarioId(contaParaSalvar.usuario().id()))
                .thenReturn(List.of(ContaBuilder.criar().comNome("Outra Conta").usar()));

        when(contaRepository.save(contaParaSalvar)).thenReturn(contaParaSalvar);

        Conta contaSalva = underTest.save(contaParaSalvar);

        assertNotNull(contaSalva);
        assertNotNull(contaSalva.id());

        verify(contaRepository).obterContasPorUsuarioId(contaParaSalvar.usuario().id());
        verify(contaRepository).save(contaParaSalvar);
        verifyNoMoreInteractions(contaRepository);

    }
















}
