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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.projetofilmes.dto.GeneroDTO;
import br.com.projetofilmes.service.GeneroService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/projetofilmes")
@Api(value = "genero", description = "Operações relacionadas aos generos")
public class GeneroController {

	private GeneroService generoService;

	@Autowired
	public GeneroController(GeneroService generoService) {
		this.generoService = generoService;
	}

	@GetMapping(value = "/generos", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Retorna uma lista de generos")
	public ResponseEntity<List<GeneroDTO>> obterGeneros() {
		List<GeneroDTO> genero = generoService.findAll();
		return new ResponseEntity<>(genero, HttpStatus.OK);
	}

	@PostMapping(value = "/generos", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Salva um genero")
	public ResponseEntity<?> salvarGenero(@RequestBody @Valid GeneroDTO generoDTO, BindingResult result) {
		if (result.hasErrors()) {
			final List<String> errors = result.getAllErrors().stream().map(ObjectError::getDefaultMessage)
					.collect(Collectors.toList());
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errors);
		}
		this.generoService.save(generoDTO);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
