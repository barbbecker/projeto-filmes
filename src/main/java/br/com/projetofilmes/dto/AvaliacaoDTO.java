package br.com.projetofilmes.dto;

import br.com.projetofilmes.domain.Filme;
import br.com.projetofilmes.domain.Usuario;

public class AvaliacaoDTO {

	private Integer id;

	private Usuario usuario;

	private Filme filme;

	private Integer nota;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Filme getFilme() {
		return filme;
	}

	public void setFilme(Filme filme) {
		this.filme = filme;
	}

	public Integer getNota() {
		return nota;
	}

	public void setNota(Integer nota) {
		this.nota = nota;
	}

}
