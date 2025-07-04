package rodrigolugao.simasAPI.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import rodrigolugao.simasAPI.entities.CategoriaPeca;
import rodrigolugao.simasAPI.exceptions.CategoriaPecaNaoEncontradaException;
import rodrigolugao.simasAPI.services.CategoriaPecaService;

import java.util.List;

@RestController
@RequestMapping(value = "categorias-peca")
public class CategoriaPecaController {

    @Autowired
    private CategoriaPecaService categoriaPecaService;

    @GetMapping
    public List<CategoriaPeca> findAll() {
        return categoriaPecaService.findAll();
    }

    @GetMapping(value = "/{id}")
    public CategoriaPeca findById(@PathVariable Long id) {
        return categoriaPecaService.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CategoriaPeca create(@RequestBody CategoriaPeca categoriaPeca) {
        return categoriaPecaService.create(categoriaPeca);
    }

    @PutMapping(value = "/{id}")
    public CategoriaPeca update(@PathVariable Long id, @RequestBody CategoriaPeca categoriaPeca) {
        return categoriaPecaService.update(id, categoriaPeca);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable Long id) {
        categoriaPecaService.delete(id);
    }
}