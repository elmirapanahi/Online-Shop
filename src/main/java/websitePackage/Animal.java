package websitePackage;

import java.util.ArrayList;

public class Animal extends Post{
    private int age;
    private boolean gender;// female->true  male->false
    private boolean isHealthy;

    public Animal(boolean isHealthy ,double price, String title, String region, String tag, String description, int clientID, ArrayList<String> photos) {
        super(price, title, region, tag, description, clientID, photos);
        this.isHealthy=isHealthy;
    }


    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public boolean isHealthy() {
        return isHealthy;
    }

    public void setHealthy(boolean healthy) {
        isHealthy = healthy;
    }
}
