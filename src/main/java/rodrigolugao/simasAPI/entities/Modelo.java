package rodrigolugao.simasAPI.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import rodrigolugao.simasAPI.enums.TipoVeiculo;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"nome", "ano"})
        }
)
public class Modelo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @NotBlank
    private String nome;
    private String fabricante;
    @NotNull
    @Enumerated(EnumType.STRING)
    private TipoVeiculo tipo;
    private int ano;
    private String cambio;
    private String motor;
    private int qtdEstoque;
    private String combustivel;
    private String imagem;
}
