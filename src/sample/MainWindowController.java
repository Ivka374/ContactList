package sample;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import sample.datamodel.Contact;
import sample.datamodel.ContactData;

import java.io.IOException;
import java.util.Comparator;
import java.util.Optional;
import java.util.function.Predicate;

public class MainWindowController {

    @FXML
    private TableView<Contact> tableOfContacts;

    @FXML
    private ToggleButton favouritesButton;

    @FXML
    private ToolBar mainToolBar;

    private FilteredList<Contact> filteredList;

    private Predicate<Contact> all;

    private Predicate<Contact> favourites;

    private final ObjectProperty<Contact> selectedContact = new SimpleObjectProperty<>();


    public void initialize() {
        ContextMenu listContextMenu = new ContextMenu();
        MenuItem deleteMenuItem = new MenuItem("Delete");
        deleteMenuItem.setOnAction(actionEvent -> deleteItem(selectedContact.getValue()));
        MenuItem viewMenuItem = new MenuItem("View");
        viewMenuItem.setOnAction(actionEvent -> viewItem(selectedContact.getValue()));
        MenuItem editMenuItem = new MenuItem("Edit");
        editMenuItem.setOnAction(actionEvent -> editItem(selectedContact.getValue()));

        listContextMenu.getItems().addAll(deleteMenuItem);
        listContextMenu.getItems().addAll(viewMenuItem);
        listContextMenu.getItems().addAll(editMenuItem);


        all = contact -> true;
        favourites = contact -> contact.isFavourite();

        filteredList = new FilteredList<>(ContactData.getInstance().getContacts(), all);
        SortedList<Contact> sortedList =
            new SortedList<>(filteredList, Comparator.comparing(Contact::getFirstName));

        TableColumn<Contact, String> firstName = new TableColumn<>("First Name");
        TableColumn<Contact, String> lastName = new TableColumn<>("Last Name");
        TableColumn<Contact, String> number = new TableColumn<>("Phone Number");
        TableColumn<Contact, String> notes = new TableColumn<>("Notes");

        selectedContact.bind(tableOfContacts.getSelectionModel().selectedItemProperty());
        tableOfContacts.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        tableOfContacts.getColumns().addAll(firstName, lastName, number, notes);

        firstName.setCellValueFactory(contactStringCellDataFeatures -> new SimpleStringProperty(contactStringCellDataFeatures.getValue().getFirstName()));
        setCellContents(firstName);
        lastName.setCellValueFactory(contactStringCellDataFeatures -> new SimpleStringProperty(contactStringCellDataFeatures.getValue().getLastName()));
        setCellContents(lastName);
        number.setCellValueFactory(contactStringCellDataFeatures -> new SimpleStringProperty(contactStringCellDataFeatures.getValue().getPhoneNumber()));
        setCellContents(number);
        notes.setCellValueFactory(contactStringCellDataFeatures -> new SimpleStringProperty(contactStringCellDataFeatures.getValue().getNotes()));
        setCellContents(notes);

        tableOfContacts.setItems(sortedList);
        tableOfContacts.setContextMenu(listContextMenu);

    }

    @FXML
    public void deleteItem(Contact item) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Contact");
        alert.setHeaderText("Delete contact: " + item.getFirstName() + " " + item.getLastName());
        alert.setContentText("Are you sure? Press OK to confirm, or cancel to back out");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && (result.get() == ButtonType.OK)) {
            ContactData.getInstance().removeContact(item);
        }
    }

    @FXML
    public void viewItem(Contact item) {

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Contact Details");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("FXMLfiles/contactInfoDialog.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
            ContactInfoController controller = fxmlLoader.getController();
            controller.setContact(item);

        } catch (IOException e) {
            System.out.println("Could not load the dialog");
            e.printStackTrace();
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        dialog.show();

    }

    @FXML
    public void editItem(Contact item){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainToolBar.getScene().getWindow());
        dialog.setTitle("Edit Contact");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("FXMLfiles/newContactDialog.fxml"));

        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
            dialog.setResizable(true);

            NewContactDialogController controller = fxmlLoader.getController();
            controller.handleEditMode(item);

            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

            Optional<ButtonType> result = dialog.showAndWait();


            if(result.isEmpty() || result.get() != ButtonType.OK){
                return;
            }
            controller.handleFinishingCreation();
            if (controller.handleFinishingCreation() != null) {
                ContactData.getInstance().removeContact(item);
            }

        }catch (IOException e) {
            System.out.println("Could not load the dialog");
            e.printStackTrace();
        }
    }

    @FXML
    public void showNewItemDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainToolBar.getScene().getWindow());
        dialog.setTitle("Add New Contact");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("FXMLfiles/newContactDialog.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println("Could not load the dialog");
            e.printStackTrace();
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();

        if (result.isEmpty() || result.get() != ButtonType.OK) {
            return;
        }

        NewContactDialogController controller = fxmlLoader.getController();
        Contact newContact = controller.handleFinishingCreation();
        if (newContact == null) {
            return;
        }

        tableOfContacts.getSelectionModel().select(newContact);
    }

    @FXML
    public void handleFilterButton() {
        if (favouritesButton.isSelected()) {
            filteredList.setPredicate(favourites);
        } else {
            filteredList.setPredicate(all);
        }
    }

    public void setCellContents(TableColumn<Contact, String> columnToSet){
        columnToSet.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(item);
                    Contact remakeContact = getTableView().getItems().get(getIndex());
                    if (remakeContact.isFavourite()) {
                        setTextFill(Color.DARKGOLDENROD);
                    } else {
                        setTextFill(Color.BLACK);
                    }
                }
            }
        });
    }
}
