package com.marvin.barriga.infra;

import com.marvin.barriga.domain.Conta;
import com.marvin.barriga.service.repositories.ContaRepository;

import java.util.ArrayList;
import java.util.List;

public class ContaMemoryRepository implements ContaRepository {

    private final List<Conta> contas = new ArrayList<>();
    private Long currentId;

    public ContaMemoryRepository() {
        this.currentId = 0L;
    }

    @Override
    public Conta save(final Conta conta) {
        Conta contaNova = new Conta(++currentId, conta.nome(), conta.usuario());
        contas.add(contaNova);
        return contaNova;
    }

    @Override
    public List<Conta> findByUsuarioId(Long usuarioId) {
        return List.of();
    }

}
