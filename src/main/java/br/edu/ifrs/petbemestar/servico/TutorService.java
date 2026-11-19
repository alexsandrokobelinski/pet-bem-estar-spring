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
		validarTelefone(tutor, null);
		return dao.salvar(tutor);
	}

	public Tutor atualizar(Long id, Tutor tutor) {
		if (dao.buscarPorId(id) == null) {
			return null;
		}

		validarTelefone(tutor, id);
		return dao.atualizar(id, tutor);
	}

	private void validarTelefone(Tutor tutor, Long idTutor) {
		String telefone = tutor.getTelefone().trim();
		tutor.setTelefone(telefone);

		boolean telefoneEmUso = idTutor == null ? dao.existeTelefone(telefone)
				: dao.existeTelefoneParaOutroTutor(telefone, idTutor);

		if (telefoneEmUso) {
			throw new RegraDeNegocioException(
					"Este telefone já está cadastrado para outro cliente. Informe um telefone diferente.");
		}
	}

	public void remover(Long id) {
		if (dao.buscarPorId(id) == null) {
			throw new RegraDeNegocioException("Tutor não encontrado.");
		}

		if (dao.possuiAnimais(id)) {
			throw new RegraDeNegocioException(
					"Não é possível excluir este cliente porque existem animais cadastrados em seu nome.");
		}

		dao.remover(id);
	}
}