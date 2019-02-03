package br.com.projetofilmes.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import br.com.projetofilmes.domain.DominioInvalidoException;
import br.com.projetofilmes.dto.UsuarioDTO;

@RunWith(value = SpringRunner.class)
@SpringBootTest
@Transactional
public class UsuarioServiceTest {

	@Autowired
	private UsuarioService usuarioService;

	@Test
	public void deveValidarCriacaoEmailValido() {
		UsuarioDTO usuarioDTO = new UsuarioDTO();
		usuarioDTO.setEmail("bruna@gmail.com");

		usuarioService.save(usuarioDTO);
		Assert.assertEquals("bruna@gmail.com", usuarioDTO.getEmail());
	}

	@Test(expected = DominioInvalidoException.class)
	public void deveValidarCriacaoEmailInvalido() {
		UsuarioDTO usuarioDTO = new UsuarioDTO();
		usuarioDTO.setEmail("brunagmail.com");

		usuarioService.save(usuarioDTO);

	}
}
