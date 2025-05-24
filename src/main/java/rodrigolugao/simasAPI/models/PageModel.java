package rodrigolugao.simasAPI.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;


@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public record PageModel<T>(
    long totalItens,
    int totalPaginas,
    int paginaAtual,
    List<T> itens
){}
