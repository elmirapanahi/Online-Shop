package websitePackage;

import java.util.ArrayList;

public class Apartment extends Residential{
    private int floorNumber;
    private boolean hasElevator;
    private int numberOfAprsPerFloor;

    public Apartment(int area, boolean isForRent, double price, String title, String region, String tag, String description, int clientID, ArrayList<String> photos) {
        super(area, isForRent, price, title, region, tag, description, clientID, photos);
    }


    public int getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(int floorNumber) {
        this.floorNumber = floorNumber;
    }

    public boolean isHasElevator() {
        return hasElevator;
    }

    public void setHasElevator(boolean hasElevator) {
        this.hasElevator = hasElevator;
    }

    public int getNumberOfAprsPerFloor() {
        return numberOfAprsPerFloor;
    }

    public void setNumberOfAprsPerFloor(int numberOfAprsPerFloor) {
        this.numberOfAprsPerFloor = numberOfAprsPerFloor;
    }
}
