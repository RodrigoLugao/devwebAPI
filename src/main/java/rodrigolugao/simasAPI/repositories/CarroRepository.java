package rodrigolugao.simasAPI.repositories;

import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rodrigolugao.simasAPI.entities.Carro;

import java.util.List;
import java.util.Optional;

public interface CarroRepository extends JpaRepository<Carro, Long> {

    @Query(
            value = "select c " +
                    "from Carro c " +
                    "left outer join fetch c.modelo " +
                    "order by c.codigo",
            countQuery = "select count(c) from Carro c where c.codigo like lower(concat('%', :codigo, '%'))"
    )
    Page<Carro> getCarroWithPagination(Pageable pageable);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select c from Carro c left outer join fetch c.modelo where c.id = :id")
    Optional<Carro> recuperarCarroPorIdComLock(@Param("id") Long id);

    Optional<Carro> getCarroById(long id);

    @Query(
            value = "select c " +
                    "from Carro c " +
                    "left outer join fetch c.modelo " +
                    "where c.codigo like lower(concat('%', :codigo, '%')) " +
                    "order by c.codigo",
            countQuery = "select count(c) from Carro c where c.codigo like lower(concat('%', :codigo, '%'))"
    )
    Page<Carro> getCarroWithPaginationByCodigo(Pageable pageable, @Param("codigo") String codigo);

    @Query(
            value = "select c " +
                    "from Carro c " +
                    "left outer join fetch c.modelo " +
                    "where c.modelo.id = :idModelo " +
                    "order by c.id",
            countQuery = "select count(c) from Carro c where c.modelo.id = :idModelo"
    )
    Page<Carro> getCarroByModeloWithPagination(Pageable pageable, @Param("idModelo") Long idModelo);
}
