package com.ads.bd2.agenda.modelo;

import java.util.Date;

public class Lembrete {
    private Date dataCriacao;
    private Date dataExpiracao;
    
    private String titulo;
    private String texto;
    

    public Lembrete() {
    }

    public Lembrete(Date dataCriacao, String titulo, String texto) {
        this.dataCriacao = dataCriacao;
        this.titulo = titulo;
        this.texto = texto;
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
    
    public Date getDataExpiracao() {
        return dataExpiracao;
    }
    
    public void setDataExpiracao(Date dataExpiracao) {
        this.dataExpiracao = dataExpiracao;
    }
    
}
