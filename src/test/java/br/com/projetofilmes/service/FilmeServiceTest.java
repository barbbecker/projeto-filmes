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

import br.com.projetofilmes.domain.DominioInvalidoException;
import br.com.projetofilmes.domain.Genero;
import br.com.projetofilmes.domain.Usuario;
import br.com.projetofilmes.dto.AvaliacaoDTO;
import br.com.projetofilmes.dto.FilmeInputDTO;
import br.com.projetofilmes.dto.FilmeOutputDTO;
import br.com.projetofilmes.repository.GeneroRepository;
import br.com.projetofilmes.repository.UsuarioRepository;

@RunWith(value = SpringRunner.class)
@SpringBootTest
@Transactional
public class FilmeServiceTest {

	@Autowired
	private FilmeService filmeService;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private GeneroRepository generoRepository;

	private Genero genero;

	private Usuario usuario;

	@Before
	public void init() {
		usuario = new Usuario("carmelina@gmail.com");
		usuarioRepository.save(usuario);
		genero = new Genero("scifi");
		generoRepository.saveAndFlush(genero);
	}

	@After
	public void finished() {
		generoRepository.deleteAll();
		filmeService.deleteAll();
	}

	@Test
	public void deveSalvarUmFilme() {
		FilmeInputDTO filmeASalvar = new FilmeInputDTO();
		filmeASalvar.setTitulo("Star Wars - Uma nova esperança");
		filmeASalvar.setDataLancamento(LocalDate.of(1977, Month.MAY, 19));
		filmeASalvar.setNomeDiretor("George Lucas");
		filmeASalvar.setGenero("scifi");

		filmeService.save(filmeASalvar);

		FilmeOutputDTO filmeSalvo = filmeService.findById(filmeASalvar.getId());
		Assert.assertEquals("Star Wars - Uma nova esperança", filmeSalvo.getTitulo());
		Assert.assertNotNull(LocalDate.of(1977, Month.MAY, 19));
		Assert.assertEquals("George Lucas", filmeSalvo.getNomeDiretor());
		Assert.assertEquals("scifi", filmeSalvo.getGenero());
	}

	@Test(expected = ServiceException.class)
	public void deveExcluirUmFilme() {
		FilmeInputDTO filmeDTO = new FilmeInputDTO();
		filmeDTO.setTitulo("Star Wars - Ataque dos clones");
		filmeDTO.setDataLancamento(LocalDate.of(2002, Month.MAY, 16));
		filmeDTO.setNomeDiretor("George Lucas");
		filmeDTO.setGenero("scifi");

		filmeService.save(filmeDTO);
		Integer idFilme = filmeDTO.getId();
		filmeService.delete(idFilme);
		filmeService.findById(idFilme);
	}

	@Test
	public void deveAtualizarFilme() {
		FilmeInputDTO filmeDTO = new FilmeInputDTO();
		filmeDTO.setTitulo("Star Wars - Uma nova esperança");
		filmeDTO.setDataLancamento(LocalDate.of(1977, Month.MAY, 19));
		filmeDTO.setNomeDiretor("George Lucas");
		filmeDTO.setGenero("scifi");
		filmeService.save(filmeDTO);

		filmeDTO.setDataLancamento(LocalDate.of(1999, Month.MAY, 22));
		filmeDTO.setNomeDiretor("George");
		filmeService.update(filmeDTO);

		FilmeOutputDTO filmeEditado = filmeService.findById(filmeDTO.getId());
		Assert.assertNotNull(LocalDate.of(1999, Month.MAY, 22));
		Assert.assertEquals("George", filmeEditado.getNomeDiretor());
	}

	@Test
	public void deveBuscarTodosOsFilmes() {
		FilmeInputDTO filmeDTO1 = new FilmeInputDTO();
		filmeDTO1.setTitulo("Star Wars - O despertar da força");
		filmeDTO1.setDataLancamento(LocalDate.of(2015, Month.DECEMBER, 14));
		filmeDTO1.setNomeDiretor("George Lucas");
		filmeDTO1.setGenero("scifi");
		filmeService.save(filmeDTO1);

		FilmeInputDTO filmeDTO2 = new FilmeInputDTO();
		filmeDTO2.setTitulo("Star Wars - O império contra ataca");
		filmeDTO2.setDataLancamento(LocalDate.of(1980, Month.MAY, 21));
		filmeDTO2.setNomeDiretor("George Lucas");
		filmeDTO2.setGenero("scifi");
		filmeService.save(filmeDTO2);

		List<FilmeOutputDTO> filmesSalvos = filmeService.findAll();
		Assert.assertEquals(2, filmesSalvos.size());

	}

	@Test
	public void deveValidarFilmesComAvaliacoes() {
		FilmeInputDTO filmeDTO1 = new FilmeInputDTO();
		filmeDTO1.setTitulo("Star Wars - O despertar da força");
		filmeDTO1.setDataLancamento(LocalDate.of(2015, Month.DECEMBER, 14));
		filmeDTO1.setNomeDiretor("George Lucas");
		filmeDTO1.setGenero("scifi");
		filmeService.save(filmeDTO1);

		AvaliacaoDTO avaliacao1 = new AvaliacaoDTO();
		avaliacao1.setUsuario("barbarebecker@gmail.com");
		avaliacao1.setNota(Integer.valueOf(5));

		AvaliacaoDTO avaliacao2 = new AvaliacaoDTO();
		avaliacao2.setUsuario("robertamartins@gmail.com");
		avaliacao2.setNota(Integer.valueOf(5));

		filmeService.adicionarAvaliacao(filmeDTO1.getId(), avaliacao1);
		filmeService.adicionarAvaliacao(filmeDTO1.getId(), avaliacao2);

		FilmeOutputDTO filmeComAvaliacao = filmeService.findById(filmeDTO1.getId());
		Assert.assertEquals(2, filmeComAvaliacao.getAvaliacao().size());
	}

	@Test(expected = DominioInvalidoException.class)
	public void deveValidarErroSeNotaForMaiorQueCinco() {
		FilmeInputDTO filmeDTO1 = new FilmeInputDTO();
		filmeDTO1.setTitulo("Star Wars - O despertar da força");
		filmeDTO1.setDataLancamento(LocalDate.of(2015, Month.DECEMBER, 14));
		filmeDTO1.setNomeDiretor("George Lucas");
		filmeDTO1.setGenero("scifi");
		filmeService.save(filmeDTO1);

		AvaliacaoDTO avaliacaoASalvar = new AvaliacaoDTO();
		avaliacaoASalvar.setUsuario("samantha@yahoo.com.br");
		avaliacaoASalvar.setNota(Integer.valueOf(7));

		filmeService.adicionarAvaliacao(filmeDTO1.getId(), avaliacaoASalvar);

	}

}
