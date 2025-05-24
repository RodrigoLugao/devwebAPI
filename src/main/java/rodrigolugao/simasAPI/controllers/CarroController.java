package rodrigolugao.simasAPI.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.springframework.web.bind.annotation.*;
import rodrigolugao.simasAPI.entities.Carro;
import rodrigolugao.simasAPI.models.PageModel;
import rodrigolugao.simasAPI.services.CarroService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("carros")
public class CarroController {

    @Autowired
    private CarroService carroService;

    @GetMapping("")
    public PageModel<Carro> getCarros(
            @RequestParam(value = "pagina", defaultValue = "0") int pagina,
            @RequestParam(value = "tamanho", defaultValue = "5") int tamanho){
        return pageToPageModel(carroService.getAllCarrosWithModelo(PageRequest.of(pagina, tamanho)));
    }

    @GetMapping("{id}")
    public Carro getCarro(@PathVariable("id") Long id){
        return carroService.getCarById(id);
    }

    @GetMapping("modelo/{idModelo}")
    public PageModel<Carro> getCarrosByModelo(
            @RequestParam(value = "pagina", defaultValue = "0") int pagina,
            @RequestParam(value = "tamanho", defaultValue = "5") int tamanho,
            @PathVariable("idModelo") long idModelo){
        Page<Carro> page = carroService.getCarroByModelo(PageRequest.of(pagina, tamanho), idModelo);
        return pageToPageModel(page);
    }

    @PostMapping
    public Carro postCarro(@RequestBody Carro carro) {
        return carroService.createCarro(carro);
    }

    @DeleteMapping("{id}")
    public void deleteCarro(@PathVariable long id) {
        carroService.removeCarro(id);
    }

    @PutMapping
    public Carro changeCarro(@RequestBody Carro carro){
        return carroService.updateCarro(carro);
    }

    private PageModel<Carro> pageToPageModel(Page<Carro> page){
        log.debug(page.getContent().toString());
        return new PageModel<Carro>(page.getTotalElements(), page.getTotalPages(), page.getNumber(), page.getContent());
    }

}
