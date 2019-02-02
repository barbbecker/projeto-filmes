package br.com.projetofilmes.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.projetofilmes.service.ServiceException;
import br.com.projetofilmes.domain.Filme;
import br.com.projetofilmes.domain.Genero;
import br.com.projetofilmes.dto.FilmeDTO;
import br.com.projetofilmes.repository.FilmeRepository;
import br.com.projetofilmes.repository.GeneroRepository;

@Service
@Transactional
public class FilmeService {

	private FilmeRepository filmeRepository;

	private GeneroRepository generoRepository;

	@Autowired
	public FilmeService(FilmeRepository filmeRepository, GeneroRepository generoRepository) {
		this.filmeRepository = filmeRepository;
		this.generoRepository = generoRepository;
	}

	public void deleteAll() {
		this.filmeRepository.deleteAll();
	}

	public void save(FilmeDTO filmeDTO) {
		Optional<Genero> encontrarGenero = generoRepository.findById(filmeDTO.getGenero().getId());
		String titulo = filmeDTO.getTitulo();
		LocalDate dataLancamento = filmeDTO.getDataLancamento();
		String nomeDiretor = filmeDTO.getNomeDiretor();
		Genero genero = encontrarGenero.get();

		Filme filme = new Filme(titulo, dataLancamento, nomeDiretor, genero);
		this.filmeRepository.saveAndFlush(filme);
	}

	public Filme findById(Integer id) {
		Optional<Filme> filme = filmeRepository.findById(id);
		if (filme.isPresent()) {
			return filme.get();
		}
		throw new ServiceException("Filme não encontrado");
	}

}
