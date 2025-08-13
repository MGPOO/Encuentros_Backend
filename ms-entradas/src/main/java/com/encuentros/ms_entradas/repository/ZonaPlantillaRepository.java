package com.encuentros.ms_entradas.repository;

import com.encuentros.ms_entradas.model.ZonaPlantilla;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ZonaPlantillaRepository extends JpaRepository<ZonaPlantilla, Long> {
    List<ZonaPlantilla> findByEventoId(Long eventoId);
}
