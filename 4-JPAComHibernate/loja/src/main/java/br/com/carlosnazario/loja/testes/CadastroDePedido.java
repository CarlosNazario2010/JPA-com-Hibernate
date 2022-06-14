package br.com.carlosnazario.loja.testes;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.carlosnazario.loja.dao.CategoriaDao;
import br.com.carlosnazario.loja.dao.ClienteDao;
import br.com.carlosnazario.loja.dao.PedidoDao;
import br.com.carlosnazario.loja.dao.ProdutoDao;
import br.com.carlosnazario.loja.modelo.Categoria;
import br.com.carlosnazario.loja.modelo.Cliente;
import br.com.carlosnazario.loja.modelo.ItemPedido;
import br.com.carlosnazario.loja.modelo.Pedido;
import br.com.carlosnazario.loja.modelo.Produto;
import br.com.carlosnazario.loja.util.JPAUtil;
import br.com.carlosnazario.loja.vo.RelatorioVendasVo;

public class CadastroDePedido {

	public static void main(String[] args) {
		
		popularBancoDeDados();
		EntityManager em = JPAUtil.getEntityManager();
		
		ProdutoDao produtoDao = new ProdutoDao(em);
		ClienteDao clienteDao = new ClienteDao(em);
		
		Produto produto = produtoDao.buscarPorId(1l);
		Produto produto2 = produtoDao.buscarPorId(2l);
		Produto produto3 = produtoDao.buscarPorId(3l);

		Cliente cliente = clienteDao.buscarPorId(1l);
		
		em.getTransaction().begin();
		
		Pedido pedido = new Pedido(cliente);
		pedido.adicionarItem(new ItemPedido(10, pedido, produto));
		pedido.adicionarItem(new ItemPedido(40, pedido, produto2));
		
		Pedido pedido2 = new Pedido(cliente);
		pedido2.adicionarItem(new ItemPedido(2, pedido, produto3));
		
		PedidoDao pedidoDao = new PedidoDao(em);
		pedidoDao.cadastrar(pedido);
		pedidoDao.cadastrar(pedido2);
		
		em.getTransaction().commit();
		
		BigDecimal totalVendido = pedidoDao.valorTotalVendido();
		System.out.println("VALOR TOTAL VENDIDO: " + totalVendido);

		List<RelatorioVendasVo> relatorio = pedidoDao.relatorioVendas();
		relatorio.forEach(System.out::println);
		
	
//		List<Object[]> relatorio = pedidoDao.relatorioVendas();
//		for (Object[] obj : relatorio) {
//			System.out.println(obj[0]);
//			System.out.println(obj[1]);
//			System.out.println(obj[2]);
//		}
		
		em.close();
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

		Cliente cliente = new Cliente("Carlos", "00732962013");

		
		EntityManager em = JPAUtil.getEntityManager();

		CategoriaDao categoriaDao = new CategoriaDao(em);
		ProdutoDao produtoDao = new ProdutoDao(em);		
		ClienteDao clienteDao = new ClienteDao(em);

		em.getTransaction().begin();
		
		categoriaDao.cadastrar(celulares);
		categoriaDao.cadastrar(videogames);
		categoriaDao.cadastrar(informatica);

		produtoDao.cadastrar(celular);
		produtoDao.cadastrar(videogame);
		produtoDao.cadastrar(macbook);		
		
		clienteDao.cadastrar(cliente);

		em.getTransaction().commit();
		em.close();
	}
}
