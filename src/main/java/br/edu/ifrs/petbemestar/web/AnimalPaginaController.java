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

import br.edu.ifrs.petbemestar.dao.AnimalDAO;
import br.edu.ifrs.petbemestar.dao.AtendimentoDAO;
import br.edu.ifrs.petbemestar.dao.TutorDAO;
import br.edu.ifrs.petbemestar.dominio.Animal;
import br.edu.ifrs.petbemestar.dominio.Especie;
import br.edu.ifrs.petbemestar.dominio.Porte;
import br.edu.ifrs.petbemestar.dominio.Tutor;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/paginas/animais")
public class AnimalPaginaController {

	private final AnimalDAO animalDAO;
	private final TutorDAO tutorDAO;
	private final AtendimentoDAO atendimentoDAO;

	public AnimalPaginaController(AnimalDAO animalDAO, TutorDAO tutorDAO, AtendimentoDAO atendimentoDAO) {

		this.animalDAO = animalDAO;
		this.tutorDAO = tutorDAO;
		this.atendimentoDAO = atendimentoDAO;
	}

	private void carregarOpcoes(Model model) {
		model.addAttribute("tutores", tutorDAO.listarTodos());
		model.addAttribute("especies", Especie.values());
		model.addAttribute("portes", Porte.values());
	}

	/**
	 * Verifica se o tutor informado existe no banco de dados.
	 */
	private boolean validarTutor(Animal animal, BindingResult resultado) {

		if (animal.getTutor() == null || animal.getTutor().getId() == null) {
			resultado.rejectValue("tutor", "tutor.obrigatorio", "Selecione um tutor.");
			return false;
		}

		Tutor tutor = tutorDAO.buscarPorId(animal.getTutor().getId());

		if (tutor == null) {
			resultado.rejectValue("tutor", "tutor.invalido", "O tutor selecionado não foi encontrado.");
			return false;
		}

		animal.setTutor(tutor);
		return true;
	}

	@GetMapping
	public String listar(Model model) {
		model.addAttribute("animais", animalDAO.listarTodos());
		return "animais/lista";
	}

	@GetMapping("/novo")
	public String novo(Model model) {
		model.addAttribute("animal", new Animal());
		carregarOpcoes(model);
		return "animais/formulario";
	}

	@PostMapping("/novo")
	public String cadastrar(@Valid @ModelAttribute("animal") Animal animal, BindingResult resultado, Model model) {

		if (!resultado.hasErrors()) {
			validarTutor(animal, resultado);
		}

		if (resultado.hasErrors()) {
			carregarOpcoes(model);
			return "animais/formulario";
		}

		animalDAO.salvar(animal);

		return "redirect:/paginas/animais";
	}

	@GetMapping("/editar/{id}")
	public String editar(@PathVariable Long id, Model model) {

		Animal animal = animalDAO.buscarPorId(id);

		if (animal == null) {
			return "redirect:/paginas/animais";
		}

		model.addAttribute("animal", animal);
		carregarOpcoes(model);

		return "animais/formulario";
	}

	@PostMapping("/editar/{id}")
	public String atualizar(@PathVariable Long id, @Valid @ModelAttribute("animal") Animal animal,
			BindingResult resultado, Model model) {

		Animal existente = animalDAO.buscarPorId(id);

		if (existente == null) {
			return "redirect:/paginas/animais";
		}

		if (!resultado.hasErrors()) {
			validarTutor(animal, resultado);
		}

		if (resultado.hasErrors()) {
			carregarOpcoes(model);
			return "animais/formulario";
		}

		// O ID da URL prevalece sobre o ID enviado pelo formulário.
		animal.setId(id);

		animalDAO.atualizar(id, animal);

		return "redirect:/paginas/animais";
	}

	/**
	 * Exclui um animal somente quando não possui atendimentos.
	 */
	@PostMapping("/excluir/{id}")
	public String remover(@PathVariable Long id, RedirectAttributes atributos) {

		Animal animal = animalDAO.buscarPorId(id);

		if (animal == null) {
			atributos.addFlashAttribute("erro", "Animal não encontrado.");
			return "redirect:/paginas/animais";
		}

		if (!atendimentoDAO.listarPorAnimal(id).isEmpty()) {
			atributos.addFlashAttribute("erro",
					"Não é possível excluir este animal, pois existem atendimentos vinculados.");
			return "redirect:/paginas/animais";
		}

		try {
			animalDAO.remover(id);

			atributos.addFlashAttribute("mensagem", "Animal excluído com sucesso.");

		} catch (Exception e) {
			atributos.addFlashAttribute("erro",
					"Não foi possível excluir o animal. Verifique os registros vinculados.");
		}

		return "redirect:/paginas/animais";
	}
}