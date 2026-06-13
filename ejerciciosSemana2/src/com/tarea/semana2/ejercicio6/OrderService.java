package com.tarea.semana2.ejercicio6;

public class OrderService {

    private final EventBus eventBus;
    private final NotificationStrategy notifier;

    public OrderService(
            EventBus eventBus,
            NotificationStrategy notifier
    ) {
        this.eventBus = eventBus;
        this.notifier = notifier;
    }

    public void placeOrder(
            String orderId,
            double amount
    ) throws OrderProcessingException {

        if (amount <= 0) {
            throw new OrderProcessingException(
                    "Monto invalido para orden "
                            + orderId
                            + ": $"
                            + amount
            );
        }

        String data = String.format(
                "Orden %s por $%.2f",
                orderId,
                amount
        );

        eventBus.publish(
                EventType.ORDER_PLACED,
                data
        );

        notifier.send("Nueva orden: " + data);
    }

    public void shipOrder(String orderId) {
        eventBus.publish(
                EventType.ORDER_SHIPPED,
                "Orden " + orderId + " enviada"
        );
    }

    public void cancelOrder(String orderId) {
        eventBus.publish(
                EventType.ORDER_CANCELLED,
                "Orden " + orderId + " cancelada"
        );
    }
}