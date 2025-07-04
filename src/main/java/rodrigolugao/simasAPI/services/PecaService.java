package rodrigolugao.simasAPI.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rodrigolugao.simasAPI.entities.Peca;
import rodrigolugao.simasAPI.entities.CategoriaPeca;
import rodrigolugao.simasAPI.entities.Usuario;
import rodrigolugao.simasAPI.exceptions.PecaNaoEncontradaException;
import rodrigolugao.simasAPI.exceptions.UsuarioNaoEncontradoException;
import rodrigolugao.simasAPI.repositories.PecaRepository;
import rodrigolugao.simasAPI.repositories.CategoriaPecaRepository;
import rodrigolugao.simasAPI.repositories.UsuarioRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PecaService {

    @Autowired
    private PecaRepository pecaRepository;

    @Autowired
    private CategoriaPecaRepository categoriaPecaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Retorna todas as peças.
     * @return Uma lista de Peca.
     */
    public List<Peca> findAll() {
        return pecaRepository.findAll();
    }

    /**
     * Retorna uma peça pelo ID.
     * @param id O ID da peça.
     * @return A Peca encontrada.
     * @throws PecaNaoEncontradaException se a peça não for encontrada.
     */
    public Peca findById(Long id) {
        Optional<Peca> obj = pecaRepository.findById(id);
        return obj.orElseThrow(() -> new PecaNaoEncontradaException("Peça não encontrada com ID: " + id));
    }

    /**
     * Cria uma nova peça.
     * @param peca O objeto Peca a ser salvo.
     * @return A Peca salva.
     * @throws PecaNaoEncontradaException se a categoria associada não for encontrada.
     */
    @Transactional(rollbackFor = Exception.class)
    public Peca create(Peca peca) {
        if (peca.getCategoriaPeca() != null && peca.getCategoriaPeca().getId() != null) {
            CategoriaPeca categoria = categoriaPecaRepository.findById(peca.getCategoriaPeca().getId())
                    .orElseThrow(() -> new PecaNaoEncontradaException("Categoria de Peça não encontrada com ID: " + peca.getCategoriaPeca().getId()));
            peca.setCategoriaPeca(categoria);
        } else {
            throw new IllegalArgumentException("Peça deve ter uma CategoriaPeca válida associada.");
        }
        return pecaRepository.save(peca);
    }

    /**
     * Atualiza uma peça existente.
     * @param id O ID da peça a ser atualizada.
     * @param peca Os dados atualizados da Peca.
     * @return A Peca atualizada.
     * @throws PecaNaoEncontradaException se a peça ou a categoria associada não for encontrada.
     */
    @Transactional(rollbackFor = Exception.class)
    public Peca update(Long id, Peca peca) {
        Peca existingPeca = findById(id); // findById já lança exceção se não encontrar

        existingPeca.setImagem(peca.getImagem());
        existingPeca.setNome(peca.getNome());
        existingPeca.setSlug(peca.getSlug());
        existingPeca.setDescricao(peca.getDescricao());
        existingPeca.setDisponivel(peca.isDisponivel());
        existingPeca.setQtdEstoque(peca.getQtdEstoque());
        existingPeca.setPreco(peca.getPreco());

        if (peca.getCategoriaPeca() != null && peca.getCategoriaPeca().getId() != null) {
            CategoriaPeca categoria = categoriaPecaRepository.findById(peca.getCategoriaPeca().getId())
                    .orElseThrow(() -> new PecaNaoEncontradaException("Categoria de Peça não encontrada com ID: " + peca.getCategoriaPeca().getId()));
            existingPeca.setCategoriaPeca(categoria);
        } else if (peca.getCategoriaPeca() == null) {
            existingPeca.setCategoriaPeca(null);
        }

        return pecaRepository.save(existingPeca);
    }

    /**
     * Deleta uma peça pelo ID.
     * @param id O ID da peça a ser deletada.
     * @throws PecaNaoEncontradaException se a peça não for encontrada.
     */
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        // findById(id) já lança PecaNaoEncontradaException se não encontrar
        // e garante que a peça existe antes de tentar deletar.
        findById(id);
        pecaRepository.deleteById(id);
    }

    /**
     * Retorna peças paginadas, opcionalmente filtradas por slug de categoria.
     * @param slugCategoria O slug da categoria para filtrar (opcional).
     * @return Uma página de Peca.
     */
    public Page<Peca> findPecasByCategoriaSlugWithPagination(String slugCategoria, Pageable pageable) {
        if (slugCategoria != null && !slugCategoria.isEmpty()) {
            Optional<CategoriaPeca> categoriaOptional = categoriaPecaRepository.findBySlug(slugCategoria);
            if (categoriaOptional.isPresent()) {
                return pecaRepository.findByCategoriaPeca(categoriaOptional.get(), pageable);
            } else {
                return Page.empty(pageable);
            }
        } else {
            return pecaRepository.findAll(pageable);
        }
    }

    /**
     * Retorna peças favoritas de um usuário paginadas.
     * @param usuarioId O ID do usuário.
     * @return Uma Page de objetos Peca favoritados pelo usuário.
     * @throws UsuarioNaoEncontradoException se o usuário não for encontrado.
     */
    public Page<Peca> findFavoritePecasByUsuarioIdPaginado(Long usuarioId, Pageable pageable) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado com ID: " + usuarioId));

        return pecaRepository.findFavoritePecasByUsuarioId(usuarioId, pageable);
    }

    /**
     * Retorna peças por IDs com paginação.
     * @param ids Lista de IDs das peças.
     * @param pageable Objeto Pageable para paginação.
     * @return Uma Page de objetos Peca.
     */
    public Page<Peca> findPecasByIds(List<Long> ids, Pageable pageable) {
        return pecaRepository.findPecasByIdsWithPagination(ids, pageable);
    }
}