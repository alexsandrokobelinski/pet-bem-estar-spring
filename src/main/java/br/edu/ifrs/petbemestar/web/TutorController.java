package br.edu.ifrs.petbemestar.web;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifrs.petbemestar.dao.TutorDAO;
import br.edu.ifrs.petbemestar.dominio.Tutor;

@RestController
public class TutorController {

	private final TutorDAO dao;

	public TutorController(TutorDAO dao) {
		this.dao = dao;
	}

	@GetMapping("/tutores")
	public List<Tutor> listar() {
		return dao.listarTodos();
	}

	@GetMapping("/tutores/{id}")
	public Tutor buscarPorId(@org.springframework.web.bind.annotation.PathVariable Long id) {
		return dao.buscarPorId(id);
	}

}
