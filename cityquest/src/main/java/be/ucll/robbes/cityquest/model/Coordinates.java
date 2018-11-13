package be.ucll.robbes.cityquest.model;

public class Coordinates {
    private double lat;
    private double lon;

    public Coordinates()
    {
        //why comment: for deserialization
    }

    public Coordinates(double lat, double lon) {
        setLat(lat);
        setLon(lon);
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
}
