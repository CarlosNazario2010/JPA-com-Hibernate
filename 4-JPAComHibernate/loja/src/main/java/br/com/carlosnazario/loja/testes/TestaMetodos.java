package br.com.carlosnazario.loja.testes;

import javax.persistence.EntityManager;
import br.com.carlosnazario.loja.modelo.Categoria;
import br.com.carlosnazario.loja.util.JPAUtil;

public class TestaMetodos {
	
	/*
	 * quando damos "new", isto �, quando instanciamos uma entidade, para a JPA, 
	 * a entidade est� em um estado chamado de TRANSIENT. O estado transient � de 
	 * uma entidade que nunca foi persistida, n�o est� gravada no banco de dados, 
	 * n�o tem um id e n�o est� sendo gerenciada pela JPA.
	 * 
	 * A JPA desconhece essa entidade. � como se fosse um objeto Java puro, que 
	 * n�o tem nada a ver com persist�ncia. Esse � o primeiro estado de uma 
	 * entidade. Nesse estado, se alteramos o atributo da entidade ou qualquer 
	 * outra coisa que fa�amos com a entidade, o EntityManager n�o est� 
	 * gerenciando, nem verificando, e n�o vai sincronizar com o banco de dados 
	 * se n�s commitarmos e fizermos o close do EntityManager.
	 * 
	 * Por�m, quando chamamos o m�todo persist(), ela move do estado transient 
	 * para o estado MANAGED ou gerenciado. Managed � o principal estado que 
	 * uma entidade pode estar, portanto, tudo que acontece com uma entidade 
	 * nesse estado, a JPA observar� e poder� sincronizar com o banco de dados, 
	 * a depender do que fizermos.
	 * 
	 * No momento em que fazemos o commit() ou o flush, a JPA pega todas as 
	 * entidades que estiverem no estado managed e sincroniza com o banco de 
	 * dados. Ent�o, t�nhamos uma entidade que era transient, est� gerenciada 
	 * e, depois que commitamos, ele perceber� que a entidade n�o tem id, que 
	 * ela era transient, ou seja, � necess�rio fazer um insert dela no banco 
	 * de dados.
	 * 
	 * A partir do momento em que fechamos o EntityManager, isto �, em.close() 
	 * ou clear() (para limpar as entidades gerenciadas do EntityManager), a 
	 * categoria muda de estado. Se ela estava salva antes, passa para um 
	 * estado chamado de DETACHED, que � um estado destacado.
	 * 
	 * O detached � um estado em que a entidade n�o � mais transient, porque 
	 * tem id, j� foi salva no banco de dados, por�m, n�o est� mais sendo 
	 * gerenciada. Portanto, se mexermos nos atributos, a JPA n�o disparar� 
	 * update e nem far� mais nada. 
	 * 
	 * Depois de fechado o EntityManager, a entidade est� no estado detached, 
	 * e, neste estado, nada que alterarmos na entidade ser� sincronizado 
	 * automaticamente com o banco de dados. Ent�o, surge a quest�o de como 
	 * voltar a entidade para o estado managed.
	 * 
	 * Se ao inv�s de fecharmos o EntityManager, escrevermos clear(), o 
	 * EntityManager ainda est�r� aberto, quer dizer que ainda podemos 
	 * trabalhar com ele, por�m, com o clear() n�s tiramos todas as entidades, 
	 * todas est�o detached.
	 * 
	 * Ent�o, o que precisamos fazer se quisermos voltar para o estado managed? 
	 * Existe outro m�todo, o merge(), que tem como objetivo pegar uma entidade 
	 * que est� no estado detached e retorn�-la ao estado managed (gerenciado). 
	 * A partir dali, qualquer mudan�a que fizermos na entidade ser� analisada e 
	 * sincronizada ao banco de dados quando realizarmos o commit() da transa��o 
	 * ou flush()
	 * 
	 * Quando chamamos o m�todo merge() e passamos uma entidade, ele n�o muda 
	 * o estado dessa entidade para managed, ele devolve uma nova refer�ncia, 
	 * e esta sim, estar� no estado managed. Mas, a que passamos como par�metro, 
	 * no nosso caso, "celulares", continua detached. Sendo assim, se desejarmos 
	 * mudar o atributo, � necess�rio criar uma nova categoria e atribuir, ou, 
	 * para mudar de fato o objeto, precisamos fazer celulares = em.merge(celulares); 
	 * ("celulares", que � o nosso objeto, agora aponta para o retorno do m�todo 
	 * merge()). Ou seja, o m�todo merge() devolve a entidade no estado managed.
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
