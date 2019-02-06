package br.com.projetofilmes.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.projetofilmes.dto.UsuarioDTO;
import br.com.projetofilmes.service.UsuarioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/projetofilmes")
@Api(value = "usuario", description = "Operações relacionadas aos usuarios")
public class UsuarioController {

	private UsuarioService usuarioService;

	@Autowired
	public UsuarioController(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	@PostMapping(value = "/usuarios", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Salva um usuario")
	public ResponseEntity<?> salvarUsuario(@RequestBody @Valid UsuarioDTO usuarioDTO, BindingResult result) {
		if (result.hasErrors()) {
			final List<String> errors = result.getAllErrors().stream().map(ObjectError::getDefaultMessage)
					.collect(Collectors.toList());
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errors);
		}
		this.usuarioService.save(usuarioDTO);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
