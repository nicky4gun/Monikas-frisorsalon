package dk.jannikognicklas.monikasfrisorsalon.models;

public class Customer {
    private  int id;
    private String name;
    private String email;
    private int phoneNumber;

    public Customer(int id, String name, String email, int phoneNumber) {
     this.id = id;
     this.name = name;
     this.email = email;
     this.phoneNumber = phoneNumber;

    }
    public Customer(String name, String email,int phoneNumber) {
        this.name = name;
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
        return name;
    }

    public void setName(String name) {
        name = name;
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

