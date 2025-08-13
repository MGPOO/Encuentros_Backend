package com.encuentros.ms_eventos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "eventos")
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nombre;

    @NotBlank
    @Column(length = 2000)
    private String descripcion;

    @NotNull
    private LocalDate fecha;

    @NotBlank
    private String tipo;

    @NotNull
    @Column(name = "zona_id")
    private Long zonaId;

    @ElementCollection
    @CollectionTable(name = "evento_zonas", joinColumns = @JoinColumn(name = "evento_id"))
    @Column(name = "zona_name")
    private List<String> zonaName = new ArrayList<>();

    @NotNull
    private Integer quantity;

    @NotBlank
    @Column(length = 1000)
    private String imagen;

    @ElementCollection
    @CollectionTable(name = "evento_precios", joinColumns = @JoinColumn(name = "evento_id"))
    @Column(name = "precio")
    private List<BigDecimal> precio = new ArrayList<>();

    @NotBlank
    private String estado; // activo | inactivo

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public Long getZonaId() { return zonaId; }
    public void setZonaId(Long zonaId) { this.zonaId = zonaId; }

    public List<String> getZonaName() { return zonaName; }
    public void setZonaName(List<String> zonaName) { this.zonaName = zonaName; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public String getImagen() { return imagen; }
    public void setImagen(String imagen) { this.imagen = imagen; }

    public List<BigDecimal> getPrecio() { return precio; }
    public void setPrecio(List<BigDecimal> precio) { this.precio = precio; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
