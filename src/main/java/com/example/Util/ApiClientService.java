package com.example.Util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.clientmodels.DeviceDTO;
import com.example.clientmodels.EventDTO;
import com.example.clientmodels.PositionDTO;
import com.example.clientmodels.UserDTO;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class ApiClientService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private SessionUtil sessionUtil;

    private HttpHeaders buildHeadersWithSession(HttpServletRequest request) {
        String sessionCookie = sessionUtil.extractSessionCookie(request);

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.COOKIE, sessionCookie);
        headers.setAccept(MediaType.parseMediaTypes(MediaType.APPLICATION_JSON_VALUE));
        return headers;
    }

    public EventDTO getEventById(HttpServletRequest request, Long eventId) {
        HttpEntity<Void> entity = new HttpEntity<>(buildHeadersWithSession(request));

        ResponseEntity<EventDTO> response = restTemplate.exchange(
                "http://localhost:8080/api/events/" + eventId,
                HttpMethod.GET,
                entity,
                EventDTO.class
        );

        return response.getBody();
    }

    public PositionDTO[] getPositions(HttpServletRequest request) {
        HttpEntity<Void> entity = new HttpEntity<>(buildHeadersWithSession(request));

        ResponseEntity<PositionDTO[]> response = restTemplate.exchange(
                "http://localhost:8080/api/positions",
                HttpMethod.GET,
                entity,
                PositionDTO[].class
        );

        return response.getBody();
    }

    public UserDTO getUserById(HttpServletRequest request, Long userId) {
        HttpEntity<Void> entity = new HttpEntity<>(buildHeadersWithSession(request));

        ResponseEntity<UserDTO> response = restTemplate.exchange(
                "http://localhost:8080/api/users/" + userId,
                HttpMethod.GET,
                entity,
                UserDTO.class
        );

        return response.getBody();
    }

    public DeviceDTO getDeviceById(HttpServletRequest request, Long deviceId) {
        HttpEntity<Void> entity = new HttpEntity<>(buildHeadersWithSession(request));

        ResponseEntity<DeviceDTO> response = restTemplate.exchange(
                "http://localhost:8080/api/devices/" + deviceId,
                HttpMethod.GET,
                entity,
                DeviceDTO.class
        );

        return response.getBody();
    }
}