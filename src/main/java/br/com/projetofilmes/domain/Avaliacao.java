package br.com.projetofilmes.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "avaliacao")
public class Avaliacao extends BaseDomain {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;

	@ManyToOne
	@JoinColumn(name = "filme_id")
	private Filme filme;

	@NotNull(message = "A nota da avaliação não pode ser nula")
	@Min(message = "A nota não pode ser menor que 1", value = 1)
	@Max(message = "A nota não pode ser maior que 5", value = 5)
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

	public void informarFilme(Filme filme) {
		this.filme = filme;
	}
}
