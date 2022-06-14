


						CICLO DE VIDA DAS ENTIDADES
			


										----->> remove() >> (REMOVED)
										|
										|
	new (TRANSIENT) >> persist() >> (MANAGED) >> commit() / flush() 	>> 	BD
								|		|	  << find() / createQuery()	<<
								|	 	|
								|		|
						merge()	|		|	close() / clear()
								|		|
								|	   \ /
								|	   	|
								|	 
								<<- (DETACHED)
								
			

			
				FLUXOGRAMA DO RELACIONAMENTO DAS TABELAS
				(*) == MANY     (1) == ONE				
								
								
								ITENS_PEDIDO				PRODUTOS
								produto_id	>> (*) >> (1) >	id
						->> (*)	pedido_id					nome
						|		id							descricao
		PEDIDOS			|		preco_unitario				preco
		id 	>>>>>> (1) >>		quantidade					data_cadastro
		data												categoria_id >> (*)
		cliente_id >> (*) 			CLIENTES								 |							 |		
		valor_total 	  >> (1) >> id										 |	
									nome					CATEGORIAS	 	 |
		   													id 		<<< (1) --
		   													nome	
		
		