package com.tarea.semana2.ejercicio5;

public class GiftBoxDecorator extends PizzaDecorator {

    public GiftBoxDecorator(PizzaOrder wrapped) {
        super(wrapped);
    }

    @Override
    public String getDescription() {
        return wrapped.getDescription() + " + Caja Regalo";
    }

    @Override
    public double getPrice() {
        return wrapped.getPrice() + 3.00;
    }
}