package com.tarea.semana2.ejercicio3;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.List;

public class TicketSystem {

    public static void main(String[] args) {
        List<Ticket> tickets = new ArrayList<>();

        tickets.add(
                new Ticket(
                        1,
                        "Login falla",
                        Priority.CRITICAL
                )
        );

        tickets.add(
                new Ticket(
                        2,
                        "Boton desalineado",
                        Priority.LOW
                )
        );

        tickets.add(
                new Ticket(
                        3,
                        "Error en pagos",
                        Priority.HIGH
                )
        );

        tickets.add(
                new Ticket(
                        4,
                        "Mejorar docs",
                        Priority.MEDIUM
                )
        );

        System.out.println("=== Todos los Tickets ===");
        tickets.forEach(System.out::println);

        System.out.println("\n=== Transiciones ===");

        tickets.get(0).transitionTo(
                TicketStatus.IN_PROGRESS
        );

        tickets.get(2).transitionTo(
                TicketStatus.IN_PROGRESS
        );

        tickets.get(2).transitionTo(
                TicketStatus.RESOLVED
        );

        tickets.get(2).transitionTo(
                TicketStatus.OPEN
        );

        System.out.println("\n=== Estado Actualizado ===");
        tickets.forEach(System.out::println);

        System.out.println("\n=== Dashboard (EnumMap) ===");

        EnumMap<TicketStatus, Integer> conteo =
                new EnumMap<>(TicketStatus.class);

        for (TicketStatus status : TicketStatus.values()) {
            conteo.put(status, 0);
        }

        for (Ticket ticket : tickets) {
            TicketStatus status = ticket.getStatus();

            conteo.put(
                    status,
                    conteo.get(status) + 1
            );
        }

        conteo.forEach(
                (status, count) ->
                        System.out.printf(
                                "  %s: %d%n",
                                status,
                                count
                        )
        );

        System.out.println(
                "\n=== Tickets Urgentes (EnumSet) ==="
        );

        EnumSet<Priority> urgentes = EnumSet.of(
                Priority.HIGH,
                Priority.CRITICAL
        );

        for (Ticket ticket : tickets) {
            if (urgentes.contains(ticket.getPriority())) {
                System.out.println(ticket);
            }
        }
    }
}