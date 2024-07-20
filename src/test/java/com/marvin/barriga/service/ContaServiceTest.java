package com.marvin.barriga.service;

import com.marvin.barriga.domain.Conta;
import com.marvin.barriga.domain.builder.ContaBuilder;
import com.marvin.barriga.domain.exception.ValidationException;
import com.marvin.barriga.service.repositories.ContaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@DisplayName("Conta - Service")
@ExtendWith(MockitoExtension.class)
//@MockitoSettings(strictness = Strictness.LENIENT)
public class ContaServiceTest {

    @Mock
    private ContaRepository contaRepository;

    @InjectMocks
    private ContaService underTest;

    @Test
    @DisplayName("Deve salvar um conta com sucesso")
    @MockitoSettings(strictness = Strictness.LENIENT)
    public void deveSalvarUmaContaComSucesso() {
        Conta contaParaSalvar = ContaBuilder.criar().usar();

        when(contaRepository.save(contaParaSalvar)).thenReturn(ContaBuilder.criar().usar());

        Conta contaSalva = underTest.save(contaParaSalvar);

        assertNotNull(contaSalva);
        assertEquals(contaParaSalvar.id(), contaSalva.id());

        verify(contaRepository).save(contaParaSalvar);

    }

    @Test
    @DisplayName("Deve rejeitar uma conta repetida")
    public void deveRejeitarUmaContaRepetida() {

        final String MENSAGEM_ERRO_CONTA_REPETIDA = "Usuário já possui uma conta com este nome";

        Conta contaParaSalvar = ContaBuilder.criar().usar();

        when(contaRepository.findByUsuarioId(contaParaSalvar.usuario().id()))
                .thenReturn(List.of(contaParaSalvar));

//        when(contaRepository.save(contaParaSalvar)).thenReturn(ContaBuilder.criar().usar());

        ValidationException validationException = Assertions.assertThrows(ValidationException.class, () ->
            underTest.save(contaParaSalvar)
        );

        assertEquals(MENSAGEM_ERRO_CONTA_REPETIDA, validationException.getMessage());

        verify(contaRepository).findByUsuarioId(contaParaSalvar.usuario().id());
        verify(contaRepository, never()).save(contaParaSalvar);
        verifyNoMoreInteractions(contaRepository);

    }

}
