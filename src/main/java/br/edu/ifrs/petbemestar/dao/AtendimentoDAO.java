package br.edu.ifrs.petbemestar.dao;

import java.util.List;
import br.edu.ifrs.petbemestar.dominio.Atendimento;
import br.edu.ifrs.petbemestar.dominio.SituacaoAgendamento;

public interface AtendimentoDAO {
	void salvar(Atendimento atendimento);

	Atendimento buscarPorId(Long id);

	List<Atendimento> listarTodos();

	Atendimento atualizar(Long id, Atendimento atendimento);

	void remover(Long id);

	List<Atendimento> listarPorAnimal(Long idAnimal);

	List<Atendimento> listarPorSituacao(SituacaoAgendamento situacao);
}
