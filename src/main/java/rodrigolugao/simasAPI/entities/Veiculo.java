package rodrigolugao.simasAPI.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Veiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false)
    @JoinColumn(name = "modelo_id", nullable = false)
    private Modelo modelo;
    private Long kmsRodados;
    private String descricao;
    @NotBlank
    private String codigo;
    private BigDecimal preco;
    private LocalDate dataCadastro = LocalDate.now();
    private String imagem;
    private boolean vendido = false;
    private String cor;
}
