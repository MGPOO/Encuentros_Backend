package com.encuentros.ms_eventos.mapper;


import com.encuentros.ms_eventos.dto.EventoDTO;
import com.encuentros.ms_eventos.model.Evento;

import java.time.LocalDate;

public class EventoMapper {

    public static Evento toEntity(EventoDTO dto) {
        Evento e = new Evento();
        e.setId(dto.id);
        e.setNombre(dto.nombre);
        e.setDescripcion(dto.descripcion);
        e.setFecha(LocalDate.parse(dto.fecha));
        e.setTipo(dto.tipo);
        e.setZonaId(dto.zona_id);
        e.setZonaName(dto.zona_name);
        e.setQuantity(dto.quantity);
        e.setImagen(dto.imagen);
        e.setPrecio(dto.precio);
        e.setEstado(dto.estado);
        return e;
    }

    public static EventoDTO toDTO(Evento e) {
        EventoDTO dto = new EventoDTO();
        dto.id = e.getId();
        dto.nombre = e.getNombre();
        dto.descripcion = e.getDescripcion();
        dto.fecha = e.getFecha().toString();
        dto.tipo = e.getTipo();
        dto.zona_id = e.getZonaId();
        dto.zona_name = e.getZonaName();
        dto.quantity = e.getQuantity();
        dto.imagen = e.getImagen();
        dto.precio = e.getPrecio();
        dto.estado = e.getEstado();
        return dto;
    }
}
