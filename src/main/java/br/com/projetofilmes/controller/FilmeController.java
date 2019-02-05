package br.com.projetofilmes.controller;

import org.springframework.web.bind.annotation.RestController;

import br.com.projetofilmes.domain.Filme;
import br.com.projetofilmes.dto.AvaliacaoDTO;
import br.com.projetofilmes.dto.FilmeInputDTO;
import br.com.projetofilmes.dto.FilmeOutputDTO;
import br.com.projetofilmes.service.FilmeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
@Api(value="filme", description="Operações relacionadas aos filmes")
public class FilmeController {

	private FilmeService filmeService;

	@Autowired
	public FilmeController(FilmeService filmeService) {
		this.filmeService = filmeService;
	}

	@GetMapping(value = "/filmes", produces="application/json")
    @ApiOperation(value = "Retorna uma lista de filmes com suas respectivas avaliações")
	public ResponseEntity<List<FilmeOutputDTO>> obterFilmes() {
		List<FilmeOutputDTO> filme = filmeService.findAll();
		return new ResponseEntity<>(filme, HttpStatus.OK);
	}

	@GetMapping(value = "/filmes/{id}")
    @ApiOperation(value = "Busca um filme pelo ID")
	public ResponseEntity<FilmeOutputDTO> obterFilmePeloId(@PathVariable("id") Integer id) {
		FilmeOutputDTO filme = filmeService.findById(id);
		return new ResponseEntity<FilmeOutputDTO>(filme, HttpStatus.OK);
	}

	@GetMapping(value = "/filmes/{titulo}")
    @ApiOperation(value = "Busca um filme pelo titulo")
	public ResponseEntity<FilmeOutputDTO> obterFilmePeloTitulo(@PathVariable("titulo") String titulo) {
		FilmeOutputDTO filme = filmeService.findByTitulo(titulo);
		return new ResponseEntity<FilmeOutputDTO>(filme, HttpStatus.OK);
	}

	@PostMapping(value = "/filmes")
	public ResponseEntity<?> salvarFilme(@RequestBody @Valid FilmeInputDTO filmeDTO, BindingResult result) {
		if (result.hasErrors()) {
			final List<String> errors = result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.toList());
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errors);
		}
		this.filmeService.save(filmeDTO);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping(value = "/filmes/{id}/avaliacao")
	public ResponseEntity<?> salvarAvaliacao(
			@PathVariable("id") Integer id, 
			@RequestBody @Valid AvaliacaoDTO avaliacaoDTO
		) {
//		AvaliacaoDTO avaliacao = this.avaliacaoService.findByUserEmailAndFilmeId(avaliacaoDTO.getUsuario(), avaliacaoDTO.getIdFilme());
//		
//		if(avaliacao) {
//			return new ResponseEntity<>(HttpStatus.CONFLICT );
//		}
		this.filmeService.adicionarAvaliacao(id, avaliacaoDTO);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping(value = "/filmes/{id}")
	public ResponseEntity<?> deletar(@PathVariable("id") Integer id) {
		this.filmeService.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PutMapping(value = "/filmes/{id}")
	public ResponseEntity<?> update(@RequestBody FilmeInputDTO filmeDTO) {
		this.filmeService.update(filmeDTO);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
