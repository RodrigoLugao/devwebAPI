package rodrigolugao.simasAPI.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rodrigolugao.simasAPI.dtos.CadastroUsuarioResponseDTO;
import rodrigolugao.simasAPI.dtos.LoginResponseDTO;
import rodrigolugao.simasAPI.entities.CategoriaPeca;
import rodrigolugao.simasAPI.entities.Usuario;
import rodrigolugao.simasAPI.exceptions.NomeDeUsuarioJaExisteException;
import rodrigolugao.simasAPI.exceptions.PecaNaoEncontradaException;
import rodrigolugao.simasAPI.exceptions.UnauthorizedException;
import rodrigolugao.simasAPI.repositories.UsuarioRepository;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenService tokenService;

    /**
     * Tenta autenticar um usuário com as credenciais fornecidas.
     * @param username O nome de usuário.
     * @param password A senha em texto puro.
     * @return Um LoginResponseDTO contendo os dados do usuário e o token JWT.
     * @throws UnauthorizedException se as credenciais forem inválidas.
     */
    public LoginResponseDTO authenticate(String username, String password) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findByUsername(username);

        if (optionalUsuario.isEmpty()) {
            throw new UnauthorizedException("Usuário não encontrado.");
        }

        Usuario usuario = optionalUsuario.get();

        if (!passwordEncoder.matches(password, usuario.getPassword())) {
            throw new UnauthorizedException("Senha inválida.");
        }

        String token = tokenService.generateToken(usuario);

        return new LoginResponseDTO(usuario.getId(), usuario.getUsername(), usuario.isAdmin(), token, usuario.getName(), usuario.getEmail(), usuario.getPhone());
    }

    @Transactional(rollbackFor = Exception.class)
    public CadastroUsuarioResponseDTO create(Usuario usuario) {
        if (usuarioRepository.findByUsername(usuario.getUsername()).isPresent()) {
            throw new NomeDeUsuarioJaExisteException("O nome de usuário '" + usuario.getUsername() + "' já está em uso. Por favor, escolha outro.");
        }

        // Codifica a senha antes de salvar
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuario.setAdmin(false); // Garante que novos usuários não são admin por padrão

        Usuario persistedUsuario = usuarioRepository.save(usuario);
        return new CadastroUsuarioResponseDTO(persistedUsuario.getId(), persistedUsuario.getUsername(), persistedUsuario.isAdmin(), persistedUsuario.getName(), persistedUsuario.getEmail(), persistedUsuario.getPhone());
    }

    public void logout() {
    }
}