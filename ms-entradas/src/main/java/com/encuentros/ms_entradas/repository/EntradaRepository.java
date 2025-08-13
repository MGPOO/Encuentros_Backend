package com.encuentros.ms_entradas.repository;

import com.encuentros.ms_entradas.model.Entrada;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EntradaRepository extends JpaRepository<Entrada, Long> {
    Optional<Entrada> findByCodigoQR(String codigoQR);
}
