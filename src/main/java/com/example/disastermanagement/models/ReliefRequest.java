package com.example.disastermanagement.models;

import jakarta.persistence.*;

@Entity
@Table(name = "relief_requests")
public class ReliefRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "requester_name", nullable = false)
    private String requesterName;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "details", nullable = false, columnDefinition = "TEXT")
    private String details;

    @Enumerated(EnumType.STRING) // Save the enum value as a string in the database
    @Column(name = "status", nullable = false)
    private ReliefRequestStatus status;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRequesterName() {
        return requesterName;
    }

    public void setRequesterName(String requesterName) {
        this.requesterName = requesterName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public ReliefRequestStatus getStatus() {
        return status;
    }

    public void setStatus(ReliefRequestStatus status) {
        this.status = status;
    }
}