package br.com.projetofilmes.service;

import java.time.LocalDate;
import java.time.Month;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.projetofilmes.domain.Filme;
import br.com.projetofilmes.domain.Genero;
import br.com.projetofilmes.dto.FilmeDTO;
import br.com.projetofilmes.repository.GeneroRepository;

@RunWith(value = SpringRunner.class)
@SpringBootApplication
public class FilmeServiceTest {

	@Autowired
	private FilmeService filmeService;

	@Autowired
	private GeneroRepository generoRepository;

	private Genero genero;

	@Before
	public void init() {
		genero = new Genero("ação");
		generoRepository.saveAndFlush(genero);
	}

	@After
	public void finished() {
		filmeService.deleteAll();
		generoRepository.deleteAll();
	}

	@Test
	public void deveSalvarUmFilme() {
		FilmeDTO filmeASalvar = new FilmeDTO();
		filmeASalvar.setTitulo("Star Wars - Uma nova esperança");
		filmeASalvar.setDataLancamento(LocalDate.of(1977, Month.MAY, 19));
		filmeASalvar.setNomeDiretor("George Lucas");
		filmeASalvar.setGenero(genero);

		filmeService.save(filmeASalvar);
		Filme filmeSalvo = filmeService.findById(filmeASalvar.getId());
		Assert.assertEquals("Star Wars - Uma nova esperança", filmeSalvo.getTitulo());
		Assert.assertNotNull(LocalDate.of(1977, Month.MAY, 19));
		Assert.assertEquals("George Lucas", filmeSalvo.getNomeDiretor());
		Assert.assertEquals(genero, filmeSalvo.getGenero());
	}
}
