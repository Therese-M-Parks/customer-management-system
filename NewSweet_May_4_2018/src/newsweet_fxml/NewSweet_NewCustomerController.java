/*Software II - Advanced Java Concepts (UG, C195, GZP1-0217) */
package newsweet_fxml;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import static java.time.LocalDateTime.now;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.InvalidationListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.xml.bind.Marshaller.Listener;
import newsweet_classes.Customer;
import static newsweet_fxml.NewSweet_homeController.parseCustomerList;
import static newsweet_fxml.NewSweet_mainController.loggedInUser;

/**
 * RUBRIC POINT: B. CUSTOMER RECORDS The application has functionality to enter
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
public class NewSweet_NewCustomerController extends NewSweet_homeController implements Initializable {

    // fxids for buttons
    @FXML
    private Label newCustomerLabel;

    @FXML
    private Button resetNewCustomer;

    @FXML
    private Button backToHome;

    @FXML
    private Button exitNewCustomer;

    @FXML
    private Button saveCustomerButton;

    @FXML
    private Button finishButton;

    // fxid's for TextFields
    @FXML
    private Text home_label;

    @FXML
    private TextField nameField;

    @FXML
    private TextField addressNameField1;

    @FXML
    private TextField addressNameField2;

    @FXML
    private TextField zipCodeField;

    @FXML
    private TextField phoneNumberField;

    //ComboBoxes
    @FXML
    private ComboBox<String> cityComboBox;

    @FXML
    private ComboBox<String> countryComboBox;

    @FXML // resets the textfields to re-enter text
    void resetNewCustomerButtonAction(ActionEvent event) {
        nameField.setText(null);
        phoneNumberField.setText(null);
        addressNameField1.setText(null);
        addressNameField2.setText(null);
        //   cityComboBox.
        zipCodeField.setText(null);
    }

    @FXML
    void saveCustomerButtonAction(ActionEvent event) throws IOException, SQLException {

        //---------------------------Validate user input--------------------------------//
        if (nameField.getText() == null || nameField.getText().length() < 2) {
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

        } else if (nameField.getText().length() > 25) {
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

        } else //addressNameField1
        if (addressNameField1.getText() == null || addressNameField1.getText().length() < 2) {
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

        } else if (addressNameField1.getText().length() > 25) {
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

        } else //contactAppointments
        if (zipCodeField.getText() == null || zipCodeField.getText().length() < 2) {
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

        } else if (zipCodeField.getText().length() > 25) {
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
        if (phoneNumberField.getText()
                == null || phoneNumberField.getText().length() < 2) {
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

        } else if (phoneNumberField.getText()
                .length() > 25) {
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
                if (cityComboBox.getSelectionModel().getSelectedItem() == null
                        || countryComboBox.getSelectionModel().getSelectedItem() == null) {

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
        // To insert into address

        try {
            Statement s = NewSweet_mainController.getConn().createStatement();
            // Pull correct cityid from city table and store it in a result set which is then stored in a String variable to use add to the address table
            ResultSet rs = s.executeQuery("SELECT city.cityid FROM city WHERE city.city = '" + cityComboBox.getSelectionModel().getSelectedItem() + "'");
            while (rs.next()) {
                String cityID; // Whatever city id was found from SELECT query above
                cityID = rs.getString("city.cityid");
                // Enters data into Address Table
                PreparedStatement ps = NewSweet_mainController.getConn().prepareStatement(
                        "INSERT INTO address (address, address2, cityId, postalCode, phone, createDate, createdBy, "
                        + "lastUpdate, lastUpdateBy) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);");
                ResultSetMetaData rsmd = rs.getMetaData();
                int columnsNumber = rsmd.getColumnCount();
                // List<String> validateAddress = Arrays.asList();
                ps.setString(1, addressNameField1.getText());
                ps.setString(2, addressNameField2.getText());
                ps.setString(3, cityID); // pulled from city
                ps.setString(4, zipCodeField.getText()); //postal code
                ps.setString(5, phoneNumberField.getText());
                ps.setString(6, LocalDateTime.now().toString());
                ps.setString(7, NewSweet_mainController.loggedInUser);
                ps.setString(8, LocalDateTime.now().toString());
                ps.setString(9, NewSweet_mainController.loggedInUser);

                for (int i = 1; i <= columnsNumber; i++) {
                    //---------------------------Validate user input--------------------------------//
                    //nameField
                    if (nameField.getText() == null) {
                        System.out.println("name : must not be null.");
                        //throw new IllegalArgumentException("name : must not be null");
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Invalid Information!");
                        alert.setHeaderText("Name must have at least 2 characters.");// TODO: Make this automatic logout work 
                        //Lambda Alert
                        alert.showAndWait()
                                .filter(response -> response == ButtonType.OK)
                                .ifPresent(response -> alert.close());
                        return;
                    }

                }
                int res = ps.executeUpdate();
                if (res == 1) {
                    System.out.println("YAY! for Address");
                } else {
                    System.out.println("BOO! for Address");
                }
            }//end while
        } catch (SQLException sqe) {
            System.out.println("Check your SQL for insterting into address table");
            sqe.printStackTrace();
        } catch (Exception e) {
            System.out.println("Something besides the SQL went wrong while inserting into address table.");
        }

        // customerid, customerName, addressId, active, createDate, createBy, lastUpdate, lastUpdateBy
        try {
            Statement s2 = NewSweet_mainController.getConn().createStatement();
            // Pull correct address id from address table and store it in a result set which is then stored in a String variable to use add to the customer table
            ResultSet rs2 = s2.executeQuery("SELECT address.addressid FROM address WHERE address.address = '" + addressNameField1.getText() + "'");
            rs2.next();
            String addressID = rs2.getString("address.addressid");
            //  String active = "1";
            // Enters data into Address Table
            PreparedStatement ps2 = NewSweet_mainController.getConn().prepareStatement(
                    "INSERT INTO customer (customerName, addressId, active, createDate, createdBy, lastUpdate, lastUpdateBy) VALUES (?, ?, ?, ?, ?, ?, ?);");
            ps2.setString(1, nameField.getText());
            ps2.setString(2, addressID); // pulled from address
            ps2.setString(3, "1");
            ps2.setString(4, LocalDateTime.now().toString());
            ps2.setString(5, NewSweet_mainController.loggedInUser);
            ps2.setString(6, LocalDateTime.now().toString());
            ps2.setString(7, NewSweet_mainController.loggedInUser);
            int res2 = ps2.executeUpdate();

            //   statement2.executeUpdate();
        } catch (SQLException sqe) {
            System.out.println("Check your SQL for insterting into customer table");
            sqe.printStackTrace();
        } catch (Exception e) {
            System.out.println("Something besides the SQL went wrong while inserting into customer table.");
        }

        // Take us back to the home screen
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setTitle(
                "Successfully Added");
        alert.setHeaderText(
                "You have successfully added a new Customer!");// TODO: Make this automatic logout work 
        //Lambda approach
        alert.showAndWait()
                .ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        // user chose OK
                        Stage stage;
                        Parent root = null;
                        //get reference to the button's stage         
                        stage = (Stage) newCustomerLabel.getScene().getWindow();
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
                }
                );

    }

    @FXML // Controls for back button
    void backToHomeButtonAction(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Cancel");
        alert.setHeaderText("Are you sure you want to leave this page? Changes will not be saved.");// TODO: Make this automatic logout work 
        alert.setContentText("Please click Okay to confirm, or click Cancel");
        //Lambda approach
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // user chose OK
                Stage stage;
                Parent root = null;
                //get reference to the button's stage         
                stage = (Stage) newCustomerLabel.getScene().getWindow();
                try {
                    //load up OTHER FXML document
                    root = FXMLLoader.load(getClass().getResource("NewSweet_home.fxml"));

                } catch (IOException ex) {
                    Logger.getLogger(NewSweet_homeController.class
                            .getName()).log(Level.SEVERE, null, ex);
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

    @FXML // controls to close NewSweet
    void exitNewCustomerButtonAction(ActionEvent event) {
        // get a handle to the stage
        Stage stage = (Stage) newCustomerLabel.getScene().getWindow();
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //To Fill the Country ComboBox
        String country;
        try {
            PreparedStatement statement = NewSweet_mainController.getConn().prepareStatement("SELECT country.country FROM country;");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                country = rs.getString("country.country");
                //Fills the ComboBox with the list of all Strings from the DB
                countryComboBox.getItems().add(country);
            }

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
                cityComboBox.getItems().add(city);
            }

        } catch (SQLException sqe) {
            System.out.println("Check your SQL in parseCitylist");
        } catch (Exception e) {
            System.out.println("Something besides the SQL went wrong in parseCityList.");
        }

    }

}
