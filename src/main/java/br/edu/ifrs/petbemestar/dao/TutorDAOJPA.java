package br.edu.ifrs.petbemestar.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ifrs.petbemestar.dominio.Tutor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
@Transactional
public class TutorDAOJPA implements TutorDAO {

	@PersistenceContext
	private EntityManager em;

	@Override
	public List<Tutor> listarTodos() {
		return em.createQuery("SELECT t FROM Tutor t", Tutor.class).getResultList();
	}

	@Override
	public Tutor buscarPorId(Long id) {
		return em.find(Tutor.class, id);
	}

	@Override
	public Tutor salvar(Tutor tutor) {
		em.persist(tutor);
		return tutor;
	}

	@Override
	public Tutor atualizar(Long id, Tutor tutor) {
		Tutor existente = em.find(Tutor.class, id);

		if (existente == null) {
			return null;
		}

		existente.setNome(tutor.getNome());
		existente.setTelefone(tutor.getTelefone());
		existente.setEmail(tutor.getEmail());

		return em.merge(existente);
	}

	@Override
	public void remover(Long id) {
		Tutor tutor = em.find(Tutor.class, id);

		if (tutor != null) {
			em.remove(tutor);
		}
	}
}
