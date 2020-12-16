package sample;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import sample.datamodel.Contact;
import sample.datamodel.ContactData;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class Controller {

    private List<Contact> contactList;

    @FXML
    private ListView<Contact> firstNameListView;

    @FXML
    private ListView<Contact> lastNameListView;

    @FXML
    private ListView<Contact> numberListView;

    @FXML
    private ListView<Contact> notesListView;

    @FXML
    private Button newContactButton;

    @FXML
    private ToggleButton favouritesButton;

    @FXML
    private ContextMenu listContextMenu;

    private FilteredList<Contact> filteredList;
    private Predicate<Contact> all;
    private Predicate<Contact> favourites;

    public void initialize(){
        listContextMenu = new ContextMenu();
        MenuItem deleteMenuItem = new MenuItem("Delete");
        deleteMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Contact item = firstNameListView.getSelectionModel().getSelectedItem();
                deleteItem(item);
            }
        });

        listContextMenu.getItems().addAll(deleteMenuItem);
        firstNameListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Contact>() {
            @Override
            public void changed(ObservableValue<? extends Contact> observableValue, Contact oldValue, Contact newValue) {
                if (newValue != null){
                    Contact item = firstNameListView.getSelectionModel().getSelectedItem();
                    lastNameListView.setSelectionModel(firstNameListView.getSelectionModel());
                    numberListView.setSelectionModel(firstNameListView.getSelectionModel());
                    notesListView.setSelectionModel(firstNameListView.getSelectionModel());
                }
            }
        });

        all = new Predicate<Contact>() {
            @Override
            public boolean test(Contact contact) {
                return true;
            }
        };
        favourites = new Predicate<Contact>() {
            @Override
            public boolean test(Contact contact) {
                return contact.isFavourite();
            }
        };

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
                                setTextFill(Color.YELLOW);
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
                            setText(contact.getFirstName());
                            if (contact.isFavourite()){
                                setTextFill(Color.YELLOW);
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
                            setText(contact.getFirstName());
                            if (contact.isFavourite()){
                                setTextFill(Color.YELLOW);
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
                            setText(contact.getFirstName());
                            if (contact.isFavourite()){
                                setTextFill(Color.YELLOW);
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

    @FXML
    public void handleClickListView(){
        Contact item = firstNameListView.getSelectionModel().getSelectedItem();
        lastNameListView.setSelectionModel(firstNameListView.getSelectionModel());
        numberListView.setSelectionModel(firstNameListView.getSelectionModel());
        notesListView.setSelectionModel(firstNameListView.getSelectionModel());
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
    public void handleExit(){
        Platform.exit();
    }

}
