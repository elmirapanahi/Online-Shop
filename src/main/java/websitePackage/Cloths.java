package websitePackage;

import java.util.ArrayList;

public class Cloths extends Post{
    private boolean isNew;
    private boolean femaleOrMale;// female->ture    male->flase

    public Cloths(boolean isNew,boolean femaleOrMale,double price, String title, String region, String tag, String description, int clientID, ArrayList<String> photos) {
        super(price, title, region, tag, description, clientID, photos);
        this.isNew=isNew;
        this.femaleOrMale=femaleOrMale;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }

    public boolean isFemaleOrMale() {
        return femaleOrMale;
    }

    public void setFemaleOrMale(boolean femaleOrMale) {
        this.femaleOrMale = femaleOrMale;
    }
}
