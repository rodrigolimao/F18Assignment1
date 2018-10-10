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
import java.sql.*;
import java.time.LocalDate;
import java.time.Period;

public class Contact1 {
    private String firstName, lastName, address, phone;
    private LocalDate birthday;
    private File imageFile;
    private int contactID;

    public Contact1(String firstName, String lastName, String address, String phone, LocalDate birthday) {
        setFirstName(firstName);
        setLastName(lastName);
        setAddress(address);
        setPhone(phone);
        setBirthday(birthday);
        setImageFile(new File("./src/images/defaultImage.png"));
    }

    public Contact1(String firstName, String lastName, String address, String phone, LocalDate birthday, File imageFile) {
        setFirstName(firstName);
        setLastName(lastName);
        setAddress(address);
        setPhone(phone);
        setBirthday(birthday);
        setImageFile(imageFile);

    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        if (!address.isEmpty()) {
            this.address = address;
        } else {
            throw new IllegalArgumentException("Address cannot be empty");
        }
    }

    public int getContactID() {
        return contactID;
    }

    public void setContactID(int volunteerID) {
        if (volunteerID >= 0)
            this.contactID = volunteerID;
        else
            throw new IllegalArgumentException("VolunteerID must be >= 0");
    }


    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
            if (!firstName.isEmpty()) {
                this.firstName = firstName;
            } else {
                throw new IllegalArgumentException("First name cannot be empty");
            }
        }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        if (!lastName.isEmpty()) {
            this.lastName = lastName;
        } else {
            throw new IllegalArgumentException("Last name cannot be empty");
        }
    }

    public String getPhone() {
        return phone;
    }

    /**
     * area code    city    house
     * NXX          -XXX    -XXXX
     * @param phone
     */
    public void setPhone(String phone) {
        if (phone.matches("[2-9]\\d{2}[-.]?\\d{3}[-.]\\d{4}"))
            this.phone = phone;
        else
            throw new IllegalArgumentException("Phone numbers must be in the pattern NXX-XXX-XXXX");
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    /**
     * Validate if the contact age is between 10 and 100
     * @param birthday
     */
    public void setBirthday(LocalDate birthday) {
        int age = Period.between(birthday, LocalDate.now()).getYears();

        if (age >= 18 && age <= 100)
            this.birthday = birthday;
        else
            throw new IllegalArgumentException("Volunteers must be 10-100 years of age.");
    }

    public File getImageFile() {
        return imageFile;
    }

    public void setImageFile(File imageFile) {
        this.imageFile = imageFile;
    }

    public String toString()
    {

        return String.format("%s %s %s %s", firstName, lastName, address, phone);
    }

//    /**
//     * This method will copy the file specified to the images directory on this server and give it
//     * a unique name
//     */
//    public void copyImageFile() throws IOException
//    {
//        //create a new Path to copy the image into a local directory
//        Path sourcePath = imageFile.toPath();
//
//        String uniqueFileName = getUniqueFileName(imageFile.getName());
//
//        Path targetPath = Paths.get("./src/images/"+uniqueFileName);
//
//        //copy the file to the new directory
//        Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
//
//        //update the imageFile to point to the new File
//        imageFile = new File(targetPath.toString());
//    }
//
//
//    /**
//     * This method will receive a String that represents a file name and return a
//     * String with a random, unique set of letters prefixed to it
//     */
//    private String getUniqueFileName(String oldFileName)
//    {
//        String newName;
//
//        //create a Random Number Generator
//        SecureRandom rng = new SecureRandom();
//
//        //loop until we have a unique file name
//        do
//        {
//            newName = "";
//
//            //generate 32 random characters
//            for (int count=1; count <=32; count++)
//            {
//                int nextChar;
//
//                do
//                {
//                    nextChar = rng.nextInt(123);
//                } while(!validCharacterValue(nextChar));
//
//                newName = String.format("%s%c", newName, nextChar);
//            }
//            newName += oldFileName;
//
//        } while (!uniqueFileInDirectory(newName));
//
//        return newName;
//    }
//
//
//    /**
//     * This method will search the images directory and ensure that the file name
//     * is unique
//     */
//    public boolean uniqueFileInDirectory(String fileName)
//    {
//        File directory = new File("./src/images/");
//
//        File[] dir_contents = directory.listFiles();
//
//        for (File file: dir_contents)
//        {
//            if (file.getName().equals(fileName))
//                return false;
//        }
//        return true;
//    }
//
//    /**
//     * This method will validate if the integer given corresponds to a valid
//     * ASCII character that could be used in a file name
//     */
//    public boolean validCharacterValue(int asciiValue)
//    {
//
//        //0-9 = ASCII range 48 to 57
//        if (asciiValue >= 48 && asciiValue <= 57)
//            return true;
//
//        //A-Z = ASCII range 65 to 90
//        if (asciiValue >= 65 && asciiValue <= 90)
//            return true;
//
//        //a-z = ASCII range 97 to 122
//        if (asciiValue >= 97 && asciiValue <= 122)
//            return true;
//
//        return false;
//    }


    /**
     * This method will write the instance of the Volunteer into the database
     */
    public void insertContactIntoDB() throws SQLException
    {
        String os = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;

        try
        {
            //1. Connect to the database
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/JavaDB", "root", "root");

            //2. Create a String that holds the query with ? as user inputs
            String sql = "INSERT INTO Contacts (firstName, lastName, address, phone, birthday, imageFile)"
                    + "VALUES (?,?,?,?,?,?)";

            //3. prepare the query
            ps = conn.prepareStatement(sql);

            //4. Convert the birthday into a SQL date
            Date db = Date.valueOf(birthday);

            //5. Bind the values to the parameters
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setString(3, address);
            ps.setString(4, phone);
            ps.setDate(5, db);
            ps.setString(6, imageFile.getName());


            ps.executeUpdate();
        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
        finally
        {
            if (ps != null)
                ps.close();

            if (conn != null)
                conn.close();
        }
    }

    /**
     * This will update the Contact in the database
     */
    public void updateContactInDB() throws SQLException
    {
        Connection conn = null;
        PreparedStatement ps = null;

        try{
            //1.  connect to the DB
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/JavaDB", "root", "root");

            //2.  create a String that holds our SQL update command with ? for user inputs
            String sql = "UPDATE Contacts SET firstName = ?, lastName = ?, address = ?, phone = ?, birthday = ?, imageFile = ?"
                    + "WHERE contactsID = ?";

            //3. prepare the query against SQL injection
            ps = conn.prepareStatement(sql);

            //4.  convert the birthday into a date object
            Date bd = Date.valueOf(birthday);

            //5. bind the parameters
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setString(2, address);
            ps.setString(3, phone);
            ps.setDate(4, bd);
            ps.setString(5, imageFile.getName());
            ps.setInt(6, contactID);

            //6. run the command on the SQL server
            ps.executeUpdate();
            ps.close();
        }
        catch (SQLException e)
        {
            System.err.println(e.getMessage());
        }
        finally
        {
            if (conn != null)
                conn.close();
            if (ps != null)
                ps.close();
        }

    }

}