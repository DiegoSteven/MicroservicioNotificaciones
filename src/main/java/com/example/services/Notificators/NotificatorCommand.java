package com.example.services.Notificators;

import org.springframework.stereotype.Component;

import com.example.clientmodels.EventDTO;
import com.example.clientmodels.PositionDTO;
import com.example.clientmodels.UserDTO;
import com.example.models.*;
@Component
public class NotificatorCommand implements Notificator {

    @Override
    public String getType() {
        return "command";
    }
@Override
    public void send(NotificationModel notification, UserDTO user, EventDTO event, PositionDTO position) {
        if (notification.getCommandId() == null || notification.getCommandId() <= 0) {
            System.err.println("❌ commandId no definido. No se ejecuta ningún comando.");
            return;
        }

        // Simulación de ejecución de comando
        System.out.println("⚙️ Ejecutando comando con ID: " + notification.getCommandId() +
                " para el dispositivo ID: " + event.getDeviceId());

        // Aquí podrías integrar un CommandService que envíe el comando real.
        // Por ejemplo: commandService.sendCommand(notification.getCommandId(), event.getDeviceId());
    }
}