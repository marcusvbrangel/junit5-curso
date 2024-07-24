package com.marvin.barriga.service;

import com.marvin.barriga.domain.Conta;
import com.marvin.barriga.domain.exception.ValidationException;
import com.marvin.barriga.service.external.ContaEvent;
import com.marvin.barriga.service.external.ContaEvent.EventType;
import com.marvin.barriga.service.repositories.ContaRepository;

import java.util.List;

public class ContaService {

    private final ContaRepository contaRepository;
    private final ContaEvent contaEvent;

    public ContaService(ContaRepository contaRepository, ContaEvent contaEvent) {
        this.contaRepository = contaRepository;
        this.contaEvent = contaEvent;
    }

    public Conta save(final Conta conta) {
        final String MENSAGEM_ERRO_CONTA_REPETIDA = "Usuário já possui uma conta com este nome";
        List<Conta> contas = contaRepository.obterContasPorUsuarioId(conta.usuario().id());
        contas.stream().forEach(contaExistente -> {
            if (conta.nome().equalsIgnoreCase(contaExistente.nome())) {
                throw new ValidationException(MENSAGEM_ERRO_CONTA_REPETIDA);
            }
        });

        Conta contaSalva = contaRepository.save(conta);
        contaEvent.dispatch(contaSalva, EventType.CREATED);
        return contaSalva;
    }

}
