package br.com.projetofilmes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.projetofilmes.dto.GeneroDTO;
import br.com.projetofilmes.service.GeneroService;

@RestController
@RequestMapping(value = "projetofilmes")
public class GeneroController {

	private GeneroService generoService;

	@Autowired
	public GeneroController(GeneroService generoService) {
		this.generoService = generoService;
	}

	@GetMapping(value = "/generos")
	public ResponseEntity<List<GeneroDTO>> obterGeneros() {
		List<GeneroDTO> genero = generoService.findAll();
		return new ResponseEntity<>(genero, HttpStatus.OK);
	}

}
