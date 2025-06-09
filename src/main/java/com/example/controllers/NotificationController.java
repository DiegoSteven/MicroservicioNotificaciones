package com.example.controllers;

import com.example.models.NotificationMessage;
import com.example.Dto.Typed;
import com.example.Util.SessionUtil;
import com.example.models.NotificationModel;
import com.example.repositories.NotificationRepository;
import com.example.services.NotificationService;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin(origins = "*")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private SessionUtil sessionUtil;

    @GetMapping
    public List<NotificationModel> getAll(HttpServletRequest request) {
        Long userId = sessionUtil.extractUserIdFromSession(request);
        return notificationRepository.findByUserId(userId);
    }

    @PostMapping
    public NotificationModel create(@RequestBody NotificationModel notification, HttpServletRequest request) {
        Long userId = sessionUtil.extractUserIdFromSession(request);
        return notificationService.create(notification, userId);
    }

    @GetMapping("/{id}")
    public NotificationModel getById(@PathVariable Long id, HttpServletRequest request) {
        Long userId = sessionUtil.extractUserIdFromSession(request);

        if (notificationRepository.isLinkedToUser(userId, id) != 1) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No autorizado");
        }

        return notificationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Notificación no encontrada"));
    }

    // Update
    @PutMapping("/{id}")
    public NotificationModel update(
            @PathVariable Long id,
            @RequestBody NotificationModel updated,
            HttpServletRequest request) {

        Long userId = sessionUtil.extractUserIdFromSession(request);

        return notificationService.update(id, updated, userId);
    }
    // Eliminar

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, HttpServletRequest request) {
        try {
            Long userId = sessionUtil.extractUserIdFromSession(request);
            notificationService.delete(id, userId);
            return ResponseEntity.noContent().build();
        } catch (HttpClientErrorException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Sesión inválida");
        }
    }

    @GetMapping("/types")
    public List<Typed> getTypes() {
        return notificationService.getEventTypes();
    }

    @GetMapping("/notificators")
    public List<Typed> getNotificators(@RequestParam(defaultValue = "false") boolean announcement) {
        return notificationService.getNotificators(announcement);
    }



    @PostMapping("/test")
    public ResponseEntity<Void> sendTest(HttpServletRequest request) {
        try {
            Long userId = sessionUtil.extractUserIdFromSession(request);
            notificationService.sendTest(request,userId);
            return ResponseEntity.noContent().build();
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/test/{notificator}")
    public ResponseEntity<Void> sendTestByType(@PathVariable String notificator, HttpServletRequest request) {
        try {
            Long userId = sessionUtil.extractUserIdFromSession(request);
            notificationService.sendTestByType(request, notificator, userId);
            return ResponseEntity.noContent().build();
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    

    @PostMapping("/send/{notificator}")
    public ResponseEntity<Void> sendMessageToUsers(
            @PathVariable String notificator,
            @RequestParam(required = false) List<Long> userId,
            @RequestBody NotificationMessage message,
            HttpServletRequest request) {
        try {
            Long currentUserId = sessionUtil.extractUserIdFromSession(request);
            notificationService.sendMessage(request, notificator, userId, message);
            return ResponseEntity.noContent().build();
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

}

