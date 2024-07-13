package com.marvin.barriga.domain;

import com.marvin.barriga.domain.exception.ValidationException;

public class Usuario {

    private Long id;
    private String nome;
    private String email;
    private String senha;

    public Usuario(Long id, String nome, String email, String senha) {

        validarUsuario(nome, email, senha);

        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    private static void validarUsuario(String nome, String email, String senha) {
        if (nome == null || nome.trim().isEmpty())
            throw new ValidationException("Nome é obrigatório");
        if (email == null || email.trim().isEmpty())
            throw new ValidationException("Email é obrigatório");
        if (!email.matches(".*@.*"))
            throw new ValidationException("Email inválido");
        if (senha == null || senha.trim().isEmpty())
            throw new ValidationException("Senha é obrigatória");
        if (senha.length() < 8)
            throw new ValidationException("Senha deve ter no mínimo 8 caracteres");
        if (!senha.matches(".*[^a-zA-Z0-9].*"))
            throw new ValidationException("Senha deve ter letras, números e caracteres especiais");
    }

    public Long id() {
        return id;
    }

    public String nome() {
        return nome;
    }

    public String email() {
        return email;
    }

    public String senha() {
        return senha;
    }

}
