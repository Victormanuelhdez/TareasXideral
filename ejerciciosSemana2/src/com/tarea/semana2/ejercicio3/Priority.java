package com.tarea.semana2.ejercicio3;

public enum Priority {

    LOW(1, 48),
    MEDIUM(2, 24),
    HIGH(3, 8),
    CRITICAL(4, 1);

    private final int level;
    private final int responseTimeHours;

    Priority(int level, int responseTimeHours) {
        this.level = level;
        this.responseTimeHours = responseTimeHours;
    }

    public int getLevel() {
        return level;
    }

    public int getResponseTimeHours() {
        return responseTimeHours;
    }

    public String getLabel() {
        return String.format(
                "%s (Nivel %d, Respuesta: %dh)",
                name(),
                level,
                responseTimeHours
        );
    }
}