package dk.jannikognicklas.monikasfrisorsalon.models;

import dk.jannikognicklas.monikasfrisorsalon.models.enums.Status;

import java.time.LocalDate;
import java.time.LocalTime;

public class Booking {
    private int id;
    private LocalDate date;
    private LocalTime time;
    private int employeeId;
    private int customerId;
    private int hairTreatmentId;
    private Status status;
    private String note;

    public Booking(LocalDate date, LocalTime time, int employeeId, int customerId, int hairTreatmentId, Status status, String note) {
        this.date = date;
        this.time = time;
        this.employeeId = employeeId;
        this.customerId = customerId;
        this.hairTreatmentId = hairTreatmentId;
        this.status = status;
        this.note = note;
    }

    public Booking(int id, LocalDate date, LocalTime time, int employeeId, int customerId, int hairTreatmentId, Status status,String note) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.employeeId = employeeId;
        this.customerId = customerId;
        this.hairTreatmentId = hairTreatmentId;
        this.status = status;
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
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

    public String getNote() {return note;}
    public void setNote(String note) {this.note = note;}
}




