package com.encuentros.ms_usuarios.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class LoginRequest {
    @Email    public String correo;
    @NotBlank public String password;
}
