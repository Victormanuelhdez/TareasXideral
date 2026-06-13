package com.tarea.semana2.ejercicio6;

public class SlackStrategy implements NotificationStrategy {

    @Override
    public void send(String message) {
        System.out.println("[SLACK] Enviando: " + message);
    }
}