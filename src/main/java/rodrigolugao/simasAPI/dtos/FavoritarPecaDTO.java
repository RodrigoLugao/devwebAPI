package rodrigolugao.simasAPI.dtos;

import jakarta.validation.constraints.NotNull;

public record FavoritarPecaDTO(
        @NotNull Long usuarioId,
        @NotNull Long pecaId
) {}