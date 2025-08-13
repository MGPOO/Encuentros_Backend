package com.encuentros.ms_eventos.messaging;

import com.encuentros.ms_eventos.model.Evento;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
public class EventPublisher {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper mapper = new ObjectMapper();

    @Value("${mq.exchange}") private String exchange;
    @Value("${mq.routing.event.created}") private String routingKey;

    public EventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishEventCreated(Evento e) {
        try {
            Map<String, Object> payload = new HashMap<>();
            payload.put("id", e.getId());
            payload.put("nombre", e.getNombre());
            payload.put("descripcion", e.getDescripcion());
            payload.put("fecha", e.getFecha().toString());
            payload.put("tipo", e.getTipo());
            payload.put("zona_id", e.getZonaId());
            payload.put("zona_name", e.getZonaName());
            payload.put("quantity", e.getQuantity());
            payload.put("imagen", e.getImagen());
            payload.put("precio", e.getPrecio());
            payload.put("estado", e.getEstado());
            payload.put("timestamp", OffsetDateTime.now().toString());

            String json = mapper.writeValueAsString(payload);
            rabbitTemplate.convertAndSend(exchange, routingKey, json);
            System.out.println("✅ Published event.created -> " + json);
        } catch (Exception ex) {
            System.err.println("❌ Error publishing event: " + ex.getMessage());
        }
    }
}
