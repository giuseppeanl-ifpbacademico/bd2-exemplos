package com.ads.bd2.agenda.modelo;

import java.util.Date;

public class Lembrete {
    private Date dataCriacao;
    private String titulo;
    private String texto;
    private int posicaoEntreLembretes;

    public Lembrete() {
    }

    public Lembrete(Date dataCriacao, String titulo, String texto, int posicaoEntreLembretes) {
        this.dataCriacao = dataCriacao;
        this.titulo = titulo;
        this.texto = texto;
        this.posicaoEntreLembretes = posicaoEntreLembretes;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public int getPosicaoEntreLembretes() {
        return posicaoEntreLembretes;
    }

    public void setPosicaoEntreLembretes(int posicaoEntreLembretes) {
        this.posicaoEntreLembretes = posicaoEntreLembretes;
    }
}
