package br.com.projetofilmes.dto;

import javax.validation.constraints.NotNull;

public class GeneroDTO {

	private Integer id;

	@NotNull
	private String nome;

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
