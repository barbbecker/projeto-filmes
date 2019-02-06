package br.com.projetofilmes.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "usuario")
public class Usuario extends BaseDomain {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Email
	@NotNull
	@NotEmpty(message = "O email não pode ser vazio!")
	private String email;

	@SuppressWarnings("unused")
	private Usuario() {

	}

	public Usuario(String email) {
		this.email = email;
		validarDomain();
	}

	public Usuario(Integer id, String email) {
		this(email);
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

}
