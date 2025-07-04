package rodrigolugao.simasAPI.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import rodrigolugao.simasAPI.entities.Banner;
import rodrigolugao.simasAPI.models.PageModel;
import rodrigolugao.simasAPI.services.BannerService;
import rodrigolugao.simasAPI.utils.PageUtil;

@Slf4j
@RestController
@RequestMapping("banners")
public class BannerController {

    @Autowired
    private BannerService bannerService;

    /**
     * GET /banners - Recupera todos os banners com paginação e ordenação.
     * Ex: /banners?page=0&size=6&sort=dataCadastro,desc
     *
     * @param pageable Objeto Pageable injetado automaticamente pelo Spring,
     * com defaults de paginação e ordenação definidos por @PageableDefault.
     * @return Uma PageModel de Banners.
     */
    @GetMapping
    public PageModel<Banner> getBanners(
            @PageableDefault(
                    size = 6, // 6 itens por página
                    page = 0, // Começa na página 0
                    sort = "dataCadastro", // Ordenar por 'dataCadastro'
                    direction = Sort.Direction.DESC // Em ordem decrescente
            ) Pageable pageable) {
        log.info("Buscando banners com paginação: {}", pageable);
        Page<Banner> bannersPage = bannerService.getBanners(pageable);
        return PageUtil.toPageModel(bannersPage);
    }

    /**
     * GET /banners/{id} - Recupera um banner por ID.
     *
     * @param id O ID do banner.
     * @return O banner encontrado.
     */
    @GetMapping("{id}")
    public Banner getBanner(@PathVariable("id") Long id) {
        log.info("Buscando banner com ID: {}", id);
        return bannerService.getBannerById(id);
    }

    /**
     * POST /banners - Cria um novo banner.
     *
     * @param banner O objeto Banner a ser criado (recebido no corpo da requisição).
     * @return O banner criado.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Banner createBanner(@RequestBody Banner banner) {
        log.info("Criando novo banner: {}", banner);
        return bannerService.createBanner(banner);
    }

    /**
     * PUT /banners - Atualiza um banner existente.
     *
     * @param banner O objeto Banner a ser atualizado (recebido no corpo da requisição).
     * @return O banner atualizado.
     */
    @PutMapping
    public Banner updateBanner(@RequestBody Banner banner) {
        log.info("Atualizando banner: {}", banner);
        return bannerService.updateBanner(banner);
    }

    /**
     * DELETE /banners/{id} - Exclui um banner por ID.
     *
     * @param id O ID do banner a ser excluído.
     */
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // Retorna 204 No Content para exclusão bem-sucedida
    public void deleteBanner(@PathVariable("id") Long id) {
        log.info("Excluindo banner com ID: {}", id);
        bannerService.deleteBanner(id);
    }
}