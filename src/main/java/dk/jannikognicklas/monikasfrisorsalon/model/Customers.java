package dk.jannikognicklas.monikasfrisorsalon.model;

public class Customers {
 private  int id;
 private String Name;
 private String email;
 private int phoneNumber;

 public Customers(int id, String Name, String email, int phoneNumber) {
     this.id = id;
     this.Name = Name;
     this.email = email;
     this.phoneNumber = phoneNumber;
 }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}

