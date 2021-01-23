package sample;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import sample.datamodel.Contact;
import sample.datamodel.ContactData;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

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
        try {
            Contact newContact = new Contact(nameInputField.getText(), numberInputField.getText(), descriptionInputField.getText());
            if (nameInputField.getText().matches("\\w+\\s+")) throw new Exception();
            if (isFavouriteButton.isSelected()) newContact.setFavourite(true);
            if (contactImagePreview != null) newContact.setContactImage(contactImagePreview.getImage());
            try {
                File image = File.createTempFile("image-", "-clipj.png", new File(System.getProperty("user.dir") + "\\src\\images"));
                newContact.setImageFileName(image.getName());
                String format = "PNG";
                ImageIO.write(SwingFXUtils.fromFXImage(contactImagePreview.getImage(), null), format, image);
            } catch (IOException e) {
                e.printStackTrace();
            }

            ContactData.getInstance().addContact(newContact);

            return newContact;

        }catch (Exception e) {

            Alert blankSpaces = new Alert(Alert.AlertType.ERROR);
            blankSpaces.setTitle("Incomplete Contact Properties");
            blankSpaces.setHeaderText("Please fill in the areas for name, number and notes!");
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
            contactImagePreview.setFitHeight(100);
            contactImagePreview.setFitWidth(100);
            contactImagePreview.setImage(contactImage);
        }
    }

}
