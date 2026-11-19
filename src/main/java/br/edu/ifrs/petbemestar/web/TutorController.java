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

import br.edu.ifrs.petbemestar.dominio.Tutor;
import br.edu.ifrs.petbemestar.servico.RegraDeNegocioException;
import br.edu.ifrs.petbemestar.servico.TutorService;

@RestController
public class TutorController {

	private final TutorService service;

	public TutorController(TutorService service) {
		this.service = service;
	}

	@GetMapping("/tutores")
	public List<Tutor> listar() {
		return service.listarTodos();
	}

	@GetMapping("/tutores/{id}")
	public Tutor buscarPorId(@PathVariable Long id) {
		Tutor tutor = service.buscarPorId(id);

		if (tutor == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tutor não encontrado.");
		}

		return tutor;
	}

	@PostMapping("/tutores")
	@ResponseStatus(HttpStatus.CREATED)
	public Tutor cadastrar(@RequestBody Tutor tutor) {
		try {
			return service.salvar(tutor);
		} catch (RegraDeNegocioException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
	}

	@PutMapping("/tutores/{id}")
	public Tutor atualizar(@PathVariable Long id, @RequestBody Tutor dados) {
		try {
			Tutor tutor = service.atualizar(id, dados);

			if (tutor == null) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tutor não encontrado.");
			}

			return tutor;
		} catch (RegraDeNegocioException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
	}

	@DeleteMapping("/tutores/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		try {
			service.remover(id);
		} catch (RegraDeNegocioException e) {
			HttpStatus status = "Tutor não encontrado.".equals(e.getMessage()) ? HttpStatus.NOT_FOUND
					: HttpStatus.CONFLICT;
			throw new ResponseStatusException(status, e.getMessage());
		}
	}
}