package dk.jannikognicklas.monikasfrisorsalon.models;

public class HairTreatment {
    private  int id;
    private String hairTreatment;
    private  int duration  ;
    private  double price;

    public HairTreatment(int id, String hairTreatment, int duration, double price) {
        this.id = id;
        this.hairTreatment = hairTreatment;
        this.duration = duration;
        this.price = price;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHairTreatment() {
        return hairTreatment;
    }

    public void setHairTreatment(String hairTreatment) {
        this.hairTreatment = hairTreatment;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
