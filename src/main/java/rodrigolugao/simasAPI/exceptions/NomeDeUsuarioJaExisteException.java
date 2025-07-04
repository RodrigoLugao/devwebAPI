package rodrigolugao.simasAPI.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT) // HTTP 409 Conflict
public class NomeDeUsuarioJaExisteException extends RuntimeException {
    public NomeDeUsuarioJaExisteException(String message) {
        super(message);
    }
}