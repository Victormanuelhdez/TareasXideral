package com.tarea.semana2.ejercicio5;

public enum Size {

    SMALL(8.99),
    MEDIUM(12.99),
    LARGE(16.99);

    private final double basePrice;

    Size(double basePrice) {
        this.basePrice = basePrice;
    }

    public double getBasePrice() {
        return basePrice;
    }
}