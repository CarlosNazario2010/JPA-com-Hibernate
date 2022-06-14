package br.com.carlosnazario.loja.vo;

import java.time.LocalDate;

public class RelatorioVendasVo {

	private String nomeProduto;
	private Long quantidadeVendida;
	private LocalDate dataUltimaVenda;
	
	public RelatorioVendasVo(String nomeProduto, 
			Long quantidadeVendida, LocalDate dataUltimaVenda) {
		this.nomeProduto = nomeProduto;
		this.quantidadeVendida = quantidadeVendida;
		this.dataUltimaVenda = dataUltimaVenda;
	}

	@Override
	public String toString() {
		return "RelatorioVendasVo [nomeProduto=" + nomeProduto + 
				", quantidadeVendida=" + quantidadeVendida
				+ ", dataUltimaVenda=" + dataUltimaVenda + "]";
	}

	public String getNomeProduto() {
		return nomeProduto;
	}
	public Long getQuantidadeVendida() {
		return quantidadeVendida;
	}
	public LocalDate getDataUltimaVenda() {
		return dataUltimaVenda;
	}
}
