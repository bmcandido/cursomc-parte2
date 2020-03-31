package com.brunocandido.cursomc.dto;

import java.io.Serializable;

import com.brunocandido.cursomc.domain.Categoria;

public class CategoriaDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String nome;

	public CategoriaDTO() {

	}
	
	public CategoriaDTO(Categoria obj) {
		id = obj.getId();
		nome = obj.getNome();
		
	} //Criado para lista no JASON quando chamar /categoria trazer somente a categoria e não categoria e lista de produtos

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
