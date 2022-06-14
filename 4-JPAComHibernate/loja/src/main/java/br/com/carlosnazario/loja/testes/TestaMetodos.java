package br.com.carlosnazario.loja.testes;

import javax.persistence.EntityManager;
import br.com.carlosnazario.loja.modelo.Categoria;
import br.com.carlosnazario.loja.util.JPAUtil;

public class TestaMetodos {
	
	/*
	 * quando damos "new", isto é, quando instanciamos uma entidade, para a JPA, 
	 * a entidade está em um estado chamado de TRANSIENT. O estado transient é de 
	 * uma entidade que nunca foi persistida, não está gravada no banco de dados, 
	 * não tem um id e não está sendo gerenciada pela JPA.
	 * 
	 * A JPA desconhece essa entidade. É como se fosse um objeto Java puro, que 
	 * não tem nada a ver com persistência. Esse é o primeiro estado de uma 
	 * entidade. Nesse estado, se alteramos o atributo da entidade ou qualquer 
	 * outra coisa que façamos com a entidade, o EntityManager não está 
	 * gerenciando, nem verificando, e não vai sincronizar com o banco de dados 
	 * se nós commitarmos e fizermos o close do EntityManager.
	 * 
	 * Porém, quando chamamos o método persist(), ela move do estado transient 
	 * para o estado MANAGED ou gerenciado. Managed é o principal estado que 
	 * uma entidade pode estar, portanto, tudo que acontece com uma entidade 
	 * nesse estado, a JPA observará e poderá sincronizar com o banco de dados, 
	 * a depender do que fizermos.
	 * 
	 * No momento em que fazemos o commit() ou o flush, a JPA pega todas as 
	 * entidades que estiverem no estado managed e sincroniza com o banco de 
	 * dados. Então, tínhamos uma entidade que era transient, está gerenciada 
	 * e, depois que commitamos, ele perceberá que a entidade não tem id, que 
	 * ela era transient, ou seja, é necessário fazer um insert dela no banco 
	 * de dados.
	 * 
	 * A partir do momento em que fechamos o EntityManager, isto é, em.close() 
	 * ou clear() (para limpar as entidades gerenciadas do EntityManager), a 
	 * categoria muda de estado. Se ela estava salva antes, passa para um 
	 * estado chamado de DETACHED, que é um estado destacado.
	 * 
	 * O detached é um estado em que a entidade não é mais transient, porque 
	 * tem id, já foi salva no banco de dados, porém, não está mais sendo 
	 * gerenciada. Portanto, se mexermos nos atributos, a JPA não disparará 
	 * update e nem fará mais nada. 
	 * 
	 * Depois de fechado o EntityManager, a entidade está no estado detached, 
	 * e, neste estado, nada que alterarmos na entidade será sincronizado 
	 * automaticamente com o banco de dados. Então, surge a questão de como 
	 * voltar a entidade para o estado managed.
	 * 
	 * Se ao invés de fecharmos o EntityManager, escrevermos clear(), o 
	 * EntityManager ainda estárá aberto, quer dizer que ainda podemos 
	 * trabalhar com ele, porém, com o clear() nós tiramos todas as entidades, 
	 * todas estão detached.
	 * 
	 * Então, o que precisamos fazer se quisermos voltar para o estado managed? 
	 * Existe outro método, o merge(), que tem como objetivo pegar uma entidade 
	 * que está no estado detached e retorná-la ao estado managed (gerenciado). 
	 * A partir dali, qualquer mudança que fizermos na entidade será analisada e 
	 * sincronizada ao banco de dados quando realizarmos o commit() da transação 
	 * ou flush()
	 * 
	 * Quando chamamos o método merge() e passamos uma entidade, ele não muda 
	 * o estado dessa entidade para managed, ele devolve uma nova referência, 
	 * e esta sim, estará no estado managed. Mas, a que passamos como parâmetro, 
	 * no nosso caso, "celulares", continua detached. Sendo assim, se desejarmos 
	 * mudar o atributo, é necessário criar uma nova categoria e atribuir, ou, 
	 * para mudar de fato o objeto, precisamos fazer celulares = em.merge(celulares); 
	 * ("celulares", que é o nosso objeto, agora aponta para o retorno do método 
	 * merge()). Ou seja, o método merge() devolve a entidade no estado managed.
	 * 
	 * 
	 */
	
	public static void main(String[] args) {
		
		EntityManager em = JPAUtil.getEntityManager();
		
		Categoria celulares = new Categoria("CELULARES"); // transient
		
		em.getTransaction().begin();
		
		em.persist(celulares); 		// managed  INSERT
		celulares.setNome("XPTO");	// UPDATE
		
		em.flush();					
		em.clear();					// detached
		
		celulares = em.merge(celulares); 	// managed SELECT
		celulares.setNome("1234");			// UPDATE
		em.flush();
		
		em.remove(celulares);		// DELETE
		em.flush();
		
		em.close();
	}
}
