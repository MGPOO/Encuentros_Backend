package com.encuentros.ms_entradas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class CrearEntradaDTO {
    @NotNull public Long eventoId;
    @NotBlank public String zonaNombre;
}
