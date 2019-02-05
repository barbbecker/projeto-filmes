package br.com.projetofilmes.domain;

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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "filme")
public class Filme extends BaseDomain {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "filmeid")
	private Integer id;

	@NotNull(message = "O titulo do filme não pode ser nulo")
	@NotEmpty(message = "O titulo do filme não pode ser vazio")
	private String titulo;

	@NotNull
	@Column(name = "data_lancamento")
	private LocalDate dataLancamento;

	@NotNull(message = "O nome do Diretor não pode ser nulo")
	@NotEmpty(message = "O nome do Diretor não pode ser vazio")
	@Column(name = "nome_diretor")
	private String nomeDiretor;

	@ManyToOne
	@JoinColumn(name = "generoid")
	private Genero genero;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "filme")
	private List<Avaliacao> avaliacoes;

	@SuppressWarnings("unused")
	private Filme() {

	}

	public Filme(String titulo, LocalDate dataLancamento, String nomeDiretor, Genero genero) {
		this.titulo = titulo;
		this.dataLancamento = dataLancamento;
		this.nomeDiretor = nomeDiretor;
		this.genero = genero;
		this.avaliacoes = new ArrayList<>();
		validarDomain();
	}

	public Filme(Integer id, String titulo, LocalDate dataLancamento, String nomeDiretor, Genero genero) {
		this(titulo, dataLancamento, nomeDiretor, genero);
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public String getTitulo() {
		return titulo;
	}

	public LocalDate getDataLancamento() {
		return dataLancamento;
	}

	public String getNomeDiretor() {
		return nomeDiretor;
	}

	public Genero getGenero() {
		return genero;
	}

	public List<Avaliacao> getAvaliacao() {
		return avaliacoes;
	}

	public void adicionarAvaliacao(Avaliacao avaliacao) {
		avaliacao.informarFilme(this);
		this.avaliacoes.add(avaliacao);
	}

}
