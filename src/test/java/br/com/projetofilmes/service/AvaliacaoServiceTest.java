package br.com.projetofilmes.service;

import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;

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
import br.com.projetofilmes.domain.Filme;
import br.com.projetofilmes.domain.Genero;
import br.com.projetofilmes.domain.Usuario;
import br.com.projetofilmes.dto.AvaliacaoDTO;
import br.com.projetofilmes.repository.FilmeRepository;
import br.com.projetofilmes.repository.GeneroRepository;
import br.com.projetofilmes.repository.UsuarioRepository;

@RunWith(value = SpringRunner.class)
@SpringBootTest
@Transactional
public class AvaliacaoServiceTest {

	@Autowired
	private AvaliacaoService avaliacaoService;

	@Autowired
	private UsuarioRepository usuarioRepository;

	private Usuario usuario;

	@Autowired
	private FilmeRepository filmeRepository;

	@Autowired
	private GeneroRepository generoRepository;

	private Genero genero;

	private Filme filme;

	@Before
	public void init() {
		usuario = new Usuario("samantha@yahoo.com.br");
		usuarioRepository.saveAndFlush(usuario);

		genero = new Genero("scifi");
		generoRepository.saveAndFlush(genero);

		filme = new Filme("Star Wars - Retorno de Jedi", LocalDate.of(1983, Month.MAY, 25), "George Lucas", genero);
		filmeRepository.saveAndFlush(filme);
	}

	@After
	public void finished() {
		avaliacaoService.deleteAll();
		usuarioRepository.deleteAll();
		generoRepository.deleteAll();
		filmeRepository.deleteAll();
	}

	@Test
	public void deveSalvarUmaAvaliacaoQueUsuarioExiste() {
		AvaliacaoDTO avaliacaoASalvar = new AvaliacaoDTO();
		avaliacaoASalvar.setUsuario("samantha@yahoo.com.br");
		avaliacaoASalvar.setIdFilme(filme.getId());
		avaliacaoASalvar.setNota(Integer.valueOf(5));

		avaliacaoService.save(avaliacaoASalvar);
		
		Optional<Usuario> usuario = usuarioRepository.findByEmail(avaliacaoASalvar.getUsuario());

		AvaliacaoDTO avaliacaoSalva = avaliacaoService.findById(avaliacaoASalvar.getId());
		
		Assert.assertEquals("samantha@yahoo.com.br", avaliacaoSalva.getUsuario());
		Assert.assertEquals(usuario.isPresent(), true);
		Assert.assertEquals(filme.getId(), avaliacaoSalva.getIdFilme());
		Assert.assertEquals(Integer.valueOf(5), avaliacaoSalva.getNota());
	}

	@Test
	public void deveSalvarAvaliacaoQueUsuarioNaoExiste() {
		AvaliacaoDTO avaliacaoParaSalvar = new AvaliacaoDTO();
		avaliacaoParaSalvar.setUsuario("sandrasilva@hotmail.com");
		avaliacaoParaSalvar.setIdFilme(filme.getId());
		avaliacaoParaSalvar.setNota(Integer.valueOf(4));

		avaliacaoService.save(avaliacaoParaSalvar);

		AvaliacaoDTO avaliacaoFeita = avaliacaoService.findById(avaliacaoParaSalvar.getId());
		Assert.assertEquals("sandrasilva@hotmail.com", avaliacaoFeita.getUsuario());
		Assert.assertEquals(filme.getId(), avaliacaoFeita.getIdFilme());
		Assert.assertEquals(Integer.valueOf(4), avaliacaoFeita.getNota());
	}

	@Test(expected = DominioInvalidoException.class)
	public void deveValidarErroSeNotaForMaiorQueCinco() {
		AvaliacaoDTO avaliacaoASalvar = new AvaliacaoDTO();
		avaliacaoASalvar.setUsuario("samantha@yahoo.com.br");
		avaliacaoASalvar.setIdFilme(filme.getId());
		avaliacaoASalvar.setNota(Integer.valueOf(7));

		avaliacaoService.save(avaliacaoASalvar);

	}
}
