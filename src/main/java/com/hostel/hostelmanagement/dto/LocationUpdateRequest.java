package com.hostel.hostelmanagement.dto;

public class LocationUpdateRequest {
    private Double latitude;
    private Double longitude;
    private String zoneName;
    private Boolean permissionGranted;

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }

    public String getZoneName() { return zoneName; }
    public void setZoneName(String zoneName) { this.zoneName = zoneName; }

    public Boolean getPermissionGranted() { return permissionGranted; }
    public void setPermissionGranted(Boolean permissionGranted) { this.permissionGranted = permissionGranted; }
}
