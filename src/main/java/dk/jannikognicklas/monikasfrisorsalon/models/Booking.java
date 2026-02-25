package dk.jannikognicklas.monikasfrisorsalon.models;

import dk.jannikognicklas.monikasfrisorsalon.models.enums.Status;

import java.time.LocalDateTime;

public class Booking {
    private int id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int employeeId;
    private int customerId;
    private int hairTreatmentId;
    private Status status;

    public Booking(LocalDateTime startTime, LocalDateTime endTime, int employeeId, int customerId, int hairTreatmentId, Status status) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.employeeId = employeeId;
        this.customerId = customerId;
        this.hairTreatmentId = hairTreatmentId;
        this.status = status;
    }

    public Booking(int id, LocalDateTime startTime, LocalDateTime endTime, int employeeId, int customerId, int hairTreatmentId , Status status) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.employeeId = employeeId;
        this.customerId = customerId;
        this.hairTreatmentId = hairTreatmentId;
        this.status = status;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getHairTreatmentId() {
        return hairTreatmentId;
    }

    public void setHairTreatmentId(int hairTreatmentId) {
        this.hairTreatmentId = hairTreatmentId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}




