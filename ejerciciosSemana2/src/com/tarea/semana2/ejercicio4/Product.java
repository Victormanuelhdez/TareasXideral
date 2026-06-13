package com.tarea.semana2.ejercicio4;

public record Product(
        String name,
        double price,
        String category,
        boolean inStock
) {

    public boolean isAvailable() {
        return inStock;
    }

    public String toDisplayString() {
        return String.format(
                "%-15s $%7.2f  %-12s [%s]",
                name,
                price,
                category,
                inStock ? "En stock" : "Agotado"
        );
    }
}