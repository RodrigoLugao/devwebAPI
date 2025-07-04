package rodrigolugao.simasAPI.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rodrigolugao.simasAPI.entities.Peca;
import rodrigolugao.simasAPI.entities.PecaFavorita;
import rodrigolugao.simasAPI.entities.Usuario;
import rodrigolugao.simasAPI.exceptions.BadRequestException;
import rodrigolugao.simasAPI.exceptions.PecaFavoritaNaoEncontradaException;
import rodrigolugao.simasAPI.exceptions.PecaNaoEncontradaException;
import rodrigolugao.simasAPI.exceptions.UsuarioNaoEncontradoException;
import rodrigolugao.simasAPI.repositories.PecaFavoritaRepository;
import rodrigolugao.simasAPI.repositories.PecaRepository;
import rodrigolugao.simasAPI.repositories.UsuarioRepository;
import rodrigolugao.simasAPI.dtos.PecaFavoritaResponseDTO;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PecaFavoritaService {

    @Autowired
    private PecaFavoritaRepository pecaFavoritaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PecaRepository pecaRepository;

    /**
     * Adiciona uma peça à lista de favoritas de um usuário.
     * Lança exceções se o usuário/peça não for encontrado ou se a peça já estiver favoritada.
     *
     * @param usuarioId O ID do usuário.
     * @param pecaId    O ID da peça.
     * @return Um PecaFavoritaResponseDTO contendo os IDs do usuário e da peça favoritada.
     */
    @Transactional
    public PecaFavoritaResponseDTO favoritarPeca(Long usuarioId, Long pecaId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado com ID: " + usuarioId));

        Peca peca = pecaRepository.findById(pecaId)
                .orElseThrow(() -> new PecaNaoEncontradaException("Peça não encontrada com ID: " + pecaId));

        if (pecaFavoritaRepository.findByUsuarioAndPeca(usuario, peca).isPresent()) {
            throw new BadRequestException("Esta peça já foi favoritada por este usuário.");
        }

        PecaFavorita novaFavorita = new PecaFavorita(usuario, peca);
        novaFavorita = pecaFavoritaRepository.save(novaFavorita);

        return new PecaFavoritaResponseDTO(novaFavorita.getUsuario().getId(), novaFavorita.getPeca().getId());
    }

    /**
     * Remove uma peça da lista de favoritas de um usuário.
     * Lança exceções se o usuário/peça não for encontrado ou se a peça não estiver favoritada.
     *
     * @param usuarioId O ID do usuário.
     * @param pecaId    O ID da peça.
     */
    @Transactional
    public void desfavoritarPeca(Long usuarioId, Long pecaId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado com ID: " + usuarioId));

        Peca peca = pecaRepository.findById(pecaId)
                .orElseThrow(() -> new PecaNaoEncontradaException("Peça não encontrada com ID: " + pecaId));

        Optional<PecaFavorita> favoritaExistente = pecaFavoritaRepository.findByUsuarioAndPeca(usuario, peca);
        if (favoritaExistente.isEmpty()) {
            throw new PecaFavoritaNaoEncontradaException("Peça não está favoritada por este usuário.");
        }

        pecaFavoritaRepository.deleteByUsuarioAndPeca(usuario, peca);
    }

    /**
     * Busca todas as peças favoritadas por um usuário específico.
     * Retorna uma lista de DTOs contendo apenas os IDs do usuário e da peça.
     *
     * @param usuarioId O ID do usuário.
     * @return Uma lista de PecaFavoritaResponseDTO representando as peças favoritas.
     */
    public List<PecaFavoritaResponseDTO> buscarPecasFavoritasPorUsuario(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado com ID: " + usuarioId));

        List<PecaFavorita> favoritas = pecaFavoritaRepository.findByUsuario(usuario);

        return favoritas.stream()
                .map(fav -> new PecaFavoritaResponseDTO(fav.getUsuario().getId(), fav.getPeca().getId()))
                .collect(Collectors.toList());
    }

    /**
     * Verifica se uma peça específica está favoritada por um determinado usuário.
     *
     * @param usuarioId O ID do usuário.
     * @param pecaId    O ID da peça.
     * @return true se a peça estiver favoritada pelo usuário, false caso contrário.
     */
    public boolean isPecaFavorita(Long usuarioId, Long pecaId) {
        Usuario usuario = usuarioRepository.findById(usuarioId).orElse(null);
        Peca peca = pecaRepository.findById(pecaId).orElse(null);

        if (usuario == null || peca == null) {
            return false;
        }

        return pecaFavoritaRepository.findByUsuarioAndPeca(usuario, peca).isPresent();
    }

    public Optional<PecaFavoritaResponseDTO> buscarPecaFavoritaPorUsuarioPorPeca(Long usuarioId, Long pecaId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado com ID: " + usuarioId));

        Peca peca = pecaRepository.findById(pecaId)
                .orElseThrow(() -> new PecaNaoEncontradaException("Peça não encontrada com ID: " + pecaId));


        return pecaFavoritaRepository.findByUsuarioAndPeca(usuario, peca)
                .map(favorita -> new PecaFavoritaResponseDTO(favorita.getUsuario().getId(), favorita.getPeca().getId()));
    }
}