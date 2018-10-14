package sample;

import com.sun.corba.se.pept.transport.ContactInfo;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class SceneChanger {

    /**
     * This method will accept the title of the new scene, the .fxml file name
     * for the view and the ActionEvent that triggered the change
     *
     * @param event
     * @param viewName
     * @param title
     * @throws java.io.IOException
     */
    public void changeScenes(ActionEvent event, String viewName, String title) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(viewName));
        Parent parent = loader.load();

        Scene scene = new Scene(parent);

        //get the stage from the event that was passed in
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method will change scenes and pre-load the next scene with a
     * Contact object
     *
     * @param event
     * @param viewName
     * @param title
     * @param contact
     * @param controllerClass
     * @throws java.io.IOException
     */
    public void changeScenes(ActionEvent event, String viewName, String title, ContactInfo contact, Controller1 controllerClass) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(viewName));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);

        //Access the controller and the data preloaded
        controllerClass = loader.getController();
        controllerClass.loadContacts();

        //Get the stage from the event
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }

    void changeScenes(ActionEvent event, String contactFormfxml, String edit_Contact, ContactInfo contact, ControllerRegistration npvc) {
        throw new UnsupportedOperationException("Not supported");
    }
}
