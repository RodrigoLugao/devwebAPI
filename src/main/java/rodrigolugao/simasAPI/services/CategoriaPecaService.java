package rodrigolugao.simasAPI.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rodrigolugao.simasAPI.entities.CategoriaPeca;
import rodrigolugao.simasAPI.exceptions.CategoriaPecaNaoEncontradaException;
import rodrigolugao.simasAPI.repositories.CategoriaPecaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaPecaService {

    @Autowired
    private CategoriaPecaRepository categoriaPecaRepository;

    /**
     * Retorna todas as categorias de peças.
     * @return Uma lista de CategoriaPeca.
     */
    public List<CategoriaPeca> findAll() {
        return categoriaPecaRepository.findAll();
    }

    /**
     * Retorna uma categoria de peça pelo ID.
     * @param id O ID da categoria de peça.
     * @return A CategoriaPeca encontrada.
     * @throws CategoriaPecaNaoEncontradaException se a categoria não for encontrada.
     */
    public CategoriaPeca findById(Long id) {
        Optional<CategoriaPeca> obj = categoriaPecaRepository.findById(id);
        return obj.orElseThrow(() -> new CategoriaPecaNaoEncontradaException("Categoria de Peça não encontrada com ID: " + id));
    }

    /**
     * Cria uma nova categoria de peça.
     * @param categoriaPeca O objeto CategoriaPeca a ser salvo.
     * @return A CategoriaPeca salva.
     */
    public CategoriaPeca create(CategoriaPeca categoriaPeca) {
        // Você pode adicionar lógicas de validação adicionais aqui antes de salvar
        return categoriaPecaRepository.save(categoriaPeca);
    }

    /**
     * Atualiza uma categoria de peça existente.
     * @param id O ID da categoria de peça a ser atualizada.
     * @param categoriaPeca Os dados atualizados da CategoriaPeca.
     * @return A CategoriaPeca atualizada.
     * @throws CategoriaPecaNaoEncontradaException se a categoria não for encontrada.
     */
    public CategoriaPeca update(Long id, CategoriaPeca categoriaPeca) {
        // Verifica se a categoria existe antes de atualizar
        CategoriaPeca existingCategoria = findById(id); // Reutiliza o findById para verificar a existência

        // Atualiza apenas os campos permitidos ou necessários
        existingCategoria.setNome(categoriaPeca.getNome());
        existingCategoria.setSlug(categoriaPeca.getSlug());
        existingCategoria.setDescricao(categoriaPeca.getDescricao());
        existingCategoria.setImagem(categoriaPeca.getImagem());

        return categoriaPecaRepository.save(existingCategoria);
    }

    /**
     * Deleta uma categoria de peça pelo ID.
     * @param id O ID da categoria de peça a ser deletada.
     * @throws CategoriaPecaNaoEncontradaException se a categoria não for encontrada.
     */
    public void delete(Long id) {
        // Verifica se a categoria existe antes de deletar
        findById(id); // Lança CategoriaPecaNaoEncontradaException se não encontrar
        categoriaPecaRepository.deleteById(id);
    }
}