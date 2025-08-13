package com.encuentros.ms_entradas.dto;

import jakarta.validation.constraints.NotBlank;

public class UsarEntradaDTO {
    @NotBlank public String codigoQR;
}
