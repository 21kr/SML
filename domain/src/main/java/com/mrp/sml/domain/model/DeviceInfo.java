package com.mrp.sml.domain.model;

public class DeviceInfo {
    private final String deviceName;
    private final String deviceId;
    private final String version;

    public DeviceInfo(String deviceName, String deviceId, String version) {
        this.deviceName = deviceName;
        this.deviceId = deviceId;
        this.version = version;
    }

    public String getDeviceName() { return deviceName; }
    public String getDeviceId() { return deviceId; }
    public String getVersion() { return version; }

    public String toJson() {
        return "{\"deviceName\":\"" + escape(deviceName) + "\","
                + "\"deviceId\":\"" + escape(deviceId) + "\","
                + "\"version\":\"" + escape(version) + "\"}";
    }

    public static DeviceInfo fromJson(String json) {
        String name = extract(json, "deviceName");
        String id = extract(json, "deviceId");
        String ver = extract(json, "version");
        return new DeviceInfo(name, id, ver);
    }

    private static String extract(String json, String key) {
        String search = "\"" + key + "\":\"";
        int start = json.indexOf(search);
        if (start < 0) return "";
        start += search.length();
        int end = json.indexOf("\"", start);
        return end < 0 ? "" : json.substring(start, end);
    }

    private static String escape(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
