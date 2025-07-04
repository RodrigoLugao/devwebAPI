package rodrigolugao.simasAPI.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rodrigolugao.simasAPI.entities.PecaFavorita;
import rodrigolugao.simasAPI.entities.Peca;
import rodrigolugao.simasAPI.entities.Usuario;

import java.util.List;
import java.util.Optional;

@Repository
public interface PecaFavoritaRepository extends JpaRepository<PecaFavorita, Long> {

    // Método para verificar se uma combinação usuário-peça já existe
    Optional<PecaFavorita> findByUsuarioAndPeca(Usuario usuario, Peca peca);

    // Método para buscar todas as peças favoritas de um determinado usuário
    List<PecaFavorita> findByUsuario(Usuario usuario);

    // Método para deletar uma peça favorita por usuário e peça
    void deleteByUsuarioAndPeca(Usuario usuario, Peca peca);
}