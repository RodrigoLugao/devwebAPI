package rodrigolugao.simasAPI.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import rodrigolugao.simasAPI.entities.Peca;
import rodrigolugao.simasAPI.models.PageModel;
import rodrigolugao.simasAPI.services.PecaService;
import rodrigolugao.simasAPI.utils.PageUtil;

import java.util.List;

@RestController
@RequestMapping(value = "pecas")
public class PecaController {

    @Autowired
    private PecaService pecaService;

    // GET /pecas
    @GetMapping
    public List<Peca> findAll() {
        return pecaService.findAll();
    }

    // GET /pecas/{id}
    @GetMapping(value = "/{id}")
    public Peca findById(@PathVariable Long id) {
        return pecaService.findById(id);
    }

    // POST /pecas
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Peca create(@RequestBody @Valid Peca peca) {
        return pecaService.create(peca);
    }

    // PUT /pecas/{id}
    @PutMapping(value = "/{id}")
    public Peca update(@PathVariable Long id, @RequestBody @Valid Peca peca) {
        return pecaService.update(id, peca);
    }

    // DELETE /pecas/{id}
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable Long id) {
        pecaService.delete(id);
    }

    /**
     * Retorna uma página de Peças pelo slug da categoria.
     * Ex: GET /pecas/filtrar?slugCategoria=motor-e-transmissao&pagina=0&itensPorPagina=5
     *
     * @param slug Uma lista de IDs de peças.
     * @return PageModel de entidades Peca.
     */
    @GetMapping("/filtrar")
    public PageModel<Peca> getPecasPorSlugCategoriaComPaginacao(
            @RequestParam(required = false) String slug,
            @PageableDefault(page = 0, size = 10)
            Pageable pageable) {

        return PageUtil.toPageModel(pecaService.findPecasByCategoriaSlugWithPagination(slug,pageable));
    }

    /**
     *
     * @param usuarioId O ID do usuário.
     * @return Um PageModel de entidades Peca completas.
     */
    @GetMapping("/favoritas/usuario/{usuarioId}")
    public PageModel<Peca> getFavoritePecasByUsuarioIdPaginado(
            @PathVariable Long usuarioId,
            @PageableDefault(page = 0, size = 10)
            Pageable pageable) {

        Page<Peca> pecasPage = pecaService.findFavoritePecasByUsuarioIdPaginado(usuarioId, pageable);

        return PageUtil.toPageModel(pecasPage);
    }

    /**
     * Retorna uma lista de Peças baseadas em uma lista de IDs.
     * Ex: GET /pecas/ids?ids=1&ids=2&ids=3  OU  /pecas/ids?ids=1,2,3
     *
     * @param ids Uma lista de IDs de peças.
     * @return Uma página de entidades Peca.
     */
    @GetMapping("/ids")
    public PageModel<Peca> getPecasByIds(@RequestParam List<Long> ids,
                                    @PageableDefault(page = 0, size = 10)
                                    Pageable pageable) {
        Page<Peca> pecasPage = pecaService.findPecasByIds(ids, pageable);

        return PageUtil.toPageModel(pecasPage);
    }
}