import java.awt.*;

public class Location {

    private String name;
    private Point coords;


    public Location(String name, Point coords) {
        this.name = name;
        this.coords = coords;

        //TODO
    }

    public String getName() {
        return name;
    }

    public Point getCoords() {
        return coords;
    }
}