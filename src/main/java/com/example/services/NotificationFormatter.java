package com.example.services;

import org.springframework.stereotype.Service;

import com.example.models.NotificationModel;
import com.example.clientmodels.DeviceDTO;
import com.example.clientmodels.EventDTO;
import com.example.clientmodels.PositionDTO;
import com.example.clientmodels.UserDTO;
import com.example.models.NotificationMessage;

@Service
public class NotificationFormatter {

    public NotificationMessage format(NotificationModel notification, UserDTO user, EventDTO event, PositionDTO position) {
        StringBuilder body = new StringBuilder();

        body.append("Evento: ").append(event.getType());

        if (user.getEmail() != null) {
            body.append("\n👤 Usuario: ").append(user.getEmail());
        }

        if (position != null) {
            body.append("\nUbicación: ")
                .append(position.getLatitude())
                .append(", ")
                .append(position.getLongitude());
        }

        if (notification.getDescription() != null) {
            body.append("\nDescripción: ").append(notification.getDescription());
        }

        return new NotificationMessage("Notificación: " + event.getType(), body.toString());
    }

    // 🚀 Método adicional que te faltaba
    public static String formatMessage(EventDTO event, DeviceDTO device, PositionDTO position) {
        StringBuilder body = new StringBuilder();

        body.append("Evento: ").append(event.getType());

        if (device != null) {
            body.append("\nDispositivo: ").append(device.getName());
            body.append("\nUnique ID: ").append(device.getUniqueId());
        }

        if (position != null) {
            body.append("\nUbicación: ")
                .append(position.getLatitude())
                .append(", ")
                .append(position.getLongitude())
                .append("\nVelocidad: ").append(position.getSpeed()).append(" nudos");
        }

        body.append("\nFecha evento: ").append(event.getEventTime());
        return body.toString();
    }
}
