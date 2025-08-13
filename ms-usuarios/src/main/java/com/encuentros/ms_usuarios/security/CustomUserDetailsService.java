package com.encuentros.ms_usuarios.security;

import com.encuentros.ms_usuarios.model.Usuario;
import com.encuentros.ms_usuarios.repository.UsuarioRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository repo;

    public CustomUserDetailsService(UsuarioRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario u = repo.findByCorreo(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        return new User(u.getCorreo(), u.getPasswordHash(),
                List.of(new SimpleGrantedAuthority("ROLE_" + u.getRol().toUpperCase())));
    }
}
