package sample;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import sample.Contact;

public class ControllerRegistration implements Initializable {

    @FXML
    private DatePicker birthdayDatePicker;

    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private TextField addressTextField;

    @FXML
    private TextField phoneNumberTextField;

    @FXML
    private ImageView imageRegistration;

    private File imageFile;
    private boolean imageFileChanged;
    private Contact1 contact;

    @FXML private Label errMsgLabel;


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        errMsgLabel.setText("");
        //load the default image for the Contact
        try{
            imageFile = new File("./src/images/defaultImage.png");
            BufferedImage bufferedImage = ImageIO.read(imageFile);
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            imageRegistration.setImage(image);
        }

        catch (IOException e)
        {
            System.err.println(e.getMessage());
        }
    }

    public void chooseImageButtonPushed(ActionEvent event) {
        //get the Stage to open a new window (or Stage in JavaFX)
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        //Instantiate a FileChooser object
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image");

        //filter for .jpg and .png
        FileChooser.ExtensionFilter jpgFilter = new FileChooser.ExtensionFilter("Image File (*.jpg)", "*.jpg");
        FileChooser.ExtensionFilter pngFilter = new FileChooser.ExtensionFilter("Image File (*.png)", "*.png");
        FileChooser.ExtensionFilter gifFilter = new FileChooser.ExtensionFilter("Image File (*.gif)", "*.gif");
        fileChooser.getExtensionFilters().addAll(jpgFilter, pngFilter, gifFilter);

        //Set to the user's picture directory or user directory if not available
        String userDirectoryString = System.getProperty("user.home") + "\\Pictures";
        File userDirectory = new File(userDirectoryString);

        //if you cannot access the pictures directory, go to the user home
        if (!userDirectory.canRead())
            userDirectory = new File(System.getProperty("user.home"));

        fileChooser.setInitialDirectory(userDirectory);

        //open the file dialog window
        File tmpImageFile = fileChooser.showOpenDialog(stage);

        if (tmpImageFile != null) {
            imageFile = tmpImageFile;

            //update the ImageView with the new image
            if (imageFile.isFile()) {
                try {
                    BufferedImage bufferedImage = ImageIO.read(imageFile);
                    Image img = SwingFXUtils.toFXImage(bufferedImage, null);
                    imageRegistration.setImage(img);
                    imageFileChanged = true;
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            }
        }

    }
    /**
     * This method will create a new Person object and add it to the table
     */
    public void newPersonButtonPushed() throws SQLException {

            if (imageFileChanged) //create a Contact with a custom image
            {
                contact = new Contact1(firstNameTextField.getText(),lastNameTextField.getText(),addressTextField.getText(),
                        phoneNumberTextField.getText(), birthdayDatePicker.getValue(), imageFile);
            }
            else  //create a Contact with a default image
            {
                contact = new Contact1(firstNameTextField.getText(),lastNameTextField.getText(),addressTextField.getText(),
                        phoneNumberTextField.getText(), birthdayDatePicker.getValue());
            }
            errMsgLabel.setText("");    //do not show errors if creating Volunteer was successful
            contact.insertContactIntoDB();
        }

    /**
     * This method will read from the GUI fields and update the volunteer object
     */
    public void updateContacts() throws IOException
    {
        contact.setFirstName(firstNameTextField.getText());
        contact.setLastName(lastNameTextField.getText());
        contact.setPhone(phoneNumberTextField.getText());
        contact.setBirthday(birthdayDatePicker.getValue());
        contact.setImageFile(imageFile);
        contact.copyImageFile();
    }

}