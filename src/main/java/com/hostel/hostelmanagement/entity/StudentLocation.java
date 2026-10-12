package com.hostel.hostelmanagement.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "student_locations")
public class StudentLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User student;

    private Double latitude;
    private Double longitude;
    private String zoneName;
    private String status; // ON_CAMPUS, OFF_CAMPUS, OUTSTATION, PERMISSION_DENIED
    private Boolean permissionGranted = true;
    private LocalDateTime lastUpdated = LocalDateTime.now();

    public StudentLocation() {}

    public StudentLocation(User student, Double latitude, Double longitude, String zoneName, String status, Boolean permissionGranted) {
        this.student = student;
        this.latitude = latitude;
        this.longitude = longitude;
        this.zoneName = zoneName;
        this.status = status;
        this.permissionGranted = permissionGranted;
        this.lastUpdated = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getStudent() { return student; }
    public void setStudent(User student) { this.student = student; }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }

    public String getZoneName() { return zoneName; }
    public void setZoneName(String zoneName) { this.zoneName = zoneName; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Boolean getPermissionGranted() { return permissionGranted; }
    public void setPermissionGranted(Boolean permissionGranted) { this.permissionGranted = permissionGranted; }

    public LocalDateTime getLastUpdated() { return lastUpdated; }
    public void setLastUpdated(LocalDateTime lastUpdated) { this.lastUpdated = lastUpdated; }
}
