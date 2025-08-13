package com.encuentros.ms_eventos.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

public class EventoDTO {
    public Long id;

    @NotBlank public String nombre;
    @NotBlank public String descripcion;
    @NotBlank public String fecha; // ISO: yyyy-MM-dd
    @NotBlank public String tipo;
    @NotNull  public Long zona_id;
    @NotNull  public List<String> zona_name;
    @NotNull  public Integer quantity;
    @NotBlank public String imagen;
    @NotNull  public List<BigDecimal> precio;
    @NotBlank public String estado;
}
