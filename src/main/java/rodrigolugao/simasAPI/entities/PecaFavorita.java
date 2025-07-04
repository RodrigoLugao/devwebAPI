package rodrigolugao.simasAPI.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "tb_peca_favorita",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"usuario_id", "peca_id"})
        })
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class PecaFavorita implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "peca_id", nullable = false)
    private Peca peca;

    public PecaFavorita(Usuario usuario, Peca peca){
        this.usuario = usuario;
        this.peca = peca;
    }
}