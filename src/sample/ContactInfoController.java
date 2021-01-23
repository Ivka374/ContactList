package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import sample.datamodel.Contact;

import java.io.File;

public class ContactInfoController {

    @FXML
    private Button editButton;

    @FXML
    private Button callButton;

    @FXML
    private Button closeButton;

    @FXML
    private ImageView contactImageView;

    @FXML
    private Label name;

    @FXML
    private Label number;

    @FXML
    private TextArea notesDisplay;

    //how do u get the info of the contact i need to display?
    public void initialize(){
       /* name.setText(contact.getFirstName() + " " + contact.getLastName());
        number.setText(contact.getPhoneNumber());
        contactImageView.setImage(contact.getContactImage());
        notesDisplay.setText(contact.getNotes());
        notesDisplay.setWrapText(true);*/
    }

    public void handleCloseButton(){

    }

    public void handleEditContact(){

    }
}
