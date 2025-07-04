package rodrigolugao.simasAPI.repositories;

import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rodrigolugao.simasAPI.entities.Veiculo;
import rodrigolugao.simasAPI.enums.TipoVeiculo;

import java.util.Optional;

public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {

    @Query("SELECT v FROM Veiculo v " +
            "JOIN v.modelo m " + // Faz um join com a entidade Modelo
            "WHERE (:nomeModelo IS NULL OR LOWER(m.nome) LIKE LOWER(CONCAT('%', :nomeModelo, '%'))) " +
            "AND (:fabricante IS NULL OR LOWER(m.fabricante) LIKE LOWER(CONCAT('%', :fabricante, '%'))) " +
            "AND (:tipo IS NULL OR m.tipo = :tipo) " +
            "AND (:anoMin IS NULL OR m.ano >= :anoMin) " +
            "AND (:anoMax IS NULL OR m.ano <= :anoMax) " +
            "AND (:precoMin IS NULL OR v.preco >= :precoMin) " +
            "AND (:precoMax IS NULL OR v.preco <= :precoMax) " +
            "AND (:kmsMin IS NULL OR v.kmsRodados >= :kmsMin) " +
            "AND (:kmsMax IS NULL OR v.kmsRodados <= :kmsMax) " +
            "AND (:cambio IS NULL OR LOWER(m.cambio) LIKE LOWER(CONCAT('%', :cambio, '%'))) " +
            "AND (:cor IS NULL OR LOWER(v.cor) LIKE LOWER(CONCAT('%', :cor, '%')))"
    )
    Page<Veiculo> getVeiculosWithPagination(
            @Param("nomeModelo") String nomeModelo,
            @Param("fabricante") String fabricante,
            @Param("tipo") TipoVeiculo tipo,
            @Param("anoMin") Integer anoMin,
            @Param("anoMax") Integer anoMax,
            @Param("precoMin") Double precoMin,
            @Param("precoMax") Double precoMax,
            @Param("kmsMin") Integer kmsMin,
            @Param("kmsMax") Integer kmsMax,
            @Param("cambio") String cambio,
            @Param("cor") String cor,
            Pageable pageable
    );

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select v from Veiculo v left outer join fetch v.modelo where v.id = :id")
    Optional<Veiculo> recuperarVeiculoPorIdComLock(@Param("id") Long id);

    Optional<Veiculo> getVeiculoById(long id);

    @Query(
            value = "select v " +
                    "from Veiculo v " +
                    "left outer join fetch v.modelo " +
                    "where v.codigo like lower(concat('%', :codigo, '%')) " +
                    "order by v.codigo",
            countQuery = "select count(v) from Veiculo v where v.codigo like lower(concat('%', :codigo, '%'))"
    )
    Page<Veiculo> getVeiculosWithPaginationByCodigo(Pageable pageable, @Param("codigo") String codigo);

    @Query(
            value = "select v " +
                    "from Veiculo v " +
                    "left outer join fetch v.modelo " +
                    "where v.modelo.id = :idModelo " +
                    "order by v.id",
            countQuery = "select count(v) from Veiculo v where v.modelo.id = :idModelo"
    )
    Page<Veiculo> getVeiculoByModeloWithPagination(Pageable pageable, @Param("idModelo") Long idModelo);

    @Query(
            value = "select v " +
                    "from Veiculo v " +
                    "left outer join fetch v.modelo " +
                    "where v.modelo.nome like lower(concat('%', :nomeModelo, '%'))  " +
                    "order by v.modelo.nome",
            countQuery = "select count(v) from Veiculo v where v.modelo.nome like lower(concat('%', :nomeModelo, '%')) "
    )
    Page<Veiculo> getVeiculoByModeloNameWithPagination(Pageable pageable, @Param("nomeModelo") String nomeModelo);

    Optional<Veiculo> getVeiculoByCodigo(String codigo);
}
