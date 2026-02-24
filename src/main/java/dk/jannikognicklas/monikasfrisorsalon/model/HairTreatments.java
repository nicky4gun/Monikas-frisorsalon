package dk.jannikognicklas.monikasfrisorsalon.model;

public class HairTreatments {
    private  int id;
    private String hairTreatmens;
    private  int duration  ;
    private  double price;

    public  HairTreatments(int id, String hairTreatmens, int duration, double price) {
        this.id = id;
        this.hairTreatmens = hairTreatmens;
        this.duration = duration;
        this.price = price;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHairTreatmens() {
        return hairTreatmens;
    }

    public void setHairTreatmens(String hairTreatmens) {
        this.hairTreatmens = hairTreatmens;
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
