package websitePackage;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.Timestamp;
import java.util.ArrayList;

public class Post {
    private int postID;
    private String adminNote;
    private double price;
    private String title;
    private String region;
    private String tag;
    private int numberOfViews;
    private boolean isActive;
    private Timestamp insertionTime;
    private String description;
    private ArrayList<String> photos;//URL if images
    private int clientID;
    private int businessID=2;

    public Post(double price, String title,String region, String tag,String description,int clientID,ArrayList<String> photos){
        this.photos=new ArrayList<>();
        this.price = price;
        this.title = title;
        this.region = region;
        this.tag = tag;
        this.isActive = false;
        this.description = description;
        this.photos = photos;
        this.clientID = clientID;
        businessID=2;
    }

    public int getPostID() {
        return postID;
    }

    public void setPostID(int postID) {
        this.postID = postID;
    }

    public String getAdminNote() {
        return adminNote;
    }

    public void setAdminNote(String adminNote) {
        this.adminNote = adminNote;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getNumberOfViews() {
        return numberOfViews;
    }

    public void setNumberOfViews(int numberOfViews) {
        this.numberOfViews = numberOfViews;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Timestamp getInsertionTime() {
        return insertionTime;
    }

    public void setInsertionTime(Timestamp insertionTime) {
        this.insertionTime = insertionTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<String> getPhotos() {
        return photos;
    }

    public void setPhotos(ArrayList<String> photos) {
        this.photos = photos;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public int getBusinessID() {
        return businessID;
    }

    public void setBusinessID(int businessID) {
        this.businessID = businessID;
    }


    public static ArrayList<String> jsonToPhotoUrls(String json){
        ArrayList<String> photoUrls=new ArrayList<>();
        try{
            ObjectMapper mapper=new ObjectMapper();
            photoUrls=mapper.readValue(json, new TypeReference<ArrayList<String>>() {});
        }catch (Exception e){
            e.printStackTrace();
        }
        return photoUrls;
    }
}
