package com.example.clientmodels;

public final class EventTypes {

    private EventTypes() {
    }

    public static final String COMMAND_RESULT = "commandResult";
    public static final String DEVICE_ONLINE = "deviceOnline";
    public static final String DEVICE_UNKNOWN = "deviceUnknown";
    public static final String DEVICE_OFFLINE = "deviceOffline";
    public static final String DEVICE_INACTIVE = "deviceInactive";
    public static final String QUEUED_COMMAND_SENT = "queuedCommandSent";

    public static final String DEVICE_MOVING = "deviceMoving";
    public static final String DEVICE_STOPPED = "deviceStopped";

    public static final String DEVICE_OVERSPEED = "deviceOverspeed";
    public static final String DEVICE_FUEL_DROP = "deviceFuelDrop";
    public static final String DEVICE_FUEL_INCREASE = "deviceFuelIncrease";

    public static final String GEOFENCE_ENTER = "geofenceEnter";
    public static final String GEOFENCE_EXIT = "geofenceExit";

    public static final String ALARM = "alarm";
    public static final String IGNITION_ON = "ignitionOn";
    public static final String IGNITION_OFF = "ignitionOff";

    public static final String MAINTENANCE = "maintenance";
    public static final String TEXT_MESSAGE = "textMessage";
    public static final String DRIVER_CHANGED = "driverChanged";
    public static final String MEDIA = "media";
}