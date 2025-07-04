package rodrigolugao.simasAPI.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "tb_usuario")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome de usuário é obrigatório")
    @Size(min = 3, max = 20, message = "O nome de usuário deve ter entre 3 e 20 caracteres")
    @Pattern(regexp = "^\\S+$", message = "O nome de usuário não pode conter espaços em branco")
    @Column(unique = true, nullable = false)
    private String username;

    @NotBlank
    @Size(min = 5, message = "A senha deve ter no mínimo 5 caracteres.")
    private String password;

    @NotBlank
    @Size(min = 3, message = "O nome deve ter no mínimo 3 caracteres")
    private String name;


    @NotBlank
    @Email(message = "Formato de email inválido.")
    private String email;

    @NotBlank(message = "O telefone é obrigatório")
    @Pattern(regexp = "^\\(?\\d{2}\\)?\\s?\\d{4,5}-?\\d{4}$",
            message = "Formato de telefone inválido. Use (XX)YYYY-ZZZZ ou XXXXXXXXXXX.")
    private String phone;


    private boolean isAdmin;

    public Usuario(String username, String password, boolean isAdmin, String name, String email, String phone) {
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
        this.email = email;
        this.name = name;
        this.phone = phone;
    }
}