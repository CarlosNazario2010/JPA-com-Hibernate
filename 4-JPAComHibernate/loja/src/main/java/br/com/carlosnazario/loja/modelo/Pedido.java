package br.com.carlosnazario.loja.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "pedidos")
public class Pedido {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	// id gerado automaticamente pelo BD
	private Long id;
	
	@Column(name = "valor_total")
	private BigDecimal valorTotal = BigDecimal.ZERO;
	
	private LocalDate data = LocalDate.now();
	
	/*
	 * Muitos pedidos podem ter um cliente
	 * A boa prática é: todo relacionamento to one, coloque o carregamento 
	 * para ser lazy, (fetch = FetchType.LAZY), porque por padrão, ele é eager. 
	 * Se você carregou o pedido, não importa, ele sempre vai carregar o 
	 * cliente, se ele for um relacionamento to one, então mudamos para lazy.
	 */
	@ManyToOne(fetch = FetchType.LAZY)						
	private Cliente cliente;
	
	/*
	 * um pedido pode ter muitos itens
	 * mapeado pelo atributo pedido da classe ItemPedido
	 * e com cascade tudo que for feito no Pedido tambem sera feito no ItemPedido
	 */
	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)	 
	private List<ItemPedido> itens = new ArrayList<>();
	
	public Pedido() {}

	public Pedido(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public void adicionarItem(ItemPedido item) {
		item.setPedido(this);
		this.itens.add(item);
		this.valorTotal = this.valorTotal.add(item.getValor());
	}

	public Long getId() {
		return id;
	}
	public BigDecimal getValorTotal() {
		return valorTotal;
	}
	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}
	public LocalDate getData() {
		return data;
	}
	public void setData(LocalDate data) {
		this.data = data;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public List<ItemPedido> getItens() {
		return itens;
	}
}
