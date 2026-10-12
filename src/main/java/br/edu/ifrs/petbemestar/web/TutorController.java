package br.edu.ifrs.petbemestar.web;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PutMapping;

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

	@PostMapping("/tutores")
	@ResponseStatus(HttpStatus.CREATED)
	public Tutor cadastrar(@RequestBody Tutor tutor) {
		return dao.salvar(tutor);
	}

	@PutMapping("/tutores/{id}")
	public Tutor atualizar(@PathVariable Long id, @RequestBody Tutor dados) {
		return dao.atualizar(id, dados);
	}

}
