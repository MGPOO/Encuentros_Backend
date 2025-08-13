package com.encuentros.ms_usuarios.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class RegisterRequest {
    @NotBlank public String nombre;
    @Email    public String correo;
    @NotBlank public String password;
    @NotBlank public String rol; // ADMIN | ORGANIZADOR | ASISTENTE
}
