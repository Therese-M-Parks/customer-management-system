/*Software II - Advanced Java Concepts (UG, C195, GZP1-0217) */
package newsweet_fxml;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import newsweet_classes.Appointment;
import newsweet_classes.Country;
import newsweet_classes.Customer;
import static newsweet_fxml.NewSweet_homeController.selectedCustomer;

/**
 * RUBRICS POINT: B. CUSTOMER RECORDS The application has functionality to enter
 * and maintain customer records in the database, including name, address, and
 * phone number. The code is complete and functions properly.
 *
 * RUBRIC POINT: F. EXCEPTION CONTROLS The application code includes exception
 * controls to prevent each of the given points and uses at least 2 different
 * mechanisms. The code is complete and functions properly.
 *
 * RUBRIC POINT: G. POP-UPS The application code uses lambda expressions to
 * create standard pop-up and alert messages. The code is complete and functions
 * properly.
 *
 * @author Therese Parks
 */
public class NewSweet_EditCustomerController extends NewSweet_homeController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Label editCustomerLabel;

    //Buttons
    @FXML
    private Button cancelButton;

    @FXML
    private Button exitEditScreen;

    @FXML
    private Text CountryInfo;

    //Text Fields
    @FXML
    private TextField editName;

    @FXML
    private TextField editStreet;

    @FXML
    private TextField editPhone;

    @FXML
    private TextField editAddress2;

    @FXML
    private TextField editZip;

    @FXML
    private ComboBox<String> editCountry;

    @FXML
    private ComboBox<String> editCity;

    @FXML
    private Button saveEditedCustomerButton;

    public static Customer deletedCustomer;

    @FXML
    void handleCity(ActionEvent event) {
        String selectedCustomer = editCountry.getSelectionModel().getSelectedItem();

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // Customer selectedCustomer = NewSweet_homeController.selectedCustomer;
        editName.setText(selectedCustomer.getName());
        editStreet.setText(selectedCustomer.getStreet());
        editAddress2.setText(selectedCustomer.getAddress2());
        editPhone.setText(selectedCustomer.getPhone());
        editCity.getSelectionModel().getSelectedItem();
        editCountry.getSelectionModel().getSelectedItem();
        editZip.setText(selectedCustomer.getZip());

        //To Fill the Country ComboBox
        Customer selectedCustomer = NewSweet_homeController.selectedCustomer;
        String country;
        try {
            PreparedStatement statement = NewSweet_mainController.getConn().prepareStatement("SELECT country.country FROM country;");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                country = rs.getString("country.country");
                //Fills the ComboBox with the list of all Strings from the DB
                editCountry.getItems().add(country);
            }
            editCountry.getSelectionModel().select(selectedCustomer.getCountryCustomer());

        } catch (SQLException sqe) {
            System.out.println("Check your SQL in Country ComboBox");
        } catch (Exception e) {
            System.out.println("Something besides the SQL went wrong in Country Combo Box");
        }

        //To Fill the City ComboBox
        String city;
        try {

            PreparedStatement statement = NewSweet_mainController.getConn().prepareStatement("SELECT city.city FROM city;");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                city = rs.getString("city.city");
                //Fills the ComboBox with the list of all Strings from the DB
                editCity.getItems().add(city);
            }
            editCity.getSelectionModel().select(selectedCustomer.getCity());
        } catch (SQLException sqe) {
            System.out.println("Check your SQL in parseCitylist");
        } catch (Exception e) {
            System.out.println("Something besides the SQL went wrong in parseCityList.");
        }

    }

    @FXML// Controls to save newly edited information
    void saveEditedCustomerButtonAction(ActionEvent event) throws IOException, SQLException {

        //---------------------------Validate user input--------------------------------//
        //nameField
        if (editName.getText() == null || editName.getText().length() < 2) {
            System.out.println("title : must have at least 2 characters.");
            try {
                // Section: F Second type of Exception control/throws clause
                throw new IllegalArgumentException("Invalid Customer Information!");

            } catch (IllegalArgumentException IA) {
                System.out.println("Invalid Customer Info.");
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Invalid Customer Information!");
                alert.setHeaderText("Fields must have at least 2 characters and no more than 25 characters.");
                //Lambda Alert
                alert.showAndWait()
                        .filter(response -> response == ButtonType.OK)
                        .ifPresent(response -> alert.close());
                return;
            }

        } else if (editName.getText().length() > 25) {
            System.out.println("title : must have less than 25 characters.");
            try {
                // Section: F Second type of Exception control/throws clause
                throw new IllegalArgumentException("Invalid Customer Information!");
            } catch (IllegalArgumentException IA) {
                System.out.println("Invalid Customer Info.");
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Invalid Customer Information!");
                alert.setHeaderText("Fields must have at least 2 characters and no more than 25 characters.");
                //Lambda Alert
                alert.showAndWait()
                        .filter(response -> response == ButtonType.OK)
                        .ifPresent(response -> alert.close());
                return;
            }

        } else if (editStreet.getText() == null || editStreet.getText().length() < 2) {
            System.out.println("description : must have at least 2 characters.");
            try {
                // Section: F Second type of Exception control/throws clause
                throw new IllegalArgumentException("Invalid Customer Information!");
            } catch (IllegalArgumentException IA) {
                System.out.println("Invalid Customer Info.");
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Invalid Customer Information!");
                alert.setHeaderText("Fields must have at least 2 characters and no more than 25 characters.");
                //Lambda Alert
                alert.showAndWait()
                        .filter(response -> response == ButtonType.OK)
                        .ifPresent(response -> alert.close());
                return;
            }

        } else if (editStreet.getText().length() > 25) {
            System.out.println("description : must have less than 25 characters.");
            try {
                // Section: F Second type of Exception control/throws clause
                throw new IllegalArgumentException("Invalid Customer Information!");
            } catch (IllegalArgumentException IA) {
                System.out.println("Invalid Customer Info.");
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Invalid Customer Information!");
                alert.setHeaderText("Fields must have at least 2 characters and no more than 25 characters.");
                //Lambda Alert
                alert.showAndWait()
                        .filter(response -> response == ButtonType.OK)
                        .ifPresent(response -> alert.close());
                return;
            }

        } else //editPhone
        if (editPhone.getText() == null || editPhone.getText().length() < 2) {
            System.out.println("contact : must have more than 2 characters");
            try {
                // Section: F Second type of Exception control/throws clause
                throw new IllegalArgumentException("Invalid Customer Information!");
            } catch (IllegalArgumentException IA) {
                System.out.println("Invalid Customer Info.");
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Invalid Customer Information!");
                alert.setHeaderText("Fields must have at least 2 characters and no more than 25 characters.");
                //Lambda Alert
                alert.showAndWait()
                        .filter(response -> response == ButtonType.OK)
                        .ifPresent(response -> alert.close());
                return;
            }

        } else if (editPhone.getText().length() > 25) {
            System.out.println("contact : must have less than 25 characters");
            try {
                // Section: F Second type of Exception control/throws clause
                throw new IllegalArgumentException("Invalid Customer Information!");
            } catch (IllegalArgumentException IA) {
                System.out.println("Invalid Customer Info.");
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Invalid Customer Information!");
                alert.setHeaderText("Fields must have at least 2 characters and no more than 25 characters.");
                //Lambda Alert
                alert.showAndWait()
                        .filter(response -> response == ButtonType.OK)
                        .ifPresent(response -> alert.close());
                return;
            }

        } else //contactAppointments
        if (editZip.getText() == null || editZip.getText().length() < 2) {
            System.out.println("contact : must have more than 2 characters");
            try {
                // Section: F Second type of Exception control/throws clause
                throw new IllegalArgumentException("Invalid Customer Information!");
            } catch (IllegalArgumentException IA) {
                System.out.println("Invalid Customer Info.");
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Invalid Customer Information!");
                alert.setHeaderText("Fields must have at least 2 characters and no more than 25 characters.");
                //Lambda Alert
                alert.showAndWait()
                        .filter(response -> response == ButtonType.OK)
                        .ifPresent(response -> alert.close());
                return;
            }

        } else if (editZip.getText().length() > 25) {
            System.out.println("contact : must have less than 25 characters");
            try {
                // Section: F Second type of Exception control/throws clause
                throw new IllegalArgumentException("Invalid Customer Information!");
            } catch (IllegalArgumentException IA) {
                System.out.println("Invalid Customer Info.");
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Invalid Customer Information!");
                alert.setHeaderText("Fields must have at least 2 characters and no more than 25 characters.");
                //Lambda Alert
                alert.showAndWait()
                        .filter(response -> response == ButtonType.OK)
                        .ifPresent(response -> alert.close());
                return;
            }
        } else {
            try {
                //Check for day and times being null
                if (editCountry.getSelectionModel().getSelectedItem() == null
                        || editCity.getSelectionModel().getSelectedItem() == null) {

                    System.out.print("No City/Sate Selected.");
                    System.out.println("Invalid Customer Info.");
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Invalid Customer Information!");
                    alert.setHeaderText("Must Select Country and State");
                    //Lambda Alert
                    alert.showAndWait()
                            .filter(response -> response == ButtonType.OK)
                            .ifPresent(response -> alert.close());
                    return;
                }
            } catch (NullPointerException n) {

            }

        }

        // Whether all the fields are changed or not, they will still have text to pull
        String tName = editName.getText();
        String tStreet = editStreet.getText();
        String tAddress2 = editAddress2.getText();
        String tPhone = editPhone.getText();
        String tCity = editCity.getSelectionModel().getSelectedItem();
        String tZip = editZip.getText();
        //String tCountry = selectedCustomer.getCountry();

        // To insert into address
        try {
            Statement s = NewSweet_mainController.getConn().createStatement();
            ResultSet rs = s.executeQuery("SELECT city.cityid FROM city WHERE city.city = '" + tCity + "'");
            rs.next();
            String cityID;
            cityID = rs.getString("city.cityid");

            // Want to udate: address, address2, cityId, postalCode, phone, lastUdate, lastUpdateBy
            PreparedStatement ps = NewSweet_mainController.getConn().prepareStatement(
                    "UPDATE address "
                    + "SET address = ?, "
                    + "address2 = ?, "
                    + "cityId = ?, "
                    + "postalCode = ?, "
                    + "phone = ?, "
                    + "address.lastUpdate = ?, "
                    //  + "address.lastUpdateBy = ? WHERE address.addressid = customer.addressId AND address.addressid = ?;");
                    + "address.lastUpdateBy = ? WHERE address.addressid = '" + selectedCustomer.getCusID() + "'");
            ps.setString(1, tStreet);
            ps.setString(2, tAddress2);
            ps.setString(3, cityID);
            ps.setString(4, tZip);
            ps.setString(5, tPhone);
            ps.setString(6, LocalDateTime.now().toString());// lastUpdate
            ps.setString(7, NewSweet_mainController.loggedInUser); // lastUpdateBy
            //   ps.setString(8, selectedCustomer.getAddressId());
            int res = ps.executeUpdate();

            if (res == 1) {//one row was affected; namely the one that was inserted!
                System.out.println("YAY! for Address");
            } else {
                System.out.println("BOO! for Address");
            }

        } catch (SQLException sqe) {
            System.out.println("Check your SQL for UPDATING address table");
            sqe.printStackTrace();
        } catch (Exception e) {
            System.out.println("Something besides the SQL went wrong UPDATING ADDRESS.");
        }
        //  customerid, customerName, addressId, active, createDate, createBy, lastUpdate, lastUpdateBy
        try {

            // Want to update these columns: customerName, addressId, lastUpdate, lastUpdateBy
            PreparedStatement ps2 = NewSweet_mainController.getConn().prepareStatement(
                    "UPDATE customer SET "
                    + "customerName = ?, "
                    + "lastUpdate = ?, "
                    + "lastUpdateBy = ? WHERE customer.customerid =  '" + selectedCustomer.getCusID() + "'");

            ps2.setString(1, tName);
            ps2.setString(2, LocalDateTime.now().toString()); // lastUpdate
            ps2.setString(3, NewSweet_mainController.loggedInUser); // lastUpdateBy
            int res2 = ps2.executeUpdate();
            if (res2 == 1) {//one row was affected; namely the one that was inserted!
                System.out.println("YAY!");
            } else {
                System.out.println("BOO!");
            }

            //   statement2.executeUpdate();
        } catch (SQLException sqe) {
            System.out.println("Check your SQL for UPDATING CUSTOMER table");
            sqe.printStackTrace();
        } catch (Exception e) {
            System.out.println("Something besides the SQL went wrong UPDATING CUSTOMER table.");
        }

        // Take us back to the home screen
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Successfull Edited");
        alert.setHeaderText("You have successfully edited the record for: " + tName + " !");
        //Lambda approach;// TODO: Make this automatic logout work
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // user chose OK
                Stage stage;
                Parent root = null;
                //get reference to the button's stage         
                stage = (Stage) editCustomerLabel.getScene().getWindow();
                try {
                    //load up OTHER FXML document
                    root = FXMLLoader.load(getClass().getResource("NewSweet_home.fxml"));
                } catch (IOException ex) {
                    Logger.getLogger(NewSweet_homeController.class.getName()).log(Level.SEVERE, null, ex);
                }
                //create a new scene with root and set the stage
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } else {
                // user chose CANCEL or closed the dialog
                alert.close();
            }
        });

    }

    @FXML
    void handleCountryInfo(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cannot Change Country!");
        alert.setHeaderText("If the customer has changed countries, a new account must be created.");
        alert.setContentText("Please click Okay to confirm, or click Cancel");

        //  alert.setContentText("Please click Okay to confirm, or click Cancel");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            alert.close();

        } else {
            // user chose CANCEL or closed the dialog
            alert.close();
        }

    }

    @FXML // Controls for cancel button
    void cancelButtonAction(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Cancel");
        alert.setHeaderText("Are you sure you want to leave this page? Changes will not be saved.");
        alert.setContentText("Please click Okay to confirm, or click Cancel");

        //Lambda approach
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // user chose OK
                Stage stage;
                Parent root = null;
                //get reference to the button's stage         
                stage = (Stage) editCustomerLabel.getScene().getWindow();
                try {
                    //load up OTHER FXML document
                    root = FXMLLoader.load(getClass().getResource("NewSweet_home.fxml"));
                } catch (IOException ex) {
                    Logger.getLogger(NewSweet_homeController.class.getName()).log(Level.SEVERE, null, ex);
                }
                //create a new scene with root and set the stage
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } else {
                // user chose CANCEL or closed the dialog
                alert.close();
            }
        });

    }

    @FXML // controls for exit button
    void exitEditScreenButtonAction(ActionEvent event) {

        // get a handle to the stage
        Stage stage = (Stage) exitEditScreen.getScene().getWindow();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Exit");
        alert.setHeaderText("Changes may not be saved. Are you sure you want to exit netSweet ?");
        alert.setContentText("Please click Okay to confirm, or click Cancel");
        //Lambda Alert
        alert.showAndWait()
                .filter(response -> response == ButtonType.OK)
                .ifPresent(response -> stage.close());
        return;

    }

}
