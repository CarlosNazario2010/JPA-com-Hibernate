package br.com.carlosnazario.loja.modelo;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "categorias")
public class Categoria {
	
	/*
	 * EmbeddedId implementa uma chave composta. Exige que a classe Embeddable
	 * implemente o Serializable, e os metodos equals e hashcode
	 */
	
	@EmbeddedId
	private CategoriaID id;
	
	@SuppressWarnings("unused")
	private String nome;
	
	// A JPA exige o construtor default
	public Categoria() {}

	public Categoria(String nome) {
		this.id = new CategoriaID(nome, "xpto");
	}
	
	public String getNome() {
		return this.id.getNome();
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
}
