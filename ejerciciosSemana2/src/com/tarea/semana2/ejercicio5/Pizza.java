package com.tarea.semana2.ejercicio5;

import java.util.ArrayList;
import java.util.List;

public class Pizza implements PizzaOrder {

    private final Size size;
    private final Crust crust;
    private final List<String> toppings;

    private Pizza(Builder builder) {
        this.size = builder.size;
        this.crust = builder.crust;
        this.toppings = List.copyOf(builder.toppings);
    }

    @Override
    public String getDescription() {
        return String.format(
                "Pizza %s, %s con %s",
                size,
                crust,
                toppings
        );
    }

    @Override
    public double getPrice() {
        return size.getBasePrice()
                + crust.getExtraCost()
                + toppings.size() * 1.50;
    }

    public static class Builder {

        private final Size size;
        private final Crust crust;
        private final List<String> toppings = new ArrayList<>();

        public Builder(Size size, Crust crust) {
            this.size = size;
            this.crust = crust;
        }

        public Builder addTopping(String topping) {
            if (toppings.size() >= 5) {
                throw new IllegalStateException("Maximo 5 toppings");
            }

            toppings.add(topping);
            return this;
        }

        public Pizza build() {
            if (toppings.isEmpty()) {
                throw new IllegalStateException("Minimo 1 topping");
            }

            return new Pizza(this);
        }
    }
}