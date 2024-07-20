package com.marvin.barriga.service;

import com.marvin.barriga.domain.Conta;
import com.marvin.barriga.domain.exception.ValidationException;
import com.marvin.barriga.service.repositories.ContaRepository;

import java.util.List;

public class ContaService {

    private final ContaRepository contaRepository;

    public ContaService(ContaRepository contaRepository) {
        this.contaRepository = contaRepository;
    }

    public Conta save(final Conta conta) {
        final String MENSAGEM_ERRO_CONTA_REPETIDA = "Usuário já possui uma conta com este nome";

        List<Conta> contas = contaRepository.findByUsuarioId(conta.usuario().id());
        contas.stream().forEach(contaExistente -> {
            if (conta.nome().equalsIgnoreCase(contaExistente.nome())) {
                throw new ValidationException(MENSAGEM_ERRO_CONTA_REPETIDA);
            }
        });

        return contaRepository.save(conta);
    }

}
