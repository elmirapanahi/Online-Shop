package websitePackage;

import java.util.ArrayList;

public class DigitalDevice extends Post{
    private String brand;
    private boolean isNew;
    private String color;

    public DigitalDevice(boolean isNew,double price, String title, String region, String tag, String description, int clientID, ArrayList<String> photos) {
        super(price, title, region, tag, description, clientID, photos);
        this.isNew = isNew;
    }


    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
