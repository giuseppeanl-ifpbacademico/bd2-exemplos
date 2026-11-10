package com.ads.bd2.agenda.modelo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class Usuario {
    private String nome;
    private Date dataNascimento;
    private String login;
    private String senha;
    private String email;
    private Collection<Lembrete> lembretes;

    public Usuario() {
        this.lembretes = new ArrayList<>();
    }

    public Usuario(String nome, Date dataNascimento, String login, String senha, String email) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.login = login;
        this.senha = senh
        this.email = email;
        this.lembretes = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Collection<Lembrete> getLembretes() {
        return lembretes;
    }

    public void setLembretes(Collection<Lembrete> lembretes) {
        this.lembretes = lembretes;
    }
}
