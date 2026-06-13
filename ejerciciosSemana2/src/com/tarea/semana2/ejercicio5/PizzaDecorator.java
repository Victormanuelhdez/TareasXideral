package com.tarea.semana2.ejercicio5;

public abstract class PizzaDecorator implements PizzaOrder {

    protected final PizzaOrder wrapped;

    public PizzaDecorator(PizzaOrder wrapped) {
        this.wrapped = wrapped;
    }
}