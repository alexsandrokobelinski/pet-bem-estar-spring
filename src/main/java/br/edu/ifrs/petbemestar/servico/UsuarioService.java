package br.edu.ifrs.petbemestar.servico;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.edu.ifrs.petbemestar.dao.UsuarioDAO;
import br.edu.ifrs.petbemestar.dominio.Usuario;

@Service
public class UsuarioService {

	private final UsuarioDAO dao;
	private final BCryptPasswordEncoder passwordEncoder;

	public UsuarioService(UsuarioDAO dao) {
		this.dao = dao;
		this.passwordEncoder = new BCryptPasswordEncoder();
	}

	public Usuario cadastrar(Usuario usuario) {
		String email = usuario.getEmail().trim().toLowerCase();

		if (dao.existeEmail(email)) {
			throw new RegraDeNegocioException("Este e-mail já está cadastrado.");
		}

		if (usuario.getSenha() == null || usuario.getSenha().length() < 6) {
			throw new RegraDeNegocioException("A senha deve possuir pelo menos 6 caracteres.");
		}

		usuario.setEmail(email);
		usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));

		return dao.salvar(usuario);
	}

	public Usuario autenticar(String email, String senha) {
		if (email == null || senha == null) {
			return null;
		}

		Usuario usuario = dao.buscarPorEmail(email.trim().toLowerCase());

		if (usuario != null && passwordEncoder.matches(senha, usuario.getSenha())) {
			return usuario;
		}

		return null;
	}
}