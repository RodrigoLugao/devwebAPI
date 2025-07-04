package rodrigolugao.simasAPI.dtos;

// Record para o DTO de resposta de login
public record CadastroUsuarioResponseDTO(Long id, String username, boolean isAdmin, String nome, String email, String telefone) {
}