
package pe.edu.cibertec.smartfix_grupo4.service;

import pe.edu.cibertec.smartfix_grupo4.entity.Usuario;
import pe.edu.cibertec.smartfix_grupo4.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario registrar(Usuario usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return usuarioRepository.save(usuario);
    }

    public Optional<Usuario> buscarPorUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    public boolean validarCredenciales(String username, String password) {
        return usuarioRepository.findByUsername(username)
                .map(usuario -> passwordEncoder.matches(
                        password, usuario.getPassword()))
                .orElse(false);
    }
}
