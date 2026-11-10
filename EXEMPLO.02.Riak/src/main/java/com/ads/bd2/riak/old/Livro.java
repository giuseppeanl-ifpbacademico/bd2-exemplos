package com.ads.bd2.riak.old;

public class Livro {
    private String titulo;
    private Autor autor;
    private String resumo;
    private String isbn;
    private Integer copiasObtidas;
    
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public Autor getAutor() {
		return autor;
	}
	public void setAutor(Autor autor) {
		this.autor = autor;
	}
	public String getResumo() {
		return resumo;
	}
	public void setResumo(String resumo) {
		this.resumo = resumo;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public Integer getCopiasObtidas() {
		return copiasObtidas;
	}
	public void setCopiasObtidas(Integer copiasObtidas) {
		this.copiasObtidas = copiasObtidas;
	}
    
    
    
}
