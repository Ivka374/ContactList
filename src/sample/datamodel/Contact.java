package sample.datamodel;

import javafx.scene.image.Image;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;

public class Contact {

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String notes;

    private boolean favourite;

    private Image contactImage;

    private String imageFileName;

    public Contact(String name, String number, String description) {
        String[] names = name.split(" ");
        firstName = names[0];
        lastName = name.length() > 1 ? names[1] : "";
        this.phoneNumber = number;
        this.notes = description;
        favourite = false;
        File imageFile = Paths.get("images", "defaultContactImage.jpg").toFile();
        contactImage = new Image(imageFile.toURI().toString());
        imageFileName = "defaultContactImage.jpg";
    }

    public Contact() {
    }    //not sure if I should keep it like this...

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public boolean isFavourite() {
        return favourite;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }

    public Image getContactImage() {
        return contactImage;
    }

    public void setContactImage(Image contactImage) {
        this.contactImage = contactImage;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }
}
