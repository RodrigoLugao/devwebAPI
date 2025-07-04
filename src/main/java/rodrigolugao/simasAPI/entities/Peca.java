package rodrigolugao.simasAPI.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Peca {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String imagem;

    @NotEmpty(message = "O nome da peça deve ser informado.")
    private String nome;

    private String slug;

    @NotEmpty(message = "A descrição da peça deve ser informada.")
    private String descricao;

    private boolean disponivel;

    @NotNull(message = "A 'Quantidade em estoque' deve ser informada.")
    @Min(value=0, message = "A 'Quantidade em estoque' deve ser maior ou igual a 0.")
    private int qtdEstoque;

    @NotNull(message = "O 'Preço' deve ser informado.")
    @DecimalMin(value="0.01", message = "O 'Preço' deve ser maior ou igual a 0.01.")
    private BigDecimal preco;

    @NotNull(message = "A 'Data de Cadastro' deve ser informada.")
    private LocalDate dataCadastro;

    @ManyToOne
    @NotNull(message = "A 'Categoria' deve ser informada.")
    private CategoriaPeca categoriaPeca;

    @JsonIgnore
    @OneToMany(mappedBy = "peca", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PecaFavorita> pecasFavoritas = new HashSet<>();

    public Peca(String imagem, String nome, String slug, String descricao, boolean disponivel,
                int qtdEstoque, BigDecimal preco, LocalDate dataCadastro, CategoriaPeca categoriaPeca) {
        this.imagem = imagem;
        this.nome = nome;
        this.slug = slug;
        this.descricao = descricao;
        this.disponivel = disponivel;
        this.qtdEstoque = qtdEstoque;
        this.preco = preco;
        this.dataCadastro = dataCadastro;
        this.categoriaPeca = categoriaPeca;
    }

}
