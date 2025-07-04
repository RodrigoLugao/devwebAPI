package rodrigolugao.simasAPI.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class CategoriaPecaNaoEncontradaException extends EntidadeNaoEncontradaException {
    public CategoriaPecaNaoEncontradaException(String message) {
        super(message);
    }
}