package rodrigolugao.simasAPI.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rodrigolugao.simasAPI.dtos.FavoritarPecaDTO;
import rodrigolugao.simasAPI.dtos.PecaFavoritaResponseDTO;

import rodrigolugao.simasAPI.services.PecaFavoritaService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pecas-favoritas")
public class PecaFavoritaController {

    @Autowired
    private PecaFavoritaService pecaFavoritaService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public PecaFavoritaResponseDTO favoritarPeca(@RequestBody FavoritarPecaDTO dto) {
        return pecaFavoritaService.favoritarPeca(dto.usuarioId(), dto.pecaId());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping
    public void desfavoritarPeca(@RequestBody FavoritarPecaDTO dto) {
        pecaFavoritaService.desfavoritarPeca(dto.usuarioId(), dto.pecaId());
    }

    @GetMapping("/usuario/{usuarioId}")
    public List<PecaFavoritaResponseDTO> getPecasFavoritasPorUsuario(@PathVariable Long usuarioId) {
        return pecaFavoritaService.buscarPecasFavoritasPorUsuario(usuarioId);
    }

    @GetMapping("/usuario/{usuarioId}/peca/{pecaId}")
    public ResponseEntity<PecaFavoritaResponseDTO> getPecaFavoritaPorUsuarioPorPeca(@PathVariable Long usuarioId, @PathVariable Long pecaId) {
        Optional<PecaFavoritaResponseDTO> favorita = pecaFavoritaService.buscarPecaFavoritaPorUsuarioPorPeca(usuarioId, pecaId);

        if(favorita.isPresent()){
            return ResponseEntity.of(favorita);
        }else{
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }

    }
}