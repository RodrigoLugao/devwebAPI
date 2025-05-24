package rodrigolugao.simasAPI.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CarroNaoEncontradoException.class)
    public ResponseEntity<String> handleCarroNaoEncontrado(CarroNaoEncontradoException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ModeloNaoEncontradoException.class)
    public ResponseEntity<String> handleModeloNaoEncontrado(ModeloNaoEncontradoException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ModeloConflictException.class)
    public ResponseEntity<String> handleModeloConflict(ModeloConflictException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ModeloNaoEnviadoException.class)
    public ResponseEntity<String> handleModeloNaoEnviado(ModeloNaoEnviadoException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
