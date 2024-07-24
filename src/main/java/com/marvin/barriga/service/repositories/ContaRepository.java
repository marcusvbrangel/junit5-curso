package com.marvin.barriga.service.repositories;

import com.marvin.barriga.domain.Conta;

import java.util.List;

public interface ContaRepository {

    Conta save(Conta conta);

    List<Conta> findByUsuarioId(Long usuarioId);

    List<Conta> findAll();

    List<Conta> obterContasPorUsuarioId(Long usuarioId);

}
