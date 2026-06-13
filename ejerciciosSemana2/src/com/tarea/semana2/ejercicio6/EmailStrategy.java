package com.tarea.semana2.ejercicio6;

public class EmailStrategy implements NotificationStrategy {

    @Override
    public void send(String message) {
        System.out.println("[EMAIL] Enviando: " + message);
    }
}