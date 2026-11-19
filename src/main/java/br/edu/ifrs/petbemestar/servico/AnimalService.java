package br.edu.ifrs.petbemestar.servico;

import java.util.List;

import org.springframework.stereotype.Service;

import br.edu.ifrs.petbemestar.dao.AnimalDAO;
import br.edu.ifrs.petbemestar.dao.AtendimentoDAO;
import br.edu.ifrs.petbemestar.dao.TutorDAO;
import br.edu.ifrs.petbemestar.dominio.Animal;
import br.edu.ifrs.petbemestar.dominio.Tutor;

@Service
public class AnimalService {

	private final AnimalDAO dao;
	private final TutorDAO tutorDAO;
	private final AtendimentoDAO atendimentoDAO;

	public AnimalService(AnimalDAO dao, TutorDAO tutorDAO, AtendimentoDAO atendimentoDAO) {

		this.dao = dao;
		this.tutorDAO = tutorDAO;
		this.atendimentoDAO = atendimentoDAO;
	}

	public List<Animal> listarTodos() {
		return dao.listarTodos();
	}

	public Animal buscarPorId(Long id) {
		return dao.buscarPorId(id);
	}

	public Animal salvar(Animal animal) {
		animal.setTutor(validarTutor(animal));
		dao.salvar(animal);
		return animal;
	}

	public Animal atualizar(Long id, Animal dados) {
		Animal existente = dao.buscarPorId(id);

		if (existente == null) {
			return null;
		}

		dados.setTutor(validarTutor(dados));
		return dao.atualizar(id, dados);
	}

	public boolean remover(Long id) {
		Animal animal = dao.buscarPorId(id);

		if (animal == null) {
			return false;
		}

		if (!atendimentoDAO.listarPorAnimal(id).isEmpty()) {
			throw new RegraDeNegocioException(
					"Não é possível excluir este animal, pois existem atendimentos vinculados.");
		}

		dao.remover(id);
		return true;
	}

	private Tutor validarTutor(Animal animal) {
		if (animal.getTutor() == null || animal.getTutor().getId() == null) {
			throw new RegraDeNegocioException("Informe o tutor do animal.");
		}

		Tutor tutor = tutorDAO.buscarPorId(animal.getTutor().getId());

		if (tutor == null) {
			throw new RegraDeNegocioException("O tutor informado não existe.");
		}

		return tutor;
	}
}