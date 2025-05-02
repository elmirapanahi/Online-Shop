package websitePackage;

import java.util.ArrayList;

public class Commercial extends Property{
    private int builtYear;
    private int numberOfRooms;

    public Commercial(int area, boolean isForRent, double price, String title, String region, String tag, String description, int clientID, ArrayList<String> photos) {
        super(area, isForRent, price, title, region, tag, description, clientID, photos);
    }

    public int getBuiltYear() {
        return builtYear;
    }

    public void setBuiltYear(int builtYear) {
        this.builtYear = builtYear;
    }

    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(int numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }
}
