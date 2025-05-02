package websitePackage;

import java.util.ArrayList;

public class House extends Residential{
    private int numberOfFloors;
    private int areaPerFloor;

    public House(int area, boolean isForRent, double price, String title, String region, String tag, String description, int clientID, ArrayList<String> photos) {
        super(area, isForRent, price, title, region, tag, description, clientID, photos);
    }


    public int getNumberOfFloors() {
        return numberOfFloors;
    }

    public void setNumberOfFloors(int numberOfFloors) {
        this.numberOfFloors = numberOfFloors;
    }

    public int getAreaPerFloor() {
        return areaPerFloor;
    }

    public void setAreaPerFloor(int areaPerFloor) {
        this.areaPerFloor = areaPerFloor;
    }
}
