package rodrigolugao.simasAPI.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import rodrigolugao.simasAPI.entities.Veiculo;
import rodrigolugao.simasAPI.enums.TipoVeiculo;
import rodrigolugao.simasAPI.models.PageModel;
import rodrigolugao.simasAPI.services.VeiculoService;
import rodrigolugao.simasAPI.utils.PageUtil;

@Slf4j
@RestController
@RequestMapping("veiculos")
public class VeiculoController {

    @Autowired
    private VeiculoService veiculoService;

    @GetMapping
    public PageModel<Veiculo> getVeiculos(
            Pageable pageable,
            @RequestParam(required = false) String nomeModelo,
            @RequestParam(required = false) String fabricante,
            @RequestParam(required = false) TipoVeiculo tipo,
            @RequestParam(required = false) Integer anoMin,
            @RequestParam(required = false) Integer anoMax,
            @RequestParam(required = false) Double precoMin,
            @RequestParam(required = false) Double precoMax,
            @RequestParam(required = false) Integer kmsMin,
            @RequestParam(required = false) Integer kmsMax,
            @RequestParam(required = false) String cambio,
            @RequestParam(required = false) String cor
    ) {
        Page<Veiculo> veiculos = veiculoService.buscarVeiculosComFiltro(
                nomeModelo, fabricante, tipo,
                anoMin, anoMax, precoMin, precoMax,
                kmsMin, kmsMax, cambio, cor,
                pageable
        );
        return PageUtil.toPageModel(veiculoService.buscarVeiculosComFiltro(nomeModelo, fabricante, tipo,
                anoMin, anoMax, precoMin, precoMax,
                kmsMin, kmsMax, cambio, cor,
                pageable));
    }

    @GetMapping("{id}")
    public Veiculo getVeiculo(@PathVariable("id") Long id){
        return veiculoService.getVeiculoById(id);
    }

    @GetMapping("codigo/{codigo}")
    public Veiculo getVeiculoPorCodigo(@PathVariable("codigo") String codigo){
        return veiculoService.getVeiculoByCodigo(codigo);
    }

    @GetMapping("modelo/{idModelo}")
    public PageModel<Veiculo> getVeiculosByModelo(
            @RequestParam(value = "pagina", defaultValue = "0") int pagina,
            @RequestParam(value = "tamanho", defaultValue = "5") int tamanho,
            @PathVariable("idModelo") long idModelo){
        Page<Veiculo> page = veiculoService.getVeiculosByModelo(PageRequest.of(pagina, tamanho), idModelo);
        return PageUtil.toPageModel(page);
    }

    @GetMapping("modelo/codigo/{codigoModelo}")
    public PageModel<Veiculo> getVeiculosByCodigo(
            @RequestParam(value = "pagina", defaultValue = "0") int pagina,
            @RequestParam(value = "tamanho", defaultValue = "5") int tamanho,
            @PathVariable("codigoModelo") String codigoModelo){
        Page<Veiculo> page = veiculoService.getVeiculosByCodigoWithPagination(PageRequest.of(pagina, tamanho), codigoModelo);
        return PageUtil.toPageModel(page);
    }

    @GetMapping("modelo/nome/{nomeModelo}")
    public PageModel<Veiculo> getVeiculosByModeloNome(
            @RequestParam(value = "pagina", defaultValue = "0") int pagina,
            @RequestParam(value = "tamanho", defaultValue = "5") int tamanho,
            @PathVariable("nomeModelo") String nomeModelo){
        Page<Veiculo> page = veiculoService.getVeiculosByModeloNome(PageRequest.of(pagina, tamanho), nomeModelo);
        return PageUtil.toPageModel(page);
    }

    @PostMapping
    public Veiculo postCarro(@RequestBody Veiculo veiculo) {
        return veiculoService.createVeiculo(veiculo);
    }

    @DeleteMapping("{id}")
    public void deleteCarro(@PathVariable long id) {
        veiculoService.removeVeiculo(id);
    }

    @PutMapping
    public Veiculo changeVeiculo(@RequestBody Veiculo veiculo){
        return veiculoService.updateVeiculo(veiculo);
    }

}
