package br.com.projetofilmes.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import br.com.projetofilmes.dto.GeneroDTO;

@RunWith(value = SpringRunner.class)
@SpringBootTest
@Transactional
public class GeneroServiceTest {

	@Autowired
	private GeneroService generoService;

	@Test
	public void deveBuscarTodosOsGeneros() {
		GeneroDTO genero1 = new GeneroDTO();
		genero1.setNome("Ação");
		generoService.save(genero1);

		GeneroDTO genero2 = new GeneroDTO();
		genero2.setNome("Suspense");
		generoService.save(genero2);

		GeneroDTO genero3 = new GeneroDTO();
		genero3.setNome("Terror");
		generoService.save(genero3);

		List<GeneroDTO> todosGeneros = generoService.findAll();
		Assert.assertEquals(3, todosGeneros.size());
	}
}
