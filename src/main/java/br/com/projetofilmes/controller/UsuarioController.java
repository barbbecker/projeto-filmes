package br.com.projetofilmes.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.projetofilmes.dto.UsuarioDTO;
import br.com.projetofilmes.service.UsuarioService;
import io.swagger.annotations.Api;

@RestController
@RequestMapping(value = "/projetofilmes")
public class UsuarioController {

	private UsuarioService usuarioService;

	@Autowired
	public UsuarioController(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	@PostMapping(value = "/usuarios")
	public ResponseEntity<?> salvarUsuario(@RequestBody @Valid UsuarioDTO usuarioDTO) {
		this.usuarioService.save(usuarioDTO);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
