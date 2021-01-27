package sample;

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import sample.datamodel.Contact;
import sample.datamodel.ContactData;

import java.io.IOException;
import java.util.Comparator;
import java.util.Optional;
import java.util.function.Predicate;

public class MainWindowController {

    //private List<Contact> contactList;

    @FXML
    private ListView<Contact> firstNameListView;

    @FXML
    private ListView<Contact> lastNameListView;

    @FXML
    private ListView<Contact> numberListView;

    @FXML
    private ListView<Contact> notesListView;

    @FXML
    private ToggleButton favouritesButton;

    @FXML
    private ContextMenu listContextMenu;

    @FXML
    private ToolBar mainToolBar;

    private FilteredList<Contact> filteredList;
    private Predicate<Contact> all;
    private Predicate<Contact> favourites;

    public void initialize(){
        listContextMenu = new ContextMenu();
        MenuItem deleteMenuItem = new MenuItem("Delete");
        deleteMenuItem.setOnAction(actionEvent -> {
            Contact item = firstNameListView.getSelectionModel().getSelectedItem();
            deleteItem(item);
        });
        MenuItem viewMenuItem = new MenuItem("View");
        viewMenuItem.setOnAction(actionEvent -> {
            Contact item = firstNameListView.getSelectionModel().getSelectedItem();
            viewItem(item);
        });

        listContextMenu.getItems().addAll(deleteMenuItem);
        listContextMenu.getItems().addAll(viewMenuItem);
        firstNameListView.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null){
                lastNameListView.setSelectionModel(firstNameListView.getSelectionModel());
                numberListView.setSelectionModel(firstNameListView.getSelectionModel());
                notesListView.setSelectionModel(firstNameListView.getSelectionModel());
            }
        });

        all = contact -> true;
        favourites = contact -> contact.isFavourite();

        filteredList = new FilteredList<>(ContactData.getInstance().getContacts(), all);
        SortedList<Contact> sortedList = new SortedList<>(filteredList, new Comparator<Contact>() {
            @Override
            public int compare(Contact o1, Contact o2) {
                return o1.getFirstName().compareTo(o2.getFirstName());
            }
        });

        firstNameListView.setItems(sortedList);
        lastNameListView.setItems(sortedList);
        numberListView.setItems(sortedList);
        notesListView.setItems(sortedList);
        firstNameListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        firstNameListView.getSelectionModel().selectFirst();

        //should find a way to avoid the repetition
        firstNameListView.setCellFactory(new Callback<ListView<Contact>, ListCell<Contact>>() {
            @Override
            public ListCell<Contact> call(ListView<Contact> contactListView) {
                ListCell<Contact> cell = new ListCell<>(){
                    @Override
                    protected void updateItem(Contact contact, boolean empty) {
                        super.updateItem(contact, empty);
                        if (empty){
                            setText(null);
                        }else {
                            setText(contact.getFirstName());
                            if (contact.isFavourite()){
                                setTextFill(Color.DARKGOLDENROD);
                            }else {
                                setTextFill(Color.BLACK);
                            }
                        }
                    }
                };
                cell.emptyProperty().addListener(
                        (obs, wasEmpty, isNowEmpty)->{
                            if (isNowEmpty){
                                cell.setContextMenu(null);
                            }else{
                                cell.setContextMenu(listContextMenu);
                            }
                         });
                return cell;
            }
        });
        //should find a way to avoid the repetition
        lastNameListView.setCellFactory(new Callback<ListView<Contact>, ListCell<Contact>>() {
            @Override
            public ListCell<Contact> call(ListView<Contact> contactListView) {
                ListCell<Contact> cell = new ListCell<>(){
                    @Override
                    protected void updateItem(Contact contact, boolean empty) {
                        super.updateItem(contact, empty);
                        if (empty){
                            setText(null);
                        }else {
                            setText(contact.getLastName());
                            if (contact.isFavourite()){
                                setTextFill(Color.DARKGOLDENROD);
                            }else {
                                setTextFill(Color.BLACK);
                            }
                        }
                    }
                };
                cell.emptyProperty().addListener(
                        (obs, wasEmpty, isNowEmpty)->{
                            if (isNowEmpty){
                                cell.setContextMenu(null);
                            }else{
                                cell.setContextMenu(listContextMenu);
                            }
                        });
                return cell;
            }
        });
        //should find a way to avoid the repetition
        numberListView.setCellFactory(new Callback<ListView<Contact>, ListCell<Contact>>() {
            @Override
            public ListCell<Contact> call(ListView<Contact> contactListView) {
                ListCell<Contact> cell = new ListCell<>(){
                    @Override
                    protected void updateItem(Contact contact, boolean empty) {
                        super.updateItem(contact, empty);
                        if (empty){
                            setText(null);
                        }else {
                            setText(contact.getPhoneNumber());
                            if (contact.isFavourite()){
                                setTextFill(Color.DARKGOLDENROD);
                            }else {
                                setTextFill(Color.BLACK);
                            }
                        }
                    }
                };
                cell.emptyProperty().addListener(
                        (obs, wasEmpty, isNowEmpty)->{
                            if (isNowEmpty){
                                cell.setContextMenu(null);
                            }else{
                                cell.setContextMenu(listContextMenu);
                            }
                        });
                return cell;
            }
        });
        //should find a way to avoid the repetition
        notesListView.setCellFactory(new Callback<ListView<Contact>, ListCell<Contact>>() {
            @Override
            public ListCell<Contact> call(ListView<Contact> contactListView) {
                ListCell<Contact> cell = new ListCell<>(){
                    @Override
                    protected void updateItem(Contact contact, boolean empty) {
                        super.updateItem(contact, empty);
                        if (empty){
                            setText(null);
                        }else {
                            setText(contact.getNotes());
                            if (contact.isFavourite()){
                                setTextFill(Color.DARKGOLDENROD);
                            }else {
                                setTextFill(Color.BLACK);
                            }
                        }
                    }
                };
                cell.emptyProperty().addListener(
                        (obs, wasEmpty, isNowEmpty)->{
                            if (isNowEmpty){
                                cell.setContextMenu(null);
                            }else{
                                cell.setContextMenu(listContextMenu);
                            }
                        });
                return cell;
            }
        });
    }


    public void deleteItem(Contact item){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Contact");
        alert.setHeaderText("Delete contact: " + item.getFirstName() + " " + item.getLastName());
        alert.setContentText("Are you sure? Press OK to confirm, or cancel to back out");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && (result.get()==ButtonType.OK)){
            ContactData.getInstance().removeContact(item);
        }
    }

    public void viewItem(Contact item){

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Contact Details");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("FXMLfiles/contactInfoDialog.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
            ContactInfoController controller = fxmlLoader.getController();
            controller.setContact(item);

        }catch (IOException e) {
            System.out.println("Could not load the dialog");
            e.printStackTrace();
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        dialog.show();

    }

    @FXML
    public void handleClickListView(){
        Contact item = firstNameListView.getSelectionModel().getSelectedItem();
        lastNameListView.getSelectionModel().select(item);
        numberListView.getSelectionModel().select(item);
        notesListView.getSelectionModel().select(item);
    }

    @FXML
    public void handleKeyPressed(KeyEvent keyEvent){
        Contact selectedContact = firstNameListView.getSelectionModel().getSelectedItem();
        if (selectedContact != null){
            if (keyEvent.getCode().equals(KeyCode.DELETE)){
                deleteItem(selectedContact);
            }
        }
    }

    @FXML
    public void handleFilterButton(){
        if (favouritesButton.isSelected()){
            filteredList.setPredicate(favourites);
        }else {
            filteredList.setPredicate(all);
        }
    }

    @FXML
    public void showNewItemDialog(){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainToolBar.getScene().getWindow());
        dialog.setTitle("Add New Contact");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("FXMLfiles/newContactDialog.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());

        }catch (IOException e) {
            System.out.println("Could not load the dialog");
            e.printStackTrace();
            return;
        }
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK){
            NewContactDialogController controller = fxmlLoader.getController();
            Contact newContact = controller.handleFinishingCreation();
            if (newContact != null) {
                firstNameListView.setItems(ContactData.getInstance().getContacts());
                lastNameListView.setItems(ContactData.getInstance().getContacts());
                numberListView.setItems(ContactData.getInstance().getContacts());
                notesListView.setItems(ContactData.getInstance().getContacts());
                firstNameListView.getSelectionModel().select(newContact);
                all = contact -> true;
                favourites = contact ->  contact.isFavourite();
            }
        }
    }


}
