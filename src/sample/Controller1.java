package sample;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
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
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Controller1 implements Initializable{

    @FXML
    private TableView<Contact1> tableView;
    @FXML
    private TableColumn<Contact1, Integer> contactIDColumn;
    @FXML
    private TableColumn<Contact1, String> firstNameColumn;
    @FXML
    private TableColumn<Contact1, String> lastNameColumn;
    @FXML
    private TableColumn<Contact1, String> addressColumn;
    @FXML
    private TableColumn<Contact1, String> phoneColumn;
    @FXML
    private TableColumn<Contact1, LocalDate> birthdayColumn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //set up the columns in the table
        contactIDColumn.setCellValueFactory(new PropertyValueFactory<Contact1, Integer>("contactID"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<Contact1, String>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<Contact1, String>("lastName"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<Contact1, String>("address"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<Contact1, String>("phone"));
        birthdayColumn.setCellValueFactory(new PropertyValueFactory<Contact1, LocalDate>("birthday"));

        try{
            loadContacts();
        }
        catch (SQLException e)
        {
            System.err.println(e.getMessage());
        }

    //Update the table to allow for the first and last name fields
        //to be editable
        tableView.setEditable(true);
        firstNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        lastNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        addressColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        phoneColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        //This will allow the table to select multiple rows at once
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }


    public void loadContacts() throws SQLException
    {
        ObservableList<Contact1> contacts = FXCollections.observableArrayList();

        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;

        try{
            //1. connect to the database
            conn = DriverManager.getConnection("jdbc:mysql://aws.computerstudi.es/gcc200353994", "gcc200353994", "pgz8FOjK7S");
            //2.  create a statement object
            st = conn.createStatement();

            //3.  create the SQL query
            rs = st.executeQuery("SELECT * FROM contacts");

            //4.  create volunteer objects from each record
            while (rs.next())
            {
                Contact1 newContact = new Contact1(rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("address"),
                        rs.getString("phone"),
                        rs.getDate("birthday").toLocalDate());
                        newContact.setContactID(rs.getInt("ContactID"));
                newContact.setImageFile(new File(rs.getString("imageFile")));

                contacts.add(newContact);
            }

            tableView.getItems().addAll(contacts);

        } catch (Exception e)
        {
            System.err.println(e);
        }
        finally
        {
            if (conn != null)
                conn.close();
            if(st != null)
                st.close();
            if(rs != null)
                rs.close();
        }
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
        Contact1 personSelected =  tableView.getSelectionModel().getSelectedItem();
        personSelected.setFirstName(edittedCell.getNewValue().toString());
    }

    /**
     * This method will allow the user to double click on a cell and update
     * the last name of the person
     */
    public void changeLastNameCellEvent(CellEditEvent edittedCell)
    {
        Contact1 personSelected =  tableView.getSelectionModel().getSelectedItem();
        personSelected.setLastName(edittedCell.getNewValue().toString());
    }

    public void changeAddressCellEvent(CellEditEvent edittedCell)
    {
        Contact1 personSelected =  tableView.getSelectionModel().getSelectedItem();
        personSelected.setAddress(edittedCell.getNewValue().toString());
    }

    public void changePhoneCellEvent(CellEditEvent edittedCell)
    {
        Contact1 personSelected =  tableView.getSelectionModel().getSelectedItem();
        personSelected.setPhone(edittedCell.getNewValue().toString());
    }

}
