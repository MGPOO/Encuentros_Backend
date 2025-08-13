package com.encuentros.ms_eventos.repository;

import com.encuentros.ms_eventos.model.Evento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventoRepository extends JpaRepository<Evento, Long> { }

