package com.marvin.barriga.domain;

import com.marvin.barriga.domain.exception.ValidationException;

public class Conta {
    private Long id;
    private String nome;
    private Usuario usuario;

    public Conta(Long id, String nome, Usuario usuario) {

        validarConta(nome, usuario);

        this.id = id;
        this.nome = nome;
        this.usuario = usuario;
    }

    private static void validarConta(String nome, Usuario usuario) {
        if (nome == null || nome.trim().isEmpty())
            throw new ValidationException("Nome é obrigatório");
        if (usuario == null)
             throw new ValidationException("Usuário é obrigatório");
    }

    public Long id() {
        return id;
    }

    public String nome() {
        return nome;
    }

    public Usuario usuario() {
        return usuario;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Conta other = (Conta) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Conta{" + "id=" + id + ", nome=" + nome + ", usuario=" + usuario + '}';
    }

}
