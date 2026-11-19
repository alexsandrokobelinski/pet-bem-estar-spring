package br.edu.ifrs.petbemestar.dao;

import br.edu.ifrs.petbemestar.dominio.Usuario;

public interface UsuarioDAO {

	Usuario buscarPorEmail(String email);

	boolean existeEmail(String email);

	Usuario salvar(Usuario usuario);
}
