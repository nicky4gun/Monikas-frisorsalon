package dk.jannikognicklas.monikasfrisorsalon.models;

import dk.jannikognicklas.monikasfrisorsalon.models.enums.Status;
import dk.jannikognicklas.monikasfrisorsalon.models.enums.Treatment;

import java.time.LocalTime;

public class BookingView {
    private int id;
    private int customerId;
    private int treatmentId;
    private int employeeId;
    private LocalTime time;
    private String customerName;
    private String customerEmail;
    private int phoneNumber;
    private Treatment treatment;
    private String employeeName;
    private String note;
    private Status status;

    public BookingView(int id, int customerId, int treatmentId, int employeeId, LocalTime time, String customerName, String customerEmail, int phoneNumber, String treatment, String employeeName, String note, Status status) {
        this.id = id;
        this.customerId = customerId;
        this.treatmentId = treatmentId;
        this.employeeId = employeeId;
        this.time = time;
        this.customerName = customerName;
        this.treatment = Treatment.valueOf(treatment);
        this.employeeName = employeeName;
        this.customerEmail = customerEmail;
        this.phoneNumber = phoneNumber;
        this.note = note;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getTreatmentId() {
        return treatmentId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public LocalTime getTime() {
        return time;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerEmail() {return customerEmail;}

    public int getPhoneNumber() {return phoneNumber;}

    public Treatment getTreatment() {
        return treatment;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public String getNote() {
        return note;
    }

    public Status getStatus() {
        return status;
    }



}
