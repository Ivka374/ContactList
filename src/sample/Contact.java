package sample;

public class Contact {
    private String name;
    private String number;
    private String description;

    public Contact(String name, String number, String description) {
        this.name = name;
        this.number = number;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
