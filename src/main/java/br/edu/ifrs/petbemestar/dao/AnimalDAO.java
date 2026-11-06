package br.edu.ifrs.petbemestar.dao;

import java.util.List;
import br.edu.ifrs.petbemestar.dominio.Animal;

public interface AnimalDAO {
	void salvar(Animal animal);

	Animal buscarPorId(Long id);

	List<Animal> listarTodos();

	Animal atualizar(Long id, Animal animal);

	void remover(Long id);
}
