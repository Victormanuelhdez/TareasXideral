package com.tarea.semana2.ejercicio6;

public class SmsStrategy implements NotificationStrategy {

    @Override
    public void send(String message) {
        System.out.println("[SMS] Enviando: " + message);
    }
}