package br.com.projetofilmes.service;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import br.com.projetofilmes.domain.Genero;
import br.com.projetofilmes.dto.AvaliacaoDTO;
import br.com.projetofilmes.dto.FilmeDTO;
import br.com.projetofilmes.repository.GeneroRepository;

@RunWith(value = SpringRunner.class)
@SpringBootTest
@Transactional
public class FilmeServiceTest {

	@Autowired
	private FilmeService filmeService;

	@Autowired
	private AvaliacaoService avaliacaoService;

	@Autowired
	private GeneroRepository generoRepository;

	private Genero genero;

	@Before
	public void init() {
		genero = new Genero("scifi");
		generoRepository.saveAndFlush(genero);
	}

	@After
	public void finished() {
		filmeService.deleteAll();
		generoRepository.deleteAll();
	}

//	@Test
//	public void deveSalvarUmFilme() {
//		FilmeDTO filmeASalvar = new FilmeDTO();
//		filmeASalvar.setTitulo("Star Wars - Uma nova esperança");
//		filmeASalvar.setDataLancamento(LocalDate.of(1977, Month.MAY, 19));
//		filmeASalvar.setNomeDiretor("George Lucas");
//		filmeASalvar.setGenero("scifi");
//
//		filmeService.save(filmeASalvar);
//
//		FilmeDTO filmeSalvo = filmeService.findById(filmeASalvar.getId());
//		Assert.assertEquals("Star Wars - Uma nova esperança", filmeSalvo.getTitulo());
//		Assert.assertNotNull(LocalDate.of(1977, Month.MAY, 19));
//		Assert.assertEquals("George Lucas", filmeSalvo.getNomeDiretor());
//		Assert.assertEquals("scifi", filmeSalvo.getGenero());
//	}

//	@Test(expected = ServiceException.class)
//	public void deveExcluirUmFilme() {
//		FilmeDTO filmeDTO = new FilmeDTO();
//		filmeDTO.setTitulo("Star Wars - Ataque dos clones");
//		filmeDTO.setDataLancamento(LocalDate.of(2002, Month.MAY, 16));
//		filmeDTO.setNomeDiretor("George Lucas");
//		filmeDTO.setGenero("scifi");
//
//		filmeService.save(filmeDTO);
//		Integer idFilme = filmeDTO.getId();
//		filmeService.delete(idFilme);
//		filmeService.findById(idFilme);
//	}

//	@Test
//	public void deveAtualizarFilme() {
//		FilmeDTO filmeDTO = new FilmeDTO();
//		filmeDTO.setTitulo("Star Wars - Uma nova esperança");
//		filmeDTO.setDataLancamento(LocalDate.of(1977, Month.MAY, 19));
//		filmeDTO.setNomeDiretor("George Lucas");
//		filmeDTO.setGenero("scifi");
//		filmeService.save(filmeDTO);
//
//		FilmeDTO filmeParaEditar = filmeService.findById(filmeDTO.getId());
//		filmeParaEditar.setDataLancamento(LocalDate.of(1999, Month.MAY, 22));
//		filmeParaEditar.setNomeDiretor("George");
//		filmeService.update(filmeParaEditar);
//
//		FilmeDTO filmeEditado = filmeService.findById(filmeParaEditar.getId());
//		Assert.assertNotNull(LocalDate.of(1999, Month.MAY, 22));
//		Assert.assertEquals("George", filmeEditado.getNomeDiretor());
//	}
//
//	@Test
//	public void deveBuscarTodosOsFilmes() {
//		FilmeDTO filmeDTO1 = new FilmeDTO();
//		filmeDTO1.setTitulo("Star Wars - O despertar da força");
//		filmeDTO1.setDataLancamento(LocalDate.of(2015, Month.DECEMBER, 14));
//		filmeDTO1.setNomeDiretor("George Lucas");
//		filmeDTO1.setGenero("scifi");
//		filmeService.save(filmeDTO1);
//
//		FilmeDTO filmeDTO2 = new FilmeDTO();
//		filmeDTO2.setTitulo("Star Wars - O império contra ataca");
//		filmeDTO2.setDataLancamento(LocalDate.of(1980, Month.MAY, 21));
//		filmeDTO2.setNomeDiretor("George Lucas");
//		filmeDTO2.setGenero("scifi");
//		filmeService.save(filmeDTO2);
//
//		List<FilmeDTO> filmesSalvos = filmeService.findAll();
//		Assert.assertEquals(2, filmesSalvos.size());
//
//	}

	@Test
	public void deveValidarFilmesComAvaliacoes() {
		FilmeDTO filmeDTO1 = new FilmeDTO();
		filmeDTO1.setTitulo("Star Wars - O despertar da força");
		filmeDTO1.setDataLancamento(LocalDate.of(2015, Month.DECEMBER, 14));
		filmeDTO1.setNomeDiretor("George Lucas");
		filmeDTO1.setGenero("scifi");
		filmeService.save(filmeDTO1);

		AvaliacaoDTO avaliacao1 = new AvaliacaoDTO();
		avaliacao1.setIdFilme(filmeDTO1.getId());
		avaliacao1.setUsuario("barbarebecker@gmail.com");
		avaliacao1.setNota(Integer.valueOf(5));
		avaliacaoService.save(avaliacao1);

		AvaliacaoDTO avaliacao2 = new AvaliacaoDTO();
		avaliacao2.setIdFilme(filmeDTO1.getId());
		avaliacao2.setUsuario("robertamartins@gmail.com");
		avaliacao2.setNota(Integer.valueOf(5));
		avaliacaoService.save(avaliacao2);

		FilmeDTO filmeEncontrado = filmeService.findById(filmeDTO1.getId());
		Assert.assertEquals(2, filmeEncontrado.getAvaliacao().size());
	}

}
