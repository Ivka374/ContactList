package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import sample.datamodel.Contact;
import sample.datamodel.ContactData;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class ContactInfoController {

    @FXML
    private Button editButton;

    @FXML
    private Button callButton;

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

    public void setContact(Contact contact){
        name.setText(contact.getFirstName() + " " + contact.getLastName());
        number.setText(contact.getPhoneNumber());
        contactImageView.setImage(contact.getContactImage());
        notesDisplay.setText(contact.getNotes());
        notesDisplay.setWrapText(true);
        favouriteLabel.setVisible(contact.isFavourite());
    }

    public void handleEditContact(){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Edit Contact");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("FXMLfiles/newContactDialog.fxml"));
        NewContactDialogController controller = fxmlLoader.getController();

        Contact contact = new Contact(this.name.getText(), this.number.getText(), this.notesDisplay.getText());
        contact.setContactImage(this.contactImageView.getImage());
        contact.setFavourite(this.favouriteLabel.isVisible());
        contact.setImageFileName(contact.getContactImage().getUrl());

        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
            if (controller != null) controller.handleEditMode(contact);

        }catch (IOException e) {
            System.out.println("Could not load the dialog");
            e.printStackTrace();
            return;
        }
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK){
            //ContactData.getInstance().removeContact(contact);
            //controller.handleFinishingCreation();
        }
    }

    public void handleCall(){

    }
}
