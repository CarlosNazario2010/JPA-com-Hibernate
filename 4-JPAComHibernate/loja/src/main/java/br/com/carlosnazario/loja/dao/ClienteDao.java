package br.com.carlosnazario.loja.dao;

import javax.persistence.EntityManager;

import br.com.carlosnazario.loja.modelo.Cliente;

public class ClienteDao {

	private EntityManager em;

	public ClienteDao(EntityManager em) {
		this.em = em;
	}
	
	public void cadastrar(Cliente cliente) {
		this.em.persist(cliente);
	}
	
	public void atualizar(Cliente cliente) {
		this.em.merge(cliente);
	}
	
	public void remover(Cliente cliente) {
		cliente = em.merge(cliente);			// cliente no estado managed
		this.em.remove(cliente);
	}
	
	public Cliente buscarPorId(Long id) {
		return em.find(Cliente.class, id);
	}
}
