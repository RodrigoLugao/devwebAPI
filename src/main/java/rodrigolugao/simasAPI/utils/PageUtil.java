package rodrigolugao.simasAPI.utils;

import org.springframework.data.domain.Page;
import rodrigolugao.simasAPI.models.PageModel;

/**
 * Classe utilitária para conversão de objetos Page do Spring Data em PageModel.
 */
public class PageUtil {

    /**
     * Converte um objeto Page do Spring Data em um PageModel personalizado.
     * Este método é genérico e pode ser usado para qualquer tipo de dado (T).
     *
     * @param page O objeto Page<T> retornado pelo Spring Data JPA.
     * @param <T> O tipo dos itens na página.
     * @return Um PageModel<T> contendo os dados da página convertidos.
     */
    public static <T> PageModel<T> toPageModel(Page<T> page) {
        return new PageModel<>(
                page.getTotalElements(),
                page.getTotalPages(),
                page.getNumber(),
                page.getContent()
        );
    }
}