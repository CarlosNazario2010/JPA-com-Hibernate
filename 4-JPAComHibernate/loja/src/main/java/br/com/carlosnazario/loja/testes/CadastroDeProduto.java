package br.com.carlosnazario.loja.testes;

import java.math.BigDecimal;
import javax.persistence.EntityManager;
import br.com.carlosnazario.loja.dao.CategoriaDao;
import br.com.carlosnazario.loja.dao.ProdutoDao;
import br.com.carlosnazario.loja.modelo.Categoria;
import br.com.carlosnazario.loja.modelo.Produto;
import br.com.carlosnazario.loja.util.JPAUtil;

public class CadastroDeProduto {
	
	public static void main(String[] args) {
		
		cadastrarProduto();
		
		EntityManager em = JPAUtil.getEntityManager();
		ProdutoDao produtoDao = new ProdutoDao(em);
		
//		Produto p = produtoDao.buscarPorId(1l);
//		System.out.println(p.getPreco());
		
//		List<Produto> todos = produtoDao.buscarTodos();
//		todos.forEach(pr -> System.out.println(pr.getNome()));
		
//		List<Produto> todos = produtoDao.buscarPorNome("Motorola");
//		todos.forEach(pr -> System.out.println(pr.getNome()));

//		List<Produto> todos = produtoDao.buscarPorNomeDaCategoria("CELULARES");
//		todos.forEach(pr -> System.out.println(pr.getNome()));
		
		BigDecimal preco = produtoDao.buscarPrecoDoProdutoPeloNome("Motorola");
		System.out.println("Preco do Produto: " + preco);
	}	
		

	private static void cadastrarProduto() {
		EntityManager em = JPAUtil.getEntityManager();
		
		Categoria celulares = new Categoria("CELULARES");
		CategoriaDao categoriaDao = new CategoriaDao(em);
		
		Produto celular = new Produto
				("Motorola","Octa-Core", new BigDecimal("800"), celulares);
		ProdutoDao produtoDao = new ProdutoDao(em);
		
		em.getTransaction().begin();
		categoriaDao.cadastrar(celulares);
		produtoDao.cadastrar(celular);
		em.getTransaction().commit();
		em.close();
	}
}
