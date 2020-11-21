package sample;

public class Contact {
    private String firstName;
    private String lastName;
    private String number;
    private String description;

    public Contact(String firstName, String lastName, String number, String description) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.number = number;
        this.description = description;
    }

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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
