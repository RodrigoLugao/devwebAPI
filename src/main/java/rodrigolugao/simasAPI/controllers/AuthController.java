package rodrigolugao.simasAPI.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rodrigolugao.simasAPI.dtos.AuthenticationDTO;
import rodrigolugao.simasAPI.dtos.CadastroUsuarioResponseDTO;
import rodrigolugao.simasAPI.dtos.LoginResponseDTO;
import rodrigolugao.simasAPI.entities.Usuario;
import rodrigolugao.simasAPI.services.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody @Valid AuthenticationDTO data) {
        return authService.authenticate(data.username(), data.password());
    }


    @PostMapping("/logout")
    public void logout() {
        authService.logout();
    }

    @PostMapping("/cadastrar")
    public CadastroUsuarioResponseDTO cadastrar(@RequestBody @Valid Usuario usuario) {
        return authService.create(usuario);
    }
}