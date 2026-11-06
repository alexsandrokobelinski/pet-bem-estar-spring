package br.edu.ifrs.petbemestar.web;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.edu.ifrs.petbemestar.dao.AnimalDAO;
import br.edu.ifrs.petbemestar.dao.AtendimentoDAO;
import br.edu.ifrs.petbemestar.dao.TutorDAO;
import br.edu.ifrs.petbemestar.dominio.Animal;
import br.edu.ifrs.petbemestar.dominio.Tutor;
import jakarta.validation.Valid;

@RestController
public class AnimalController {

	private final AnimalDAO dao;
	private final TutorDAO tutorDAO;
	private final AtendimentoDAO atendimentoDAO;

	public AnimalController(AnimalDAO dao, TutorDAO tutorDAO, AtendimentoDAO atendimentoDAO) {

		this.dao = dao;
		this.tutorDAO = tutorDAO;
		this.atendimentoDAO = atendimentoDAO;
	}

	@GetMapping("/animais")
	public List<Animal> listar() {
		return dao.listarTodos();
	}

	@GetMapping("/animais/{id}")
	public Animal buscarPorId(@PathVariable Long id) {

		Animal animal = dao.buscarPorId(id);

		if (animal == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Animal não encontrado.");
		}

		return animal;
	}

	@PostMapping("/animais")
	@ResponseStatus(HttpStatus.CREATED)
	public Animal cadastrar(@Valid @RequestBody Animal animal) {

		Tutor tutor = validarTutor(animal);

		animal.setTutor(tutor);
		dao.salvar(animal);

		return animal;
	}

	@PutMapping("/animais/{id}")
	public Animal atualizar(@PathVariable Long id, @Valid @RequestBody Animal dados) {

		Animal existente = dao.buscarPorId(id);

		if (existente == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Animal não encontrado.");
		}

		Tutor tutor = validarTutor(dados);

		dados.setTutor(tutor);

		return dao.atualizar(id, dados);
	}

	@DeleteMapping("/animais/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {

		Animal animal = dao.buscarPorId(id);

		if (animal == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Animal não encontrado.");
		}

		if (!atendimentoDAO.listarPorAnimal(id).isEmpty()) {
			throw new ResponseStatusException(HttpStatus.CONFLICT,
					"Não é possível excluir o animal, pois existem atendimentos vinculados.");
		}

		dao.remover(id);
	}

	private Tutor validarTutor(Animal animal) {

		if (animal.getTutor() == null || animal.getTutor().getId() == null) {

			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Informe o tutor do animal.");
		}

		Tutor tutor = tutorDAO.buscarPorId(animal.getTutor().getId());

		if (tutor == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O tutor informado não existe.");
		}

		return tutor;
	}
}