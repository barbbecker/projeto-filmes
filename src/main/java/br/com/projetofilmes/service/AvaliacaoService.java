package br.com.projetofilmes.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.projetofilmes.domain.Avaliacao;
import br.com.projetofilmes.domain.Filme;
import br.com.projetofilmes.domain.Usuario;
import br.com.projetofilmes.dto.AvaliacaoDTO;
import br.com.projetofilmes.repository.AvaliacaoRepository;
import br.com.projetofilmes.repository.FilmeRepository;
import br.com.projetofilmes.repository.UsuarioRepository;

@Service
@Transactional
public class AvaliacaoService {

	private AvaliacaoRepository avaliacaoRepository;

	private UsuarioRepository usuarioRepository;

	private FilmeRepository filmeRepository;

	@Autowired
	public AvaliacaoService(UsuarioRepository usuarioRepository, FilmeRepository filmeRepository,
			AvaliacaoRepository avaliacaoRepository) {
		this.usuarioRepository = usuarioRepository;
		this.filmeRepository = filmeRepository;
		this.avaliacaoRepository = avaliacaoRepository;

	}

	public void deleteAll() {
		this.avaliacaoRepository.deleteAll();
	}

	public void save(AvaliacaoDTO avaliacaoDTO) {
		Optional<Usuario> encontrarUsuario = usuarioRepository.findByEmail(avaliacaoDTO.getUsuario());
		Usuario usuario = null;
		if (encontrarUsuario.isPresent()) {
			usuario = encontrarUsuario.get();
		} else {
			usuario = new Usuario(avaliacaoDTO.getUsuario());
			this.usuarioRepository.saveAndFlush(usuario);
		}
		Optional<Filme> encontrarFilme = filmeRepository.findById(avaliacaoDTO.getIdFilme());
		Filme filme = encontrarFilme.get();
		Integer nota = avaliacaoDTO.getNota();

		Avaliacao avaliacao = new Avaliacao(usuario, filme, nota);
		this.avaliacaoRepository.saveAndFlush(avaliacao);
		avaliacaoDTO.setId(avaliacao.getId());
	}

	public AvaliacaoDTO findById(Integer id) {
		Optional<Avaliacao> avaliacao = avaliacaoRepository.findById(id);
		if (avaliacao.isPresent()) {
			AvaliacaoDTO avaliacaoDTO = criarAvaliacaoDTO(avaliacao.get());
			return avaliacaoDTO;
		}
		throw new ServiceException("Avaliação precisa ser feita!");
	}

	private AvaliacaoDTO criarAvaliacaoDTO(Avaliacao avaliacao) {
		AvaliacaoDTO avaliacaoDTO = new AvaliacaoDTO();
		avaliacaoDTO.setId(avaliacao.getId());
		avaliacaoDTO.setIdFilme(avaliacao.getFilme().getId());
		avaliacaoDTO.setUsuario(avaliacao.getUsuario().getEmail());
		avaliacaoDTO.setNota(avaliacao.getNota());
		return avaliacaoDTO;
	}

	public Boolean isAvaliacaoSalva(String userEmail, Integer filmeId) {
		if (userEmail == null || filmeId == null) {
			throw new ServiceException("Parametros inválidos");
		}
		
		Optional<Usuario> usuario = this.usuarioRepository.findByEmail(userEmail);
		if (usuario.isPresent()) {			
			Optional<Avaliacao> avaliacao = this.avaliacaoRepository.findByUserEmailAndFilmeId(usuario.get().getId(), filmeId);
			return avaliacao.isPresent();
		} else {
			return false;
		}
	
	}

}
