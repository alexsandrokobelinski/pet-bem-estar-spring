package br.edu.ifrs.petbemestar.web;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.edu.ifrs.petbemestar.dominio.Animal;
import br.edu.ifrs.petbemestar.servico.AnimalService;
import br.edu.ifrs.petbemestar.servico.RegraDeNegocioException;
import jakarta.validation.Valid;

@RestController
public class AnimalController {

    private final AnimalService service;

    public AnimalController(AnimalService service) {
        this.service = service;
    }

    @GetMapping("/animais")
    public List<Animal> listar() {
        return service.listarTodos();
    }

    @GetMapping("/animais/{id}")
    public Animal buscarPorId(@PathVariable Long id) {
        Animal animal = service.buscarPorId(id);

        if (animal == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Animal não encontrado.");
        }

        return animal;
    }

    @PostMapping("/animais")
    @ResponseStatus(HttpStatus.CREATED)
    public Animal cadastrar(@Valid @RequestBody Animal animal) {
        try {
            return service.salvar(animal);
        } catch (RegraDeNegocioException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/animais/{id}")
    public Animal atualizar(
            @PathVariable Long id,
            @Valid @RequestBody Animal dados) {

        try {
            Animal animal = service.atualizar(id, dados);

            if (animal == null) {
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Animal não encontrado.");
            }

            return animal;
        } catch (RegraDeNegocioException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping("/animais/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        try {
            if (!service.remover(id)) {
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Animal não encontrado.");
            }
        } catch (RegraDeNegocioException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }
}