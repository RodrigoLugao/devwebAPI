package rodrigolugao.simasAPI.repositories;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import rodrigolugao.simasAPI.entities.Modelo;

public interface ModeloRepository extends JpaRepository<Modelo, Long> {
    boolean existsByNomeAndAno(@NotNull @NotBlank String nome, int ano);
}
