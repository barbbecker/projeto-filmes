package br.com.projetofilmes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.projetofilmes.domain.Usuario;
import br.com.projetofilmes.dto.UsuarioDTO;
import br.com.projetofilmes.repository.UsuarioRepository;

@Service
@Transactional
public class UsuarioService {

	private UsuarioRepository usuarioRepository;

	@Autowired
	public UsuarioService(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}

	public void save(UsuarioDTO usuarioDTO) {
		String email = usuarioDTO.getEmail();

		Usuario usuario = new Usuario(email);
		this.usuarioRepository.saveAndFlush(usuario);
	}

}
