package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;


public class Contact {
    private SimpleStringProperty firstName, lastName, address, phone;
    private LocalDate birthday;
    private Image photo;

    public Contact(String firstName, String lastName, String address, String phone, LocalDate birthday) {
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.address = new SimpleStringProperty(address);
        this.phone = new SimpleStringProperty(phone);
        this.birthday = birthday;
        photo = new Image("defaultImage.png");
    }

    public Contact(String firstName, String lastName, String address, String phone, LocalDate birthday, Image photo) {
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.address = new SimpleStringProperty(address);
        this.phone = new SimpleStringProperty(phone);
        this.birthday = birthday;
        this.photo = photo;
    }

    public String getAddress() {
        return address.get();
    }

    public void setAddress(String address) {

        this.address = new SimpleStringProperty(address);
    }

    public String getFirstName() {

        return firstName.get();
    }

    public void setFirstName(String firstName) {

        this.firstName = new SimpleStringProperty(firstName);
    }

    public String getPhone() {

        return phone.get();
    }

    public void setPhone(String phone) {

        this.phone = new SimpleStringProperty(phone);
    }

    public Image getImage()
    {

        return photo;
    }

    public void setImage(Image newPicture)
    {

        this.photo = newPicture;
    }


    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String lastName) {

        this.lastName = new SimpleStringProperty(lastName);
    }

    public LocalDate getBirthday() {

        return birthday;
    }

    public int getAge()
    {

        return Period.between(birthday, LocalDate.now()).getYears();
    }

    public void setBirthday(LocalDate birthday) {

        this.birthday = birthday;
    }

    public String toString()
    {

        return String.format("%s %s %s %s", firstName, lastName, address, phone);
    }


}

