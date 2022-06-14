package br.com.carlosnazario.loja.dao;

import javax.persistence.EntityManager;

import br.com.carlosnazario.loja.modelo.Categoria;

	/*
	 *  Classes Dao (Data Access Object), por convencao, permite a comunicacao
	 *  das entidades do banco de dados com os controllers . Por padrao, nao 
	 *  se considera boa pratica passar as Entidades direto nos controllers. 
	 *  Dessa forma, as classes dao fazem esse meio de campo 
	 */
public class CategoriaDao {

	private EntityManager em;

	public CategoriaDao(EntityManager em) {
		this.em = em;
	}
	
	public void cadastrar(Categoria categoria) {
		this.em.persist(categoria);
	}

	public void atualizar(Categoria categoria) {
		this.em.merge(categoria);
	}
	
	public void remover(Categoria categoria) {
		categoria = em.merge(categoria);		// entidade no estado managed
		this.em.remove(categoria);
	}
}
