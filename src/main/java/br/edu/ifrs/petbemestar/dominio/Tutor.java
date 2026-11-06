package br.edu.ifrs.petbemestar.dominio;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Tutor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "O nome é obrigatório.")
	private String nome;

	@NotBlank(message = "O telefone é obrigatório.")
	private String telefone;

	private String email;

	// Não há cascade: a exclusão de um tutor com animais deve ser impedida
	// pela integridade referencial do banco de dados.
	@OneToMany(mappedBy = "tutor")
	private List<Animal> animais = new ArrayList<>();

	public Tutor() {
	}

	public Tutor(String nome, String telefone) {
		this.nome = nome;
		this.telefone = telefone;
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

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Animal> getAnimais() {
		return animais;
	}

	public void adicionarAnimal(Animal animal) {
		animais.add(animal);
		animal.setTutor(this);
	}
}
