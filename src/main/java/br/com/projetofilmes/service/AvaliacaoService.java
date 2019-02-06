package br.com.projetofilmes.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.projetofilmes.domain.Avaliacao;
import br.com.projetofilmes.domain.Usuario;
import br.com.projetofilmes.repository.AvaliacaoRepository;
import br.com.projetofilmes.repository.UsuarioRepository;

@Service
@Transactional
public class AvaliacaoService {

	private AvaliacaoRepository avaliacaoRepository;

	private UsuarioRepository usuarioRepository;

	@Autowired
	public AvaliacaoService(UsuarioRepository usuarioRepository,
			AvaliacaoRepository avaliacaoRepository) {
		this.usuarioRepository = usuarioRepository;
		this.avaliacaoRepository = avaliacaoRepository;

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
