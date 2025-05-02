package websitePackage;

import java.util.ArrayList;

public class Property extends Post{
    private int area=-1;
    private boolean isForRent;
    private double deposit=-1;
    private double monthlyRent=-1;

    public Property(int area,boolean isForRent,double price, String title, String region, String tag, String description, int clientID, ArrayList<String> photos) {
        super(price, title, region, tag, description, clientID, photos);
        this.isForRent=isForRent;
        this.area = area;
    }


    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public boolean isForRent() {
        return isForRent;
    }

    public void setForRent(boolean forRent) {
        isForRent = forRent;
    }

    public double getDeposit() {
        return deposit;
    }

    public void setDeposit(double deposit) {
        this.deposit = deposit;
    }

    public double getMonthlyRent() {
        return monthlyRent;
    }

    public void setMonthlyRent(double monthlyRent) {
        this.monthlyRent = monthlyRent;
    }
}
