package com.encuentros.ms_usuarios.dto;

public class AuthResponse {
    public String token;
    public Long   id;
    public String nombre;
    public String correo;
    public String rol;

    public AuthResponse(String token, Long id, String nombre, String correo, String rol) {
        this.token = token; this.id = id; this.nombre = nombre; this.correo = correo; this.rol = rol;
    }
}
