package com.marvin.barriga.infra;

import com.marvin.barriga.domain.Usuario;
import com.marvin.barriga.service.repositories.UsuarioRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsuarioMemoryRepository implements UsuarioRepository {

    private List<Usuario> usuarios = new ArrayList<>();
    private Long currentId;

    public UsuarioMemoryRepository() {
        this.currentId = 0L;
//        this.save(new Usuario(null, "xxxx", "guerra@gmail.com", "1a#45678"));
    }

    @Override
    public Usuario save(final Usuario usuario) {
        Usuario usuarioNovo = new Usuario(++currentId, usuario.nome(), usuario.email(), usuario.senha());
        usuarios.add(usuarioNovo);
        return usuarioNovo;
    }

    @Override
    public Optional<Usuario> findByEmail(String email) {
        return usuarios.stream()
                .filter(usuario -> usuario.email().equalsIgnoreCase(email))
                .findFirst();
    }

    @Override
    public List<Usuario> findAll() {
        return usuarios;
    }

    public static void main(String[] args) {
//        UsuarioRepository usuarioRepository = new UsuarioMemoryRepository();
//        usuarioRepository.save(new Usuario(null, "James Bond", "bond@gmail.com", "00w&0007"));
//        System.out.println(usuarios);
    }
}
