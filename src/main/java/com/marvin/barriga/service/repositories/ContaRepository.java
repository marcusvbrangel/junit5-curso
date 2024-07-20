package com.marvin.barriga.service.repositories;

import com.marvin.barriga.domain.Conta;

import java.util.List;
import java.util.Optional;

public interface ContaRepository {

    Conta save(Conta conta);

    List<Conta> findByUsuarioId(Long usuarioId);

}
