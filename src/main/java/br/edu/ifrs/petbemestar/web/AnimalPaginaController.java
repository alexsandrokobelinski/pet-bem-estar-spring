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

import br.edu.ifrs.petbemestar.dominio.Animal;
import br.edu.ifrs.petbemestar.dominio.Especie;
import br.edu.ifrs.petbemestar.dominio.Porte;
import br.edu.ifrs.petbemestar.servico.AnimalService;
import br.edu.ifrs.petbemestar.servico.RegraDeNegocioException;
import br.edu.ifrs.petbemestar.servico.TutorService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/paginas/animais")
public class AnimalPaginaController {

    private final AnimalService animalService;
    private final TutorService tutorService;

    public AnimalPaginaController(
            AnimalService animalService,
            TutorService tutorService) {

        this.animalService = animalService;
        this.tutorService = tutorService;
    }

    private void carregarOpcoes(Model model) {
        model.addAttribute("tutores", tutorService.listarTodos());
        model.addAttribute("especies", Especie.values());
        model.addAttribute("portes", Porte.values());
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("animais", animalService.listarTodos());
        return "animais/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("animal", new Animal());
        carregarOpcoes(model);
        return "animais/formulario";
    }

    @PostMapping("/novo")
    public String cadastrar(
            @Valid @ModelAttribute("animal") Animal animal,
            BindingResult resultado,
            Model model) {

        if (resultado.hasErrors()) {
            carregarOpcoes(model);
            return "animais/formulario";
        }

        try {
            animalService.salvar(animal);
            return "redirect:/paginas/animais";
        } catch (RegraDeNegocioException e) {
            resultado.rejectValue("tutor", "tutor.invalido", e.getMessage());
            carregarOpcoes(model);
            return "animais/formulario";
        }
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Animal animal = animalService.buscarPorId(id);

        if (animal == null) {
            return "redirect:/paginas/animais";
        }

        model.addAttribute("animal", animal);
        carregarOpcoes(model);
        return "animais/formulario";
    }

    @PostMapping("/editar/{id}")
    public String atualizar(
            @PathVariable Long id,
            @Valid @ModelAttribute("animal") Animal animal,
            BindingResult resultado,
            Model model) {

        if (resultado.hasErrors()) {
            carregarOpcoes(model);
            return "animais/formulario";
        }

        animal.setId(id);

        try {
            Animal atualizado = animalService.atualizar(id, animal);

            if (atualizado == null) {
                return "redirect:/paginas/animais";
            }

            return "redirect:/paginas/animais";
        } catch (RegraDeNegocioException e) {
            resultado.rejectValue("tutor", "tutor.invalido", e.getMessage());
            carregarOpcoes(model);
            return "animais/formulario";
        }
    }

    @PostMapping("/excluir/{id}")
    public String remover(
            @PathVariable Long id,
            RedirectAttributes atributos) {

        try {
            if (animalService.remover(id)) {
                atributos.addFlashAttribute("mensagem", "Animal excluído com sucesso.");
            } else {
                atributos.addFlashAttribute("erro", "Animal não encontrado.");
            }
        } catch (RegraDeNegocioException e) {
            atributos.addFlashAttribute("erro", e.getMessage());
        } catch (Exception e) {
            atributos.addFlashAttribute(
                    "erro",
                    "Não foi possível excluir o animal. Verifique os registros vinculados.");
        }

        return "redirect:/paginas/animais";
    }
}