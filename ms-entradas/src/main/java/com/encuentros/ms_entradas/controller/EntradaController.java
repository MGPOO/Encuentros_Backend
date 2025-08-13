package com.encuentros.ms_entradas.controller;

import com.encuentros.ms_entradas.dto.CrearEntradaDTO;
import com.encuentros.ms_entradas.dto.UsarEntradaDTO;
import com.encuentros.ms_entradas.model.Entrada;
import com.encuentros.ms_entradas.model.ZonaPlantilla;
import com.encuentros.ms_entradas.repository.EntradaRepository;
import com.encuentros.ms_entradas.repository.ZonaPlantillaRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/entradas")
public class EntradaController {

    private final EntradaRepository entradaRepo;
    private final ZonaPlantillaRepository zonaRepo;

    public EntradaController(EntradaRepository entradaRepo, ZonaPlantillaRepository zonaRepo) {
        this.entradaRepo = entradaRepo;
        this.zonaRepo = zonaRepo;
    }

    @GetMapping
    public List<Entrada> all() {
        return entradaRepo.findAll();
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody CrearEntradaDTO dto) {
        // Buscar precio por zona en la plantilla del evento
        List<ZonaPlantilla> zonas = zonaRepo.findByEventoId(dto.eventoId);
        Optional<ZonaPlantilla> z = zonas.stream()
                .filter(v -> v.getZonaNombre().equalsIgnoreCase(dto.zonaNombre))
                .findFirst();

        if (z.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Zona no encontrada para evento " + dto.eventoId);
        }

        Entrada e = new Entrada();
        e.setEventoId(dto.eventoId);
        e.setZonaNombre(dto.zonaNombre);
        e.setPrecio(z.get().getPrecio());
        e.setCodigoQR(UUID.randomUUID().toString());
        e.setEstado("valida");
        return ResponseEntity.status(HttpStatus.CREATED).body(entradaRepo.save(e));
    }

    @PutMapping("/usar")
    public ResponseEntity<?> usar(@Valid @RequestBody UsarEntradaDTO dto) {
        return entradaRepo.findByCodigoQR(dto.codigoQR)
                .map(e -> {
                    if ("usada".equalsIgnoreCase(e.getEstado())) {
                        return ResponseEntity.status(HttpStatus.CONFLICT).body("Entrada ya usada");
                    }
                    e.setEstado("usada");
                    return ResponseEntity.ok(entradaRepo.save(e));
                }).orElse(ResponseEntity.notFound().build());
    }
}
