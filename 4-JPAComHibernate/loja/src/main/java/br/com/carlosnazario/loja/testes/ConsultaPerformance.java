package br.com.carlosnazario.loja.testes;

import java.math.BigDecimal;
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

public class ConsultaPerformance {

	/*
	 * Todo relacionamento to one, o padrão é ele ser eager, ele faz o 
	 * carregamento antecipado. Então você carregou o pedido antecipadamente, 
	 * mesmo que você não acesse nada do cliente, que é um relacionamento 
	 * to one, a JPA vai carregar esse relacionamento. Já os relacionamentos 
	 * to many, por padrão, o comportamento é chamado de lazy, que é o 
	 * carregamento preguiçoso, o carregamento tardio.
	 * Em uma aplicação real, uma aplicação web em uma API, pode acontecer de, 
	 * no momento de você acessar uma determinada informação, o entity manager 
	 * já estar fechado, porque é muito comum nas aplicações web, o entity 
	 * manager, o escopo dele, o tempo de vida dele, durar apenas pouco tempo, 
	 * durar apenas a chamada de um método. Às vezes você nem tem o controle, 
	 * é o próprio servidor de aplicação, é o próprio frameworK que está 
	 * gerenciando o entity manager, não foi você que instanciou e que fez o 
	 * close, então você nem sabe onde foi fechado.
	 * Para resolver isso ha o o join fetch, que faz essa mágica de carregar 
	 * um relacionamento, que é lazy apenas nesta consulta. É como se ele 
	 * virasse eager. Então, nessa consulta, ele virá com a entidade principal.
	 */
	
	public static void main(String[] args) {
		
		popularBancoDeDados();
		EntityManager em = JPAUtil.getEntityManager();
		
//		Pedido pedido = em.find(Pedido.class, 1l);
		
		PedidoDao pedidoDao = new PedidoDao(em);
		Pedido pedido = pedidoDao.buscarPedidoComCliente(1l);
		
//		System.out.println(pedido.getData());
//		System.out.println(pedido.getItens().size());
		
		em.close();		// simulando o fechamento do EntityManager
		
		/*
		 * Mesmo com o EntityManager fechado e possivel chamar um registro
		 * da tabela cliente que, apesar de ser lazy, e carregada junto com
		 * a query JOIN FETCH 
		 */
		System.out.println(pedido.getCliente().getDadosPessoais().getNome());

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
		
		Pedido pedido = new Pedido(cliente);
		pedido.adicionarItem(new ItemPedido(10, pedido, celular));
		pedido.adicionarItem(new ItemPedido(40, pedido, videogame));
		
		Pedido pedido2 = new Pedido(cliente);
		pedido2.adicionarItem(new ItemPedido(2, pedido, macbook));
		
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();

		CategoriaDao categoriaDao = new CategoriaDao(em);
		ProdutoDao produtoDao = new ProdutoDao(em);		
		ClienteDao clienteDao = new ClienteDao(em);
		
		categoriaDao.cadastrar(celulares);
		categoriaDao.cadastrar(videogames);
		categoriaDao.cadastrar(informatica);

		produtoDao.cadastrar(celular);
		produtoDao.cadastrar(videogame);
		produtoDao.cadastrar(macbook);		
		
		clienteDao.cadastrar(cliente);
		
		PedidoDao pedidoDao = new PedidoDao(em);
		pedidoDao.cadastrar(pedido);
		pedidoDao.cadastrar(pedido2);
		
		em.getTransaction().commit();
		em.close();
	}
}
