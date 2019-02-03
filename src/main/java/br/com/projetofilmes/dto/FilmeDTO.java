package br.com.projetofilmes.dto;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class FilmeDTO {

	private Integer id;

	@NotNull
	@NotEmpty
	private String titulo;

	@NotNull
	private LocalDate dataLancamento;

	@NotNull
	@NotEmpty
	private String nomeDiretor;

	private String genero;

	// inclusao
	private AvaliacaoDTO avaliacaoDTO;

	// consulta
	private List<AvaliacaoDTO> avaliacao;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public LocalDate getDataLancamento() {
		return dataLancamento;
	}

	public void setDataLancamento(LocalDate dataLancamento) {
		this.dataLancamento = dataLancamento;
	}

	public String getNomeDiretor() {
		return nomeDiretor;
	}

	public void setNomeDiretor(String nomeDiretor) {
		this.nomeDiretor = nomeDiretor;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public AvaliacaoDTO getAvaliacaoDTO() {
		return avaliacaoDTO;
	}

	public void setAvaliacaoDTO(AvaliacaoDTO avaliacaoDTO) {
		this.avaliacaoDTO = avaliacaoDTO;
	}

	public List<AvaliacaoDTO> getAvaliacao() {
		return avaliacao;
	}

	public void setAvaliacao(List<AvaliacaoDTO> avaliacao) {
		this.avaliacao = avaliacao;
	}

}
