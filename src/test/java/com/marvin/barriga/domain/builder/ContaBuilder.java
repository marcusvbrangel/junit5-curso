package com.marvin.barriga.domain.builder;

import com.marvin.barriga.domain.Conta;
import com.marvin.barriga.domain.Usuario;

public class ContaBuilder {

    private Long id;
    private String nome;
    private Usuario usuario;

    private ContaBuilder() {}

    public static ContaBuilder criar() {
        ContaBuilder builder = new ContaBuilder();
        inicializarDadosPadroes(builder);
        return builder;
    }

    private static void inicializarDadosPadroes(ContaBuilder builder) {
        builder.id = 1L;
        builder.nome = "Conta Prime";
        builder.usuario = UsuarioBuilder.criar().usar();
    }

    public ContaBuilder comId(Long id) {
        this.id = id;
        return this;
    }

    public ContaBuilder comNome(String nome) {
        this.nome = nome;
        return this;
    }

    public ContaBuilder comUsuario(Usuario usuario) {
        this.usuario = usuario;
        return this;
    }

    public Conta usar() {
        return new Conta(id, nome, usuario);
    }

}
