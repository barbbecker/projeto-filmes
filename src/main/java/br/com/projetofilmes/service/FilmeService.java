package br.com.projetofilmes.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.projetofilmes.service.ServiceException;
import br.com.projetofilmes.domain.Avaliacao;
import br.com.projetofilmes.domain.Filme;
import br.com.projetofilmes.domain.Genero;
import br.com.projetofilmes.domain.Usuario;
import br.com.projetofilmes.dto.AvaliacaoDTO;
import br.com.projetofilmes.dto.FilmeInputDTO;
import br.com.projetofilmes.dto.FilmeOutputDTO;
import br.com.projetofilmes.repository.FilmeRepository;
import br.com.projetofilmes.repository.GeneroRepository;
import br.com.projetofilmes.repository.UsuarioRepository;

@Service
@Transactional
public class FilmeService {

	private FilmeRepository filmeRepository;

	private GeneroRepository generoRepository;

	private UsuarioRepository usuarioRepository;

	@Autowired
	public FilmeService(FilmeRepository filmeRepository, GeneroRepository generoRepository,
			UsuarioRepository usuarioRepository) {
		this.filmeRepository = filmeRepository;
		this.generoRepository = generoRepository;
		this.usuarioRepository = usuarioRepository;
	}

	public void deleteAll() {
		this.filmeRepository.deleteAll();
	}

	public void save(FilmeInputDTO filmeDTO) {
		Optional<Genero> encontrarGenero = generoRepository.findByName(filmeDTO.getGenero());
		String titulo = filmeDTO.getTitulo();
		LocalDate dataLancamento = filmeDTO.getDataLancamento();
		String nomeDiretor = filmeDTO.getNomeDiretor();
		Genero genero = encontrarGenero.get();

		Filme filme = new Filme(titulo, dataLancamento, nomeDiretor, genero, null);
		this.filmeRepository.saveAndFlush(filme);
		filmeDTO.setId(filme.getId());
	}

	private FilmeOutputDTO criarFilmeDTO(Filme filme) {
		FilmeOutputDTO filmeDTO = new FilmeOutputDTO();
		filmeDTO.setId(filme.getId());
		filmeDTO.setTitulo(filme.getTitulo());
		filmeDTO.setDataLancamento(filme.getDataLancamento());
		filmeDTO.setNomeDiretor(filme.getNomeDiretor());
		filmeDTO.setGenero(filme.getGenero().getNome());
		filmeDTO.setAvaliacao(criarAvaliacao(filme.getAvaliacao()));
		return filmeDTO;
	}

	public FilmeOutputDTO findById(Integer id) {
		Optional<Filme> filme = filmeRepository.findById(id);
		if (filme.isPresent()) {
			FilmeOutputDTO filmeDTO = criarFilmeDTO(filme.get());
			return filmeDTO;
		}
		throw new ServiceException("Filme não encontrado");
	}

	private List<AvaliacaoDTO> criarAvaliacao(List<Avaliacao> avaliacoes) {
		List<AvaliacaoDTO> resposta = new ArrayList<>();

		for (Avaliacao avaliacao : avaliacoes) {
			AvaliacaoDTO avaliacaoDTO = new AvaliacaoDTO();
			avaliacaoDTO.setId(avaliacao.getId());
			avaliacaoDTO.setIdFilme(avaliacao.getFilme().getId());
			avaliacaoDTO.setUsuario(avaliacao.getUsuario().getEmail());
			avaliacaoDTO.setNota(avaliacao.getNota());
			resposta.add(avaliacaoDTO);
		}
		return resposta;
	}

	public List<FilmeOutputDTO> findAll() {
		List<FilmeOutputDTO> todosOsFilmes = new ArrayList<FilmeOutputDTO>();
		List<Filme> filmes = filmeRepository.findAll();

		for (Filme filme : filmes) {
			FilmeOutputDTO filmeDTO = criarFilmeDTO(filme);
			todosOsFilmes.add(filmeDTO);
		}
		return todosOsFilmes;
	}

	public void delete(Integer id) {
		this.filmeRepository.deleteById(id);
	}

	public void update(FilmeInputDTO filmeDTO) {
		Optional<Genero> encontrarGenero = generoRepository.findByName(filmeDTO.getGenero());
		Optional<Filme> filmeEncontrado = filmeRepository.findById(filmeDTO.getId());
		if (filmeEncontrado.isPresent()) {
			String titulo = filmeDTO.getTitulo();
			LocalDate dataLancamento = filmeDTO.getDataLancamento();
			String nomeDiretor = filmeDTO.getNomeDiretor();
			Genero genero = encontrarGenero.get();
			Integer idFilme = filmeEncontrado.get().getId();
			List<Avaliacao> avaliacoes = filmeEncontrado.get().getAvaliacao();
			Filme filme = new Filme(idFilme, titulo, dataLancamento, nomeDiretor, genero, avaliacoes);
			this.filmeRepository.saveAndFlush(filme);
			filmeDTO.setId(filme.getId());
		} else {
			throw new Error("Not Found");
		}

	}

	public void adicionarAvaliacao(Integer id, AvaliacaoDTO avaliacaoDTO) {
		Optional<Usuario> usuarioEncontrado = this.usuarioRepository.findByEmail(avaliacaoDTO.getUsuario());
		Usuario usuarioParacriar = null;
		if (usuarioEncontrado.isPresent()) {
			usuarioParacriar = usuarioEncontrado.get();
		} else {
			usuarioParacriar = new Usuario(avaliacaoDTO.getUsuario());
			this.usuarioRepository.saveAndFlush(usuarioParacriar);
		}

		Optional<Filme> filmeEncontrado = this.filmeRepository.findById(avaliacaoDTO.getIdFilme());
		if (filmeEncontrado.isPresent()) {
			Filme filme = filmeEncontrado.get();
			Integer nota = avaliacaoDTO.getNota();
			Avaliacao avaliacao = new Avaliacao(usuarioParacriar, filme, nota);
			filme.adicionarAvaliacao(avaliacao);
			this.filmeRepository.saveAndFlush(filme);
		}
	}

	public FilmeOutputDTO findByTitulo(String titulo) {
		Optional<Filme> filme = filmeRepository.findByTitulo(titulo);
		if (filme.isPresent()) {
			FilmeOutputDTO filmeDTO = criarFilmeDTO(filme.get());
			return filmeDTO;
		}
		throw new ServiceException("Filme não encontrado!");
	}

	public FilmeOutputDTO getIndicacaoFilme(String email) {
		Optional<Usuario> usuario = usuarioRepository.findByEmail(email);
		Optional<List<Filme>> filmes = this.filmeRepository.getIndicacaoFilme(usuario.get().getId());
		FilmeOutputDTO filmeDto = criarFilmeDTO(this.getRandomFilme(filmes.get()));
		return filmeDto;
	}

	private Filme getRandomFilme(List<Filme> list) { 
		Random rand = new Random(); 
		return list.get(rand.nextInt(list.size())); 
	} 

}
