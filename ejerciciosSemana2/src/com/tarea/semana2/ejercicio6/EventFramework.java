package com.tarea.semana2.ejercicio6;

public class EventFramework {

    public static void main(String[] args) {
        EventBus bus = new EventBus();

        bus.subscribe(
                EventType.ORDER_PLACED,
                data -> System.out.println(
                        "  [LOG] " + data
                )
        );

        bus.subscribe(
                EventType.ORDER_SHIPPED,
                data -> System.out.println(
                        "  [LOG] " + data
                )
        );

        bus.subscribe(
                EventType.ORDER_CANCELLED,
                data -> System.out.println(
                        "  [ALERTA] " + data
                )
        );

        System.out.println("=== Ordenes con Email ===");

        OrderService service = new OrderService(
                bus,
                new EmailStrategy()
        );

        try {
            service.placeOrder("ORD-001", 299.99);
            service.shipOrder("ORD-001");
        } catch (OrderProcessingException e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println("\n=== Orden Invalida ===");

        try {
            service.placeOrder("ORD-002", -50.00);
        } catch (OrderProcessingException e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println("\n=== Cambiar a SMS Strategy ===");

        OrderService serviceSms = new OrderService(
                bus,
                new SmsStrategy()
        );

        try {
            serviceSms.placeOrder("ORD-003", 150.00);
            serviceSms.cancelOrder("ORD-003");
        } catch (OrderProcessingException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}