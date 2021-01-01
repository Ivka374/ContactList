package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import sample.datamodel.Contact;
import sample.datamodel.ContactData;

public class NewContactDialogController {

    @FXML
    private TextField nameInputField;

    @FXML
    private TextField numberInputField;

    @FXML
    private Button changePictureButton;

    @FXML
    private Button createButton;

    @FXML
    private Button cancelButton;

    @FXML
    private ImageView contactImagePreview;

    @FXML
    private TextArea descriptionInputField;

    public Contact handleFinishingCreation(){
        if (!nameInputField.equals("") && !numberInputField.equals("") && !descriptionInputField.equals("")){

            Contact newContact = new Contact(nameInputField.getText(), numberInputField.getText(), descriptionInputField.getText());
            ContactData.getInstance().addContact(newContact);
            //ContactData.getInstance().saveContacts();

            return newContact;
        } else {
            return null;
        }
    }

}
