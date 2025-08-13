package com.encuentros.ms_usuarios.controller;

import com.encuentros.ms_usuarios.dto.*;
import com.encuentros.ms_usuarios.model.Usuario;
import com.encuentros.ms_usuarios.repository.UsuarioRepository;
import com.encuentros.ms_usuarios.security.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UsuarioRepository repo;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authManager;
    private final JwtService jwt;

    public AuthController(UsuarioRepository repo, PasswordEncoder encoder,
                          AuthenticationManager authManager, JwtService jwt) {
        this.repo = repo;
        this.encoder = encoder;
        this.authManager = authManager;
        this.jwt = jwt;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest in) {
        if (repo.findByCorreo(in.correo).isPresent()) {
            return ResponseEntity.badRequest().body("Correo ya registrado");
        }
        Usuario u = new Usuario();
        u.setNombre(in.nombre);
        u.setCorreo(in.correo);
        u.setPasswordHash(encoder.encode(in.password));
        u.setRol(in.rol.toUpperCase()); // ADMIN | ORGANIZADOR | ASISTENTE
        u = repo.save(u);

        String token = jwt.generate(u.getCorreo(), Map.of("rol", u.getRol(), "uid", u.getId()));
        return ResponseEntity.ok(new AuthResponse(token, u.getId(), u.getNombre(), u.getCorreo(), u.getRol()));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest in) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(in.correo, in.password));
        Usuario u = repo.findByCorreo(in.correo).orElseThrow();
        String token = jwt.generate(u.getCorreo(), Map.of("rol", u.getRol(), "uid", u.getId()));
        return ResponseEntity.ok(new AuthResponse(token, u.getId(), u.getNombre(), u.getCorreo(), u.getRol()));
    }
}
