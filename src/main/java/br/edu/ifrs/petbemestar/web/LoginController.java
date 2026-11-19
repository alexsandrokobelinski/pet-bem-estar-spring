package br.edu.ifrs.petbemestar.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifrs.petbemestar.dominio.Usuario;
import br.edu.ifrs.petbemestar.servico.RegraDeNegocioException;
import br.edu.ifrs.petbemestar.servico.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

    private final UsuarioService usuarioService;

    public LoginController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String autenticar(
            @RequestParam String email,
            @RequestParam String senha,
            HttpServletRequest request,
            Model model) {

        Usuario usuario = usuarioService.autenticar(email, senha);

        if (usuario == null) {
            model.addAttribute("erro", "E-mail ou senha inválidos.");
            model.addAttribute("email", email);
            return "login";
        }

        HttpSession sessaoAnterior = request.getSession(false);
        if (sessaoAnterior != null) {
            sessaoAnterior.invalidate();
        }

        HttpSession sessao = request.getSession(true);
        sessao.setAttribute("usuarioLogado", usuario.getNome());
        sessao.setAttribute("emailUsuario", usuario.getEmail());

        return "redirect:/paginas/tutores";
    }

    @GetMapping("/cadastro")
    public String formularioCadastro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "cadastro";
    }

    @PostMapping("/cadastro")
    public String cadastrar(
            @RequestParam String nome,
            @RequestParam String email,
            @RequestParam String senha,
            @RequestParam String confirmarSenha,
            Model model,
            RedirectAttributes atributos) {

        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setEmail(email);

        if (!senha.equals(confirmarSenha)) {
            model.addAttribute("erro", "A confirmação da senha não corresponde.");
            model.addAttribute("nome", nome);
            model.addAttribute("email", email);
            return "cadastro";
        }

        usuario.setSenha(senha);

        try {
            usuarioService.cadastrar(usuario);
            atributos.addFlashAttribute("mensagem", "Cadastro realizado. Faça login para continuar.");
            return "redirect:/login";
        } catch (RegraDeNegocioException e) {
            model.addAttribute("erro", e.getMessage());
            model.addAttribute("nome", nome);
            model.addAttribute("email", email);
            return "cadastro";
        }
    }

    @GetMapping("/sair")
    public String sair(HttpSession session) {
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/login";
    }
}