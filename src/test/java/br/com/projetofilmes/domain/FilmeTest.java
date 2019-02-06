package br.com.projetofilmes.domain;

import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Test;

public class FilmeTest {

	@Test
	public void deveValidarCriacaoDeUmFilme() {
		Genero acao = new Genero("ação");
		LocalDate dataLancamento = LocalDate.of(1977, 5, 19);
		Filme filme = new Filme("Star Wars - Uma nova Esperança", dataLancamento, "George Lucas", acao, null);

		Assert.assertEquals("Star Wars - Uma nova Esperança", filme.getTitulo());
		Assert.assertEquals(dataLancamento, filme.getDataLancamento());
		Assert.assertEquals("George Lucas", filme.getNomeDiretor());
		Assert.assertEquals(acao, filme.getGenero());
	}
}
