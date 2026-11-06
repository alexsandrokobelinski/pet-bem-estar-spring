package br.edu.ifrs.petbemestar.dao;

import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import br.edu.ifrs.petbemestar.dominio.Atendimento;
import br.edu.ifrs.petbemestar.dominio.SituacaoAgendamento;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
@Transactional
public class AtendimentoDAOJPA implements AtendimentoDAO {
	@PersistenceContext
	private EntityManager em;

	@Override
	public void salvar(Atendimento atendimento) {
		em.persist(atendimento);
	}

	@Override
	public Atendimento buscarPorId(Long id) {
		return em.find(Atendimento.class, id);
	}

	@Override
	public List<Atendimento> listarTodos() {
		return em.createQuery("SELECT a FROM Atendimento a", Atendimento.class).getResultList();
	}

	@Override
	public Atendimento atualizar(Long id, Atendimento dados) {
		Atendimento existente = em.find(Atendimento.class, id);
		if (existente == null)
			return null;
		existente.setDataHora(dados.getDataHora());
		existente.setTipoServico(dados.getTipoServico());
		existente.setSituacao(dados.getSituacao());
		existente.setAnimal(dados.getAnimal());
		return em.merge(existente);
	}

	@Override
	public void remover(Long id) {
		Atendimento atendimento = em.find(Atendimento.class, id);
		if (atendimento != null)
			em.remove(atendimento);
	}

	@Override
	public List<Atendimento> listarPorAnimal(Long idAnimal) {
		return em.createQuery("SELECT a FROM Atendimento a WHERE a.animal.id = :idAnimal", Atendimento.class)
				.setParameter("idAnimal", idAnimal).getResultList();
	}

	@Override
	public List<Atendimento> listarPorSituacao(SituacaoAgendamento situacao) {
		return em.createQuery("SELECT a FROM Atendimento a WHERE a.situacao = :situacao", Atendimento.class)
				.setParameter("situacao", situacao).getResultList();
	}
}
