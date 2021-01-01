package sample.datamodel;

public class Contact {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String notes;
    private boolean favourite;

    public Contact(String name, String number, String description) {
        String[] names = name.split(" ");
        firstName = names[0];
        lastName = names[1];
        this.phoneNumber = number;
        this.notes = description;
        favourite = false;
    }

    public Contact(){}    //not sure if I should keep it like this...

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
}
