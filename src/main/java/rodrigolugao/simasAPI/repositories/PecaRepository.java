package rodrigolugao.simasAPI.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query; // Importar a anotação Query
import org.springframework.data.repository.query.Param; // Importar a anotação Param
import org.springframework.stereotype.Repository;
import rodrigolugao.simasAPI.entities.Peca;
import rodrigolugao.simasAPI.entities.CategoriaPeca;

import java.util.List;

@Repository
public interface PecaRepository extends JpaRepository<Peca, Long> {

    // Método para buscar peças por CategoriaPeca (com paginação)
    Page<Peca> findByCategoriaPeca(CategoriaPeca categoriaPeca, Pageable pageable);

    /**
     * Busca todas as peças que são favoritas para um dado usuário, com paginação.
     * Utiliza um JOIN implícito entre Peca e PecaFavorita através da entidade PecaFavorita.
     *
     * @param usuarioId O ID do usuário.
     * @param pageable Objeto Pageable para configurar a paginação.
     * @return Uma Page de objetos Peca favoritados pelo usuário.
     */
    @Query("SELECT p FROM Peca p JOIN PecaFavorita pf ON p.id = pf.peca.id WHERE pf.usuario.id = :usuarioId")
    Page<Peca> findFavoritePecasByUsuarioId(@Param("usuarioId") Long usuarioId, Pageable pageable);

    @Query("SELECT p FROM Peca p WHERE p.id IN :ids")
    Page<Peca> findPecasByIdsWithPagination(@Param("ids") List<Long> ids, Pageable pageable);
}