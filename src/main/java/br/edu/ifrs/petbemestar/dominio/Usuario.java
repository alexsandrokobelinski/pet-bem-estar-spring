package br.edu.ifrs.petbemestar.dominio;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "O nome é obrigatório.")
	@Size(max = 120, message = "O nome deve ter no máximo 120 caracteres.")
	private String nome;

	@NotBlank(message = "O e-mail é obrigatório.")
	@Email(message = "Informe um endereço de e-mail válido.")
	@Size(max = 150, message = "O e-mail deve ter no máximo 150 caracteres.")
	@Column(nullable = false, unique = true, length = 150)
	private String email;

	@JsonIgnore
	@NotBlank(message = "A senha é obrigatória.")
	private String senha;

	public Usuario() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
}
