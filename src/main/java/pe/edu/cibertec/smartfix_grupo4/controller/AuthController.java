
package pe.edu.cibertec.smartfix_grupo4.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import pe.edu.cibertec.smartfix_grupo4.entity.Usuario;
import pe.edu.cibertec.smartfix_grupo4.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UsuarioService usuarioService;

    public AuthController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    public record LoginRequest(
            @NotBlank String username,
            @NotBlank String password) {
    }

    public record RegistroRequest(
            @NotBlank String username,
            @NotBlank String password) {
    }

    public record AuthResponse(String mensaje) {
    }

    @PostMapping("/registro")
    public ResponseEntity<AuthResponse> registrar(
            @Valid @RequestBody RegistroRequest request) {

        Usuario usuario = new Usuario();
        usuario.setUsername(request.username());
        usuario.setPassword(request.password());

        usuarioService.registrar(usuario);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new AuthResponse("Usuario registrado correctamente"));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody LoginRequest request) {

        boolean valido = usuarioService.validarCredenciales(
                request.username(), request.password());

        if (!valido) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthResponse("Usuario o contraseña incorrectos"));
        }

        return ResponseEntity.ok(
                new AuthResponse("Inicio de sesión exitoso"));
    }
}
