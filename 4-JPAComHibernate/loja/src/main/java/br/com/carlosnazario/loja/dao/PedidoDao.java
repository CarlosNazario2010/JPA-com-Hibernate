package br.com.carlosnazario.loja.dao;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.carlosnazario.loja.modelo.Pedido;
import br.com.carlosnazario.loja.vo.RelatorioVendasVo;

public class PedidoDao {

	private EntityManager em;

	public PedidoDao(EntityManager em) {
		this.em = em;
	}
	
	public void cadastrar(Pedido pedido) {
		this.em.persist(pedido);
	}
	
	public void atualizar(Pedido pedido) {
		this.em.merge(pedido);
	}
	
	public void remover(Pedido pedido) {
		pedido = em.merge(pedido);		// pedido no estado managed
		this.em.remove(pedido);
	}
	
	public Pedido buscarPorId(Long id) {
		return em.find(Pedido.class, id);
	}
	
	public BigDecimal valorTotalVendido() {
		String jpql = "SELECT SUM(p.valorTotal) FROM Pedido p ";
		return em.createQuery(jpql, BigDecimal.class)
				.getSingleResult();
	}
	
	/*
	 * JOIN FETCH carrega um join em um relacionamento LAZY como EAGER
	 */
	public Pedido buscarPedidoComCliente(Long id) {
		String jpql = "SELECT p FROM Pedido p JOIN FETCH p.cliente WHERE p.id = :id";
		return em.createQuery(jpql, Pedido.class)
				.setParameter("id", id)
				.getSingleResult();
	}
	
	public List<RelatorioVendasVo> relatorioVendas() {
		String jpql = "SELECT new br.com.carlosnazario.loja.vo.RelatorioVendasVo("
				+ "produto.nome, "
				+ "SUM(item.quantidade), "
				+ "MAX(pedido.data)) "
				+ "FROM Pedido pedido "
				+ "JOIN pedido.itens item "
				+ "JOIN item.produto produto "
				+ "GROUP BY produto.nome "
				+ "ORDER BY item.quantidade DESC";
		
		return em.createQuery(jpql, RelatorioVendasVo.class)
				.getResultList();
	}
	
//	public List<Object[]> relatorioVendas() {
//		String jpql = "SELECT produto.nome, "
//				+ "SUM(item.quantidade), "
//				+ "MAX(pedido.data) "
//				+ "FROM Pedido pedido "
//				+ "JOIN pedido.itens item "
//				+ "JOIN item.produto produto "
//				+ "GROUP BY produto.nome "
//				+ "ORDER BY item.quantidade DESC";
//		
//		return em.createQuery(jpql, Object[].class)
//				.getResultList();
//	}
	
}
