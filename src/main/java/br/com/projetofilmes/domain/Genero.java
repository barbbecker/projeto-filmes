package br.com.projetofilmes.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "genero")
public class Genero extends BaseDomain {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotNull
	private String nome;

	@SuppressWarnings("unused")
	private Genero() {

	}

	public Genero(String nome) {
		this.nome = nome;
		validarDomain();
	}

	public Genero(Integer id, String nome) {
		this(nome);
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

}
