package com.encuentros.ms_entradas.messaging;

import com.encuentros.ms_entradas.model.ZonaPlantilla;
import com.encuentros.ms_entradas.repository.ZonaPlantillaRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Iterator;

@Component
public class EventosConsumer {

    private final ZonaPlantillaRepository zonaRepo;
    private final ObjectMapper mapper = new ObjectMapper();

    @Value("${mq.queue.entradas:entradas.eventos}")
    private String queueName;

    public EventosConsumer(ZonaPlantillaRepository zonaRepo) {
        this.zonaRepo = zonaRepo;
    }

    @RabbitListener(queues = "${mq.queue.entradas}")
    public void onEventCreated(String json) {
        System.out.println("▶️ Recibido en " + queueName + ": " + json);
        try {
            JsonNode root = mapper.readTree(json);
            Long eventoId = root.path("id").asLong();
            JsonNode zonas = root.path("zona_name");
            JsonNode precios = root.path("precio");

            // Guardar parejas zona/precio en plantilla
            int n = Math.min(zonas.size(), precios.size());
            for (int i = 0; i < n; i++) {
                String z = zonas.get(i).asText();
                BigDecimal p = precios.get(i).decimalValue();
                ZonaPlantilla zp = new ZonaPlantilla();
                zp.setEventoId(eventoId);
                zp.setZonaNombre(z);
                zp.setPrecio(p);
                zonaRepo.save(zp);
            }
            System.out.println("✅ Plantilla zonas/ precios creada para evento " + eventoId);
        } catch (Exception e) {
            System.err.println("❌ Error procesando evento.created: " + e.getMessage());
        }
    }
}
