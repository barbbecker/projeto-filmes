package br.com.projetofilmes.dto;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;

public class FilmeInputDTO {

	private Integer id;

	@NotNull(message = "O campo titulo deve ser preenchido")
	@NotEmpty(message = "O campo titulo deve não pode estar vazio")
	private String titulo;

	@NotNull
	private LocalDate dataLancamento;

	@NotNull
	@NotEmpty
	private String nomeDiretor;

	@NotNull
	@NotEmpty
	private String genero;

	@ApiModelProperty(hidden = true)
	private AvaliacaoDTO avaliacaoDTO;

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

}
