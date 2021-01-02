package sample;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import sample.datamodel.Contact;
import sample.datamodel.ContactData;

import java.io.File;

public class NewContactDialogController {

    @FXML
    private TextField nameInputField;

    @FXML
    private TextField numberInputField;

    @FXML
    private ImageView contactImagePreview;

    @FXML
    private ToggleButton isFavouriteButton;

    @FXML
    private TextArea descriptionInputField;

    public Contact handleFinishingCreation(){
        if (!nameInputField.equals("") && !numberInputField.equals("") && !descriptionInputField.equals("")){

            Contact newContact = new Contact(nameInputField.getText(), numberInputField.getText(), descriptionInputField.getText());
            if (isFavouriteButton.isSelected())newContact.setFavourite(true);
            if (contactImagePreview != null)newContact.setContactImage(contactImagePreview.getImage());

            ContactData.getInstance().addContact(newContact);

            return newContact;

        } else {
            Alert blankSpaces = new Alert(Alert.AlertType.WARNING);
            blankSpaces.setTitle("Incomplete Contact Properties");
            blankSpaces.setHeaderText("Please fill in the areas fo name, number and notes!");
            blankSpaces.show();

            return null;
        }
    }

    public void handleChangingImage(){
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);

        File file = fileChooser.showOpenDialog(null);
        if (file != null){
            Image contactImage = new Image(file.toURI().toString());
            contactImagePreview.setFitHeight(80);
            contactImagePreview.setFitWidth(80);
            contactImagePreview.setImage(contactImage);
        }
    }

}
