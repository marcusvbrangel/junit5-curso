package com.marvin.barriga.service;

import com.marvin.barriga.domain.Usuario;
import com.marvin.barriga.domain.exception.ValidationException;
import com.marvin.barriga.service.repositories.UsuarioRepository;

import java.util.List;

public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(final UsuarioRepository usuarioRepository) {
        System.out.println("=> UsuarioService => Criando nova instancia...");
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario save(Usuario usuario) {
        usuarioRepository.findByEmail(usuario.email())
                .ifPresent(usuarioEncontrado -> {
                    throw new ValidationException("Email já cadastrado");
                });
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

}
