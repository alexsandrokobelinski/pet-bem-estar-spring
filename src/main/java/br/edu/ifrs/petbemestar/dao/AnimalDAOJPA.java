package br.edu.ifrs.petbemestar.dao;

import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import br.edu.ifrs.petbemestar.dominio.Animal;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
@Transactional
public class AnimalDAOJPA implements AnimalDAO {
	@PersistenceContext
	private EntityManager em;

	@Override
	public void salvar(Animal animal) {
		em.persist(animal);
	}

	@Override
	public Animal buscarPorId(Long id) {
		return em.find(Animal.class, id);
	}

	@Override
	public List<Animal> listarTodos() {
		return em.createQuery("SELECT a FROM Animal a JOIN FETCH a.tutor ORDER BY a.nome", Animal.class)
				.getResultList();
	}

	@Override
	public Animal atualizar(Long id, Animal dados) {
		Animal existente = em.find(Animal.class, id);
		if (existente == null)
			return null;
		existente.setNome(dados.getNome());
		existente.setTutor(dados.getTutor());
		existente.setEspecie(dados.getEspecie());
		existente.setPorte(dados.getPorte());
		return em.merge(existente);
	}

	@Override
	public void remover(Long id) {
		Animal animal = em.find(Animal.class, id);
		if (animal != null)
			em.remove(animal);
	}
}
