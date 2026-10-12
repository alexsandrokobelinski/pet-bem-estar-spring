package br.edu.ifrs.petbemestar.dao;

import java.util.List;
import br.edu.ifrs.petbemestar.dominio.Tutor;

public interface TutorDAO {

    List<Tutor> listarTodos();

    Tutor buscarPorId(Long id);

    Tutor salvar(Tutor tutor);

    Tutor atualizar(Long id, Tutor tutor);

    void remover(Long id);
}
