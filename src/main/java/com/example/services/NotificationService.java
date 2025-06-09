package com.example.services;

import com.example.Dto.Typed;
import com.example.models.NotificationMessage;
import com.example.models.NotificationModel;
import com.example.repositories.NotificationRepository;
import com.example.services.Notificators.MailService;
import com.example.Util.ApiClientService;
import com.example.clientmodels.*;

import com.example.websocket.NotificationWebSocketHandler;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private NotificatorManager notificatorManager;

    @Autowired
    private ApiClientService apiClientService;

    @Autowired
    private MailService mailService;

    @PersistenceContext
    private EntityManager entityManager;

    public List<NotificationModel> getAll() {
        return notificationRepository.findAll();
    }

    @Transactional
    public NotificationModel create(NotificationModel notification, Long userId) {
        if (notification.getCalendarId() != null && notification.getCalendarId() == 0)
            notification.setCalendarId(null);
        if (notification.getCommandId() != null && notification.getCommandId() == 0)
            notification.setCommandId(null);

        NotificationModel saved = notificationRepository.save(notification);

        entityManager.createNativeQuery("""
            INSERT INTO tc_user_notification (userid, notificationid)
            VALUES (:userId, :notificationId)
        """)
            .setParameter("userId", userId)
            .setParameter("notificationId", saved.getId())
            .executeUpdate();

        return saved;
    }
//Actualizar
   @Transactional
    public NotificationModel update(Long id, NotificationModel updated, Long userId) {
             if (notificationRepository.isLinkedToUser(userId, id) != 1) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No autorizado");
        }
      

        return notificationRepository.findById(id).map(existing -> {
            existing.setDescription(updated.getDescription());
            existing.setType(updated.getType());
            existing.setCommandId(updated.getCommandId() == 0 ? null : updated.getCommandId());
            existing.setCalendarId(updated.getCalendarId() == 0 ? null : updated.getCalendarId());
            existing.setAlways(updated.isAlways());
            existing.setNotificators(updated.getNotificators());
            return notificationRepository.save(existing);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Notificación no encontrada"));
    }

    //Rliminar
    @Transactional
    public void delete(Long notificationId, Long userId) {
        entityManager.createNativeQuery("""
            DELETE FROM tc_user_notification
            WHERE userid = :userId AND notificationid = :notificationId
        """)
            .setParameter("userId", userId)
            .setParameter("notificationId", notificationId)
            .executeUpdate();

        notificationRepository.deleteById(notificationId);
    }

    public void sendTest(HttpServletRequest request, Long userId) {
        UserDTO user = apiClientService.getUserById(request, userId);

        EventDTO testEvent = new EventDTO();
        testEvent.setType("test");

        NotificationModel notification = new NotificationModel();
        notification.setType("test");

        notificatorManager.getAllTypes().forEach(type -> {
            notificatorManager.get(type).send(notification, user, testEvent, null);
        });
    }
    public void sendTestByType(HttpServletRequest request, String type, Long userId) {
        UserDTO user = apiClientService.getUserById(request, userId);  // Necesita la cookie para hacer la petición
    
        EventDTO testEvent = new EventDTO();
        testEvent.setType("test");
    
        NotificationModel notification = new NotificationModel();
        notification.setType("test");
    
        notificatorManager.get(type).send(notification, user, testEvent, null);
    }
    


    public void sendMessage(HttpServletRequest request, String notificator, List<Long> userIds, NotificationMessage message) {
        if (userIds == null || userIds.isEmpty()) {
            throw new RuntimeException("Se requiere al menos un ID de usuario.");
        }

        for (Long userId : userIds) {
            UserDTO user = apiClientService.getUserById(request, userId);
            notificatorManager.get(notificator).send(user, message, null, null);
        }
    }

    public void processEvent(HttpServletRequest request, EventDTO event, DeviceDTO device, PositionDTO position) {
        List<NotificationModel> notifications = notificationRepository.findByTypeAndAlwaysTrue(event.getType());

        for (NotificationModel notification : notifications) {
            String message = NotificationFormatter.formatMessage(event, device, position);

            if (notification.getNotificators() != null && notification.getNotificators().contains("mail")) {
                String correoDestino = "acrdkr@gmail.com"; // Puedes reemplazar por user.getEmail()
                mailService.send(correoDestino, "Evento: " + event.getType(), message);
            }

            if (notification.getNotificators() != null && notification.getNotificators().contains("web")) {
                try {
                    ObjectMapper mapper = new ObjectMapper();
                    String json = mapper.writeValueAsString(Map.of("message", message));
                    NotificationWebSocketHandler.broadcast(json);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public List<Typed> getEventTypes() {
        List<Typed> types = new LinkedList<>();
        Field[] fields = com.example.clientmodels.EventTypes.class.getDeclaredFields();

        for (Field field : fields) {
            if (Modifier.isStatic(field.getModifiers())) {
                try {
                    types.add(new Typed((String) field.get(null)));
                } catch (IllegalAccessException ignored) {}
            }
        }
        return types;
    }

    public List<Typed> getNotificators(boolean filterAnnouncement) {
        Set<String> announcementsUnsupported = Set.of("command", "web", "mail");

        return notificatorManager.getAllTypes().stream()
            .filter(type -> !filterAnnouncement || !announcementsUnsupported.contains(type))
            .map(Typed::new)
            .collect(Collectors.toList());
    }
}
