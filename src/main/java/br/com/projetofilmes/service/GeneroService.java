package br.com.projetofilmes.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.projetofilmes.domain.Genero;
import br.com.projetofilmes.dto.GeneroDTO;
import br.com.projetofilmes.repository.GeneroRepository;

@Service
@Transactional
public class GeneroService {

	private GeneroRepository generoRepository;

	@Autowired
	public GeneroService(GeneroRepository generoRepository) {
		this.generoRepository = generoRepository;
	}

	public void save(GeneroDTO generoDTO) {
		String nomeGenero = generoDTO.getNome();

		Genero genero = new Genero(nomeGenero);
		this.generoRepository.saveAndFlush(genero);
	}

	public List<GeneroDTO> findAll() {
		List<GeneroDTO> todosGeneros = new ArrayList<GeneroDTO>();
		List<Genero> generos = generoRepository.findAll();

		for (Genero genero : generos) {
			GeneroDTO generoDTO = criarGeneroDTO(genero);
			todosGeneros.add(generoDTO);
		}
		return todosGeneros;
	}

	private GeneroDTO criarGeneroDTO(Genero genero) {
		GeneroDTO generoDTO = new GeneroDTO();
		generoDTO.setNome(genero.getNome());
		return generoDTO;
	}

}
