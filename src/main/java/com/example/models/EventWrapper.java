package com.example.models;

import com.example.clientmodels.DeviceDTO;
import com.example.clientmodels.EventDTO;
import com.example.clientmodels.PositionDTO;

public class EventWrapper {
    private EventDTO event;
    private DeviceDTO device;
    private PositionDTO position;

    public EventDTO getEvent() {
        return event;
    }

    public void setEvent(EventDTO event) {
        this.event = event;
    }

    public DeviceDTO getDevice() {
        return device;
    }

    public void setDevice(DeviceDTO device) {
        this.device = device;
    }

    public PositionDTO getPosition() {
        return position;
    }

    public void setPosition(PositionDTO position) {
        this.position = position;
    }
}
