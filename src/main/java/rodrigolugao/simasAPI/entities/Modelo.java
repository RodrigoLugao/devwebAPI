package rodrigolugao.simasAPI.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

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
    public enum Tipo{
        Carro, Moto
    }
    public enum Cambio {
        Automatico, Semiautomatico, Manual
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @NotBlank
    private String nome;
    private String marca;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Tipo tipo;
    private int ano;
    private String motor;
    private int qtdEstoque;
    private String combustivel;
    private String imagem;
}
