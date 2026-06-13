package com.tarea.semana2.ejercicio3;

public enum TicketStatus {

    OPEN,
    IN_PROGRESS,
    RESOLVED,
    CLOSED;

    public boolean canTransitionTo(TicketStatus target) {
        return switch (this) {
            case OPEN -> target == IN_PROGRESS;
            case IN_PROGRESS ->
                    target == RESOLVED || target == OPEN;
            case RESOLVED ->
                    target == CLOSED || target == IN_PROGRESS;
            case CLOSED -> false;
        };
    }
}