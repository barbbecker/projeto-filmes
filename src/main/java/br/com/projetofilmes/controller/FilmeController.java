package br.com.projetofilmes.controller;

import org.springframework.web.bind.annotation.RestController;

import br.com.projetofilmes.dto.AvaliacaoDTO;
import br.com.projetofilmes.dto.FilmeInputDTO;
import br.com.projetofilmes.dto.FilmeOutputDTO;
import br.com.projetofilmes.service.AvaliacaoService;
import br.com.projetofilmes.service.FilmeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping(value = "/projetofilmes")
@Api(value = "filme", description = "Operações relacionadas aos filmes")
public class FilmeController {

	private FilmeService filmeService;

	private AvaliacaoService avaliacaoService;

	@Autowired
	public FilmeController(FilmeService filmeService, AvaliacaoService avaliacaoService) {
		this.filmeService = filmeService;
		this.avaliacaoService = avaliacaoService;
	}

	@GetMapping(value = "/filmes", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Retorna uma lista de filmes com suas respectivas avaliações")
	public ResponseEntity<List<FilmeOutputDTO>> obterFilmes() {
		List<FilmeOutputDTO> filme = filmeService.findAll();
		return new ResponseEntity<>(filme, HttpStatus.OK);
	}

	@GetMapping(value = "/filmes/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Busca um filme pelo ID")
	public ResponseEntity<FilmeOutputDTO> obterFilmePeloId(@PathVariable("id") Integer id) {
		FilmeOutputDTO filme = filmeService.findById(id);
		return new ResponseEntity<FilmeOutputDTO>(filme, HttpStatus.OK);
	}

	@GetMapping(value = "/filmes/titulo/{titulo}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Busca um filme pelo titulo")
	public ResponseEntity<FilmeOutputDTO> obterFilmePeloTitulo(@PathVariable("titulo") String titulo) {
		FilmeOutputDTO filme = filmeService.findByTitulo(titulo);
		return new ResponseEntity<FilmeOutputDTO>(filme, HttpStatus.OK);
	}

	@PostMapping(value = "/filmes", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Salva um filme")
	public ResponseEntity<?> salvarFilme(@RequestBody @Valid FilmeInputDTO filmeDTO, BindingResult result) {
		if (result.hasErrors()) {
			final List<String> errors = result.getAllErrors().stream().map(ObjectError::getDefaultMessage)
					.collect(Collectors.toList());
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errors);
		}
		this.filmeService.save(filmeDTO);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping(value = "/filmes/{idFilme}/avaliacao", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Salva uma avaliação pelo ID do filme")
	public ResponseEntity<?> salvarAvaliacao(@PathVariable("idFilme") Integer idFilme,
			@RequestBody @Valid AvaliacaoDTO avaliacaoDTO, BindingResult result) {
		if (result.hasErrors()) {
			final List<String> errors = result.getAllErrors().stream().map(ObjectError::getDefaultMessage)
					.collect(Collectors.toList());
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errors);
		}
		Boolean isPresent = this.avaliacaoService.isAvaliacaoSalva(avaliacaoDTO.getUsuario(),
				idFilme);
		if (isPresent) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		this.filmeService.adicionarAvaliacao(idFilme, avaliacaoDTO);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping(value = "/filmes/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Exclui um filme pelo ID")
	public ResponseEntity<?> deletar(@PathVariable("id") Integer id) {
		this.filmeService.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PutMapping(value = "/filmes/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Atualiza um filme pelo ID")
	public ResponseEntity<?> update(@RequestBody FilmeInputDTO filmeDTO, BindingResult result) {
		if (result.hasErrors()) {
			final List<String> errors = result.getAllErrors().stream().map(ObjectError::getDefaultMessage)
					.collect(Collectors.toList());
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errors);
		}
		this.filmeService.update(filmeDTO);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(value = "/filme/indicacao/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Retorna uma indicação de um filme que o usuário ainda não avaliou e que tenha sido avaliado por outro usuário")
	public ResponseEntity<?> indicarFilme(@PathVariable("email") String email) {
		FilmeOutputDTO filme = this.filmeService.getIndicacaoFilme(email);
		return new ResponseEntity<FilmeOutputDTO>(filme, HttpStatus.OK);
	}
}
