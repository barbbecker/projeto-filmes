package br.com.projetofilmes.controller;

import org.springframework.web.bind.annotation.RestController;

import br.com.projetofilmes.dto.AvaliacaoDTO;
import br.com.projetofilmes.dto.FilmeDTO;
import br.com.projetofilmes.service.FilmeService;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping(value = "projetofilmes")
public class FilmeController {

	private FilmeService filmeService;

	@Autowired
	public FilmeController(FilmeService filmeService) {
		this.filmeService = filmeService;
	}

	@GetMapping(value = "/filmes")
	public ResponseEntity<List<FilmeDTO>> obterFilmes() {
		List<FilmeDTO> filme = filmeService.findAll();
		return new ResponseEntity<>(filme, HttpStatus.OK);
	}

	@GetMapping(value = "/filmes/{id}")
	public ResponseEntity<FilmeDTO> obterFilmePeloId(@PathVariable("id") Integer id) {
		FilmeDTO filme = filmeService.findById(id);
		return new ResponseEntity<FilmeDTO>(filme, HttpStatus.OK);
	}

	@GetMapping(value = "/filmes/{titulo}")
	public ResponseEntity<FilmeDTO> obterFilmePeloTitulo(@PathVariable("titulo") String titulo) {
		FilmeDTO filme = filmeService.findByTitulo(titulo);
		return new ResponseEntity<FilmeDTO>(filme, HttpStatus.OK);
	}

	@PostMapping(value = "/filmes")
	public ResponseEntity<?> salvarFilme(@RequestBody @Valid FilmeDTO filmeDTO) {
		this.filmeService.save(filmeDTO);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping(value = "/filmes/{id}/avaliacao")
	public ResponseEntity<?> salvarAvaliacao(@PathVariable("id") Integer id, @RequestBody AvaliacaoDTO avaliacaoDTO) {
		this.filmeService.adicionarAvaliacao(id, avaliacaoDTO);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping(value = "/filmes/{id}")
	public ResponseEntity<?> deletar(@PathVariable("id") Integer id) {
		this.filmeService.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PutMapping(value = "/filmes/{id}")
	public ResponseEntity<?> update(@RequestBody FilmeDTO filmeDTO) {
		this.filmeService.update(filmeDTO);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
