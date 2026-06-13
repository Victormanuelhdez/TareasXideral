package com.tarea.semana2.ejercicio6;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class EventBus {

    private final Map<EventType, List<EventHandler>> listeners =
            new EnumMap<>(EventType.class);

    public EventBus() {
        for (EventType type : EventType.values()) {
            listeners.put(type, new ArrayList<>());
        }
    }

    public void subscribe(
            EventType type,
            EventHandler handler
    ) {
        listeners.get(type).add(handler);
    }

    public void publish(
            EventType type,
            String data
    ) {
        System.out.printf("[BUS] Publicando %s%n", type);

        listeners.get(type).forEach(
                handler -> handler.handle(data)
        );
    }
}