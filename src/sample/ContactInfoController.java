package sample;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import sample.datamodel.Contact;

public class ContactInfoController {

    @FXML
    private ImageView contactImageView;

    @FXML
    private Label name;

    @FXML
    private Label number;

    @FXML
    private TextArea notesDisplay;

    @FXML
    private Label favouriteLabel;

    private final ObjectProperty<Contact> contactViewed = new SimpleObjectProperty();

    public void setContact(Contact contact){
        contactViewed.setValue(contact);
        name.setText(contact.getFirstName() + " " + contact.getLastName());
        number.setText(contact.getPhoneNumber());
        contactImageView.setImage(contact.getContactImage());
        notesDisplay.setText(contact.getNotes());
        notesDisplay.setWrapText(true);
        favouriteLabel.setVisible(contact.isFavourite());
    }

    public void handleCall(){
        Alert call = new Alert(Alert.AlertType.INFORMATION);
        call.setTitle("Call " + contactViewed.getValue().getPhoneNumber());
        call.setHeaderText("You are trying to call " + contactViewed.getValue().getFirstName() + " " +
                contactViewed.getValue().getLastName());
        call.setContentText(contactViewed.getValue().getNotes());
        call.show();

    }
}
