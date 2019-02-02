package br.com.projetofilmes.controller;

import org.springframework.web.bind.annotation.RestController;

import br.com.projetofilmes.service.FilmeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping(value = "projeto-filmes")
public class FilmeController {

	private FilmeService filmeService;

	@Autowired
	public FilmeController(FilmeService filmeService) {
		this.filmeService = filmeService;
	}

}
