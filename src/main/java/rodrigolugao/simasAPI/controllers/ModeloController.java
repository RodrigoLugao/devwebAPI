package rodrigolugao.simasAPI.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rodrigolugao.simasAPI.entities.Modelo;
import rodrigolugao.simasAPI.services.ModeloService;

import java.util.List;

@RestController
@RequestMapping("modelos")
public class ModeloController {

    @Autowired
    private ModeloService modeloService;

    @GetMapping
    public List<Modelo> getAllModelos(){
        return modeloService.getAllModelos();
    }

    @PostMapping Modelo postModelo(@RequestBody Modelo modelo){
        return modeloService.createModelo(modelo);
    }
}
