package br.com.carlosnazario.loja.testes;

import java.math.BigDecimal;
import javax.persistence.EntityManager;

import br.com.carlosnazario.loja.dao.CategoriaDao;
import br.com.carlosnazario.loja.dao.ProdutoDao;
import br.com.carlosnazario.loja.modelo.Categoria;
import br.com.carlosnazario.loja.modelo.Produto;
import br.com.carlosnazario.loja.util.JPAUtil;

public class TestesCriteria {

	public static void main(String[] args) {
	
		popularBancoDeDados();
		EntityManager em = JPAUtil.getEntityManager();
		ProdutoDao produtoDao = new ProdutoDao(em);
		
		produtoDao.buscarPorParametrosComCriteria(null, new BigDecimal("4500"), null);

//		produtoDao.buscarPorParametros(null, new BigDecimal("4500"), null);

	}
	
	private static void popularBancoDeDados() {
		
		Categoria celulares = new Categoria("CELULARES");
		Categoria videogames = new Categoria("VIDEOGAMES");
		Categoria informatica = new Categoria("INFORMATICA");
		
		Produto celular = new Produto
				("Motorola","Octa-Core", new BigDecimal("800"), celulares);
		Produto videogame = new Produto
				("PlayStation5", "Nova Geracao", new BigDecimal("4500"), videogames);
		Produto macbook = new Produto
				("MacBook", "Note Apple", new BigDecimal("8000"), informatica);
		
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();

		CategoriaDao categoriaDao = new CategoriaDao(em);
		ProdutoDao produtoDao = new ProdutoDao(em);		
		categoriaDao.cadastrar(celulares);
		categoriaDao.cadastrar(videogames);
		categoriaDao.cadastrar(informatica);

		produtoDao.cadastrar(celular);
		produtoDao.cadastrar(videogame);
		produtoDao.cadastrar(macbook);		
		
		em.getTransaction().commit();
		em.close();
	}
}
