package rodrigolugao.simasAPI.dtos;

// Record para o DTO de resposta de login
public record LoginResponseDTO(Long id, String username, boolean isAdmin, String token, String nome, String email, String telefone) {
}