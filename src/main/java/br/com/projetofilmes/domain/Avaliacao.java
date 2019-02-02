package br.com.projetofilmes.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "avaliacao")
public class Avaliacao extends BaseDomain {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "avaliacaoid")
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "usuarioid")
	private Usuario usuario;

	@ManyToOne
	@JoinColumn(name = "filmeid")
	private Filme filme;

	@NotNull(message = "A nota da avaliação não pode ser nula")
	@NotEmpty(message = "A nota da avaliação não pode ser vazia")
	private Integer nota;
	
	@SuppressWarnings("unused")
	private Avaliacao() {
		
	}

	public Avaliacao(Usuario usuario, Filme filme, Integer nota) {
		this.usuario = usuario;
		this.filme = filme;
		this.nota = nota;
		validarDomain();
	}

	public Avaliacao(Integer id, Usuario usuario, Filme filme, Integer nota) {
		this(usuario, filme, nota);
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public Filme getFilme() {
		return filme;
	}

	public Integer getNota() {
		return nota;
	}

}
