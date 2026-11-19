package br.edu.ifrs.petbemestar.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifrs.petbemestar.dominio.Tutor;
import br.edu.ifrs.petbemestar.servico.AnimalService;
import br.edu.ifrs.petbemestar.servico.RegraDeNegocioException;
import br.edu.ifrs.petbemestar.servico.TutorService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/paginas/tutores")
public class TutorPaginaController {

	private final TutorService service;
	private final AnimalService animalService;

	public TutorPaginaController(TutorService service, AnimalService animalService) {
		this.service = service;
		this.animalService = animalService;
	}

	@GetMapping
	public String listar(Model model) {
		model.addAttribute("tutores", service.listarTodos());
		return "tutores/lista";
	}

	@GetMapping("/detalhes/{id}")
	public String detalhes(@PathVariable Long id, Model model) {
		Tutor tutor = service.buscarPorId(id);

		if (tutor == null) {
			return "redirect:/paginas/tutores";
		}

		model.addAttribute("tutor", tutor);
		model.addAttribute("animais", animalService.listarPorTutor(id));
		return "tutores/detalhes";
	}

	@GetMapping("/novo")
	public String novo(Model model) {
		model.addAttribute("tutor", new Tutor());
		return "tutores/formulario";
	}

	@PostMapping("/novo")
	public String cadastrar(@Valid @ModelAttribute("tutor") Tutor tutor, BindingResult resultado, Model model) {
		if (resultado.hasErrors()) {
			return "tutores/formulario";
		}

		try {
			service.salvar(tutor);
			return "redirect:/paginas/tutores";
		} catch (RegraDeNegocioException e) {
			model.addAttribute("erro", e.getMessage());
			return "tutores/formulario";
		}
	}

	@GetMapping("/editar/{id}")
	public String editar(@PathVariable Long id, Model model) {
		Tutor tutor = service.buscarPorId(id);

		if (tutor == null) {
			return "redirect:/paginas/tutores";
		}

		model.addAttribute("tutor", tutor);
		return "tutores/formulario";
	}

	@PostMapping("/editar/{id}")
	public String atualizar(@PathVariable Long id, @Valid @ModelAttribute("tutor") Tutor tutor, BindingResult resultado,
			Model model) {
		if (resultado.hasErrors()) {
			return "tutores/formulario";
		}

		try {
			if (service.atualizar(id, tutor) == null) {
				return "redirect:/paginas/tutores";
			}
			return "redirect:/paginas/tutores";
		} catch (RegraDeNegocioException e) {
			model.addAttribute("erro", e.getMessage());
			return "tutores/formulario";
		}
	}

	@PostMapping("/excluir/{id}")
	public String remover(@PathVariable Long id, RedirectAttributes atributos) {
		try {
			service.remover(id);
			atributos.addFlashAttribute("mensagem", "Tutor removido com sucesso.");
		} catch (RegraDeNegocioException e) {
			atributos.addFlashAttribute("erro", e.getMessage());
		}

		return "redirect:/paginas/tutores";
	}
}