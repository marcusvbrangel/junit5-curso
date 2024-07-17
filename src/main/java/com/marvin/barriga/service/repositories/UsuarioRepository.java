package com.marvin.barriga.service.repositories;

import com.marvin.barriga.domain.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository {

    Usuario save(Usuario usuario);

    Optional<Usuario> findByEmail(String email);

    List<Usuario> findAll();
}
