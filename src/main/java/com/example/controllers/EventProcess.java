package com.example.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.models.EventWrapper;
import com.example.services.NotificationService;

@RestController
@RequestMapping("/api/notifications/events")
public class EventProcess {

    @Autowired
    private NotificationService notificationService;

    @PostMapping
    public ResponseEntity<String> handleEvent(@RequestBody String rawJson, HttpServletRequest request) {
        System.out.println("📩 JSON recibido de Traccar:");
        System.out.println(rawJson);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            EventWrapper wrapper = objectMapper.readValue(rawJson, EventWrapper.class);

            notificationService.processEvent(request, wrapper.getEvent(), wrapper.getDevice(), wrapper.getPosition());
            System.out.println("✅ EventProcess Successfully");
            return ResponseEntity.ok("Evento procesado correctamente");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("❌ Error al procesar el JSON");
        }
    }
}
