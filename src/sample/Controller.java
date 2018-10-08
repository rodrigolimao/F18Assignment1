package sample;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Controller implements Initializable{

    @FXML
    private TableView<Contact> tableView;
    @FXML private TableColumn<Contact, Integer> volunterIDColumn;
    @FXML
    private TableColumn<Contact, String> firstNameColumn;
    @FXML
    private TableColumn<Contact, String> lastNameColumn;
    @FXML
    private TableColumn<Contact, String> addressColumn;
    @FXML
    private TableColumn<Contact, String> phoneColumn;
    @FXML
    private TableColumn<Contact, LocalDate> birthdayColumn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //set up the columns in the table
        volunterIDColumn.setCellValueFactory(new PropertyValueFactory<Contact, Integer>("volunteerID"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<Contact, String>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<Contact, String>("lastName"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<Contact, String>("address"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<Contact, String>("phone"));
        birthdayColumn.setCellValueFactory(new PropertyValueFactory<Contact, LocalDate>("birthday"));

        //load dummy data
        tableView.setItems(getPeople());

        //Update the table to allow for the first and last name fields
        //to be editable
        tableView.setEditable(true);
        firstNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        lastNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        addressColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        phoneColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        //This will allow the table to select multiple rows at once
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        //Disable the detailed person view button until a row is selected
//        this.detailedPersonViewButton.setDisable(true);

    }
    public ObservableList<Contact> getPeople()
    {
        ObservableList<Contact> people = FXCollections.observableArrayList();
        people.add(new Contact("Rodrigo","Lima", "99A Bernick Drive", "705-500-1525",LocalDate.of(1993, Month.MARCH, 15), new Image("rodrigo.jpg")));
        people.add(new Contact("Hanna","de Almeida","99A Bernick Drive", "705-500-4425", LocalDate.of(1997, Month.AUGUST, 20), new Image("./src/images/defaultImage.png")));

        return people;
    }

    public void changeScreenButtonPushed(ActionEvent event) throws IOException
    {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("Registration.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }

    /**
     * This method will allow the user to double click on a cell and update
     * the first name of the person
     */
    public void changeFirstNameCellEvent(CellEditEvent edittedCell)
    {
        Contact personSelected =  tableView.getSelectionModel().getSelectedItem();
        personSelected.setFirstName(edittedCell.getNewValue().toString());
    }

    /**
     * This method will allow the user to double click on a cell and update
     * the first name of the person
     */
    public void changeLastNameCellEvent(CellEditEvent edittedCell)
    {
        Contact personSelected =  tableView.getSelectionModel().getSelectedItem();
        personSelected.setLastName(edittedCell.getNewValue().toString());
    }

    public void changeAddressCellEvent(CellEditEvent edittedCell)
    {
        Contact personSelected =  tableView.getSelectionModel().getSelectedItem();
        personSelected.setAddress(edittedCell.getNewValue().toString());
    }

    public void changePhoneCellEvent(CellEditEvent edittedCell)
    {
        Contact personSelected =  tableView.getSelectionModel().getSelectedItem();
        personSelected.setPhone(edittedCell.getNewValue().toString());
    }
}
