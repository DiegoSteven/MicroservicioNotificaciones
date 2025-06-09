package com.example.services.Notificators;
/* 
import org.springframework.stereotype.Component;

@Component
public class NotificatorSms implements Notificator {

    private final NotificationFormatter formatter;

    @Autowired
    public NotificatorSms(NotificationFormatter formatter) {
        this.formatter = formatter;
    }

    @Override
    public String getType() {
        return "sms";
    }

    @Override
    public void send(NotificationModel notification, User user, Event event, Position position) {
        if (user.getPhone() == null || user.getPhone().isBlank()) {
            System.err.println("Usuario sin número de teléfono. Notificación ignorada.");
            return;
        }

        NotificationMessage message = formatter.format(notification, user, event, position);

        // Simulación de envío SMS (reemplazá con integración real)
        System.out.println("[SMS enviado]");
        System.out.println("A: " + user.getPhone());
        System.out.println("Mensaje: " + message.getBody());
    }
}
    */