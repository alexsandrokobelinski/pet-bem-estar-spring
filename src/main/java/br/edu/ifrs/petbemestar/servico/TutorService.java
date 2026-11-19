package br.edu.ifrs.petbemestar.servico;

import java.util.List;

import org.springframework.stereotype.Service;

import br.edu.ifrs.petbemestar.dao.TutorDAO;
import br.edu.ifrs.petbemestar.dominio.Tutor;

@Service
public class TutorService {

	private final TutorDAO dao;

	public TutorService(TutorDAO dao) {
		this.dao = dao;
	}

	public List<Tutor> listarTodos() {
		return dao.listarTodos();
	}

	public Tutor buscarPorId(Long id) {
		return dao.buscarPorId(id);
	}

	public Tutor salvar(Tutor tutor) {
		return dao.salvar(tutor);
	}

	public Tutor atualizar(Long id, Tutor tutor) {
		return dao.atualizar(id, tutor);
	}

	public void remover(Long id) {
		dao.remover(id);
	}
}
