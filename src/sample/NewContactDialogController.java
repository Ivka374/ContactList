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
import java.nio.file.Paths;

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

    public Contact handleFinishingCreation() {
        try {
            String name = nameInputField.getText().trim();
            if (!name.matches("\\w+\\s\\w+") || !numberInputField.getText().matches("\\d+")) {
                throw new Exception();
            }

            Contact newContact =
                new Contact(name, numberInputField.getText(), descriptionInputField.getText());

            if (isFavouriteButton.isSelected()) {
                newContact.setFavourite(true);
            }

            if (contactImagePreview.getImage() != null) {
                newContact.setContactImage(contactImagePreview.getImage());

                if (!contactImagePreview.getImage().getUrl().endsWith("defaultContactImage.jpg")) {
                    try {
                        File image = File.createTempFile("image-", "-clipj.png", Paths.get("images").toFile());
                        newContact.setImageFileName(image.getName());
                        String format = "PNG";
                        ImageIO.write(SwingFXUtils.fromFXImage(contactImagePreview.getImage(), null), format,
                                image);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    newContact.setImageFileName("defaultContactImage.jpg");
                }

            }

            ContactData.getInstance().addContact(newContact);

            return newContact;

        } catch (Exception e) {

            Alert blankSpaces = new Alert(Alert.AlertType.ERROR);
            blankSpaces.setTitle("Incomplete Contact Properties");
            blankSpaces.setHeaderText("Please fill in the areas for name, number and notes!");
            blankSpaces.show();

            return null;
        }
    }

    public void handleChangingImage() {
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilterJPG =
            new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterPNG =
            new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);

        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            Image contactImage = new Image(file.toURI().toString());
            contactImagePreview.setFitHeight(250);
            contactImagePreview.setFitWidth(250);
            contactImagePreview.setImage(contactImage);
        }
    }

    public void handleEditMode(Contact item) {
        nameInputField.setText(item.getFirstName() + " " + item.getLastName());
        numberInputField.setText(item.getPhoneNumber());
        descriptionInputField.setText(item.getNotes());
        descriptionInputField.setWrapText(true);
        contactImagePreview.setImage(item.getContactImage());
        contactImagePreview.setFitWidth(250);
        contactImagePreview.setFitHeight(250);
        isFavouriteButton.setSelected(item.isFavourite());
    }

}
