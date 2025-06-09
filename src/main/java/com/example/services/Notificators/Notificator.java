package com.example.services.Notificators;

import com.example.clientmodels.EventDTO;
import com.example.clientmodels.PositionDTO;
import com.example.clientmodels.UserDTO;
import com.example.models.*;
public interface Notificator {

    String getType();  // ej: "web", "mail", "telegram"

     void send(NotificationModel notification, UserDTO user, EventDTO event, PositionDTO position);

     default void send(UserDTO user, NotificationMessage message, EventDTO event, PositionDTO position){
        throw new UnsupportedOperationException("Método no implementado en este notificator");
    }
}