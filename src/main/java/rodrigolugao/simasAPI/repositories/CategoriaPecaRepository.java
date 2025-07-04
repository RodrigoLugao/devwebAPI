package rodrigolugao.simasAPI.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rodrigolugao.simasAPI.entities.CategoriaPeca;

import java.util.Optional;

@Repository
public interface CategoriaPecaRepository extends JpaRepository<CategoriaPeca, Long> {

    // Adicione este método para buscar uma categoria pelo slug
    Optional<CategoriaPeca> findBySlug(String slug);
}