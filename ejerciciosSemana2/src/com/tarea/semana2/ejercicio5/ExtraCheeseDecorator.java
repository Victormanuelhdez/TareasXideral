package com.tarea.semana2.ejercicio5;

public class ExtraCheeseDecorator extends PizzaDecorator {

    public ExtraCheeseDecorator(PizzaOrder wrapped) {
        super(wrapped);
    }

    @Override
    public String getDescription() {
        return wrapped.getDescription() + " + Extra Queso";
    }

    @Override
    public double getPrice() {
        return wrapped.getPrice() + 2.50;
    }
}