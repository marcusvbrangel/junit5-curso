package com.marvin.barriga.domain.builder;

import com.marvin.barriga.domain.Usuario;

public class UsuarioBuilder {
    private Long id;
    private String nome;
    private String email;
    private String senha;

    private UsuarioBuilder() {}

    public static UsuarioBuilder criar() {
        UsuarioBuilder builder = new UsuarioBuilder();
        inicializarDadosPadroes(builder);
        return builder;
    }

    private static void inicializarDadosPadroes(UsuarioBuilder builder) {
        builder.id = 1L;
        builder.nome = "Usuário Padrão";
        builder.email = "usuariopadrao@gmail.com";
        builder.senha = "fds#1234";
    }

    public UsuarioBuilder comId(Long id) {
        this.id = id;
        return this;
    }

    public UsuarioBuilder comNome(String nome) {
        this.nome = nome;
        return this;
    }

    public UsuarioBuilder comEmail(String email) {
        this.email = email;
        return this;
    }

    public UsuarioBuilder comSenha(String senha) {
        this.senha = senha;
        return this;
    }

    public Usuario usar() {
        return new Usuario(id, nome, email, senha);
    }

}

