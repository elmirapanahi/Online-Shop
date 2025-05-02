package websitePackage;

import java.util.ArrayList;

public class Land extends Property{
    private boolean hasPermissionForConstruction;

    public Land(int area, boolean isForRent, double price, String title, String region, String tag, String description, int clientID, ArrayList<String> photos) {
        super(area, isForRent, price, title, region, tag, description, clientID, photos);
    }


    public boolean isHasPermissionForConstruction() {
        return hasPermissionForConstruction;
    }

    public void setHasPermissionForConstruction(boolean hasPermissionForConstruction) {
        this.hasPermissionForConstruction = hasPermissionForConstruction;
    }
}
