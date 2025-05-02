package websitePackage;

import java.util.ArrayList;

public class PersonalAndHomeStuff extends Post{
    private boolean isNew;

    public PersonalAndHomeStuff(boolean isNew,double price, String title, String region, String tag, String description, int clientID, ArrayList<String> photos) {
        super(price, title, region, tag, description, clientID, photos);
        this.isNew = isNew;
    }


    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }
}
