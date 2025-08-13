package com.encuentros.ms_eventos.controller;


import com.encuentros.ms_eventos.dto.EventoDTO;
import com.encuentros.ms_eventos.mapper.EventoMapper;
import com.encuentros.ms_eventos.model.Evento;
import com.encuentros.ms_eventos.messaging.EventPublisher;
import com.encuentros.ms_eventos.repository.EventoRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/eventos")
public class EventoController {

    private final EventoRepository repository;
    private final EventPublisher publisher;

    public EventoController(EventoRepository repository, EventPublisher publisher) {
        this.repository = repository;
        this.publisher = publisher;
    }

    @GetMapping
    public List<EventoDTO> all() {
        return repository.findAll().stream()
                .map(EventoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventoDTO> one(@PathVariable Long id) {
        return repository.findById(id)
                .map(EventoMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EventoDTO> create(@Valid @RequestBody EventoDTO dto) {
        Evento entity = EventoMapper.toEntity(dto);
        Evento saved = repository.save(entity);
        EventoDTO out = EventoMapper.toDTO(saved);

        publisher.publishEventCreated(saved); // Publica todos los campos

        return ResponseEntity.status(HttpStatus.CREATED).body(out);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventoDTO> update(@PathVariable Long id, @Valid @RequestBody EventoDTO dto) {
        return repository.findById(id).map(existing -> {
            Evento updated = EventoMapper.toEntity(dto);
            updated.setId(existing.getId());
            Evento saved = repository.save(updated);
            return ResponseEntity.ok(EventoMapper.toDTO(saved));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
