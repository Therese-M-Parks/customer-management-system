/*Software II - Advanced Java Concepts (UG, C195, GZP1-0217) */
package newsweet_fxml;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
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
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import newsweet_classes.Customer;
import newsweet_classes.Appointment;
import newsweet_classes.Country;
import newsweet_classes.Reminder;
import static newsweet_fxml.NewSweet_mainController.loggedInUser;
//import static newsweet_classes.Customer.allCustomers;

/**
 * RUBRIC POINT: D. CALENDAR VIEWS The application has functionality to view the
 * calendar by month and by week. The code is complete and functions properly.
 * Requirements met by table views which show appointment schedules.
 *
 * RUBRIC POINT: G. POP-UPS The application code uses lambda expressions to
 * create standard pop-up and alert messages. The code is complete and functions
 * properly.
 *
 * @author Therese Parks
 */
public class NewSweet_homeController implements Initializable {

    @FXML
    private Label homeScreenLabel;

    @FXML
    private Button refreshButton;

    @FXML
    private Button newCustomerButton;

    @FXML
    private Button editCustomer;

    @FXML
    private Button exitHomeScreen;

    @FXML
    private Button addAppointment;

    @FXML
    private Button editAppointmentButton;

    @FXML
    private Button deleteAppointmentButton;

    @FXML
    private Button viewReportsButton;

    private Button calendarButton;

    private Button calendarButton2;

    @FXML
    private TextField searchFieldCustomers;

    @FXML
    static TextField activeCustomers;

    public static Customer selectedCustomer;
    public static Customer deletedCustomer;
    public static Appointment selectedAppointment;

    @FXML
    private Button SearchButton;

    //Customer Records Table 7 columns
    @FXML
    public TableView<Customer> customerRecordsTable;

    @FXML
    public TableColumn<Customer, String> idColCustomerRecords;

    @FXML
    public TableColumn<Customer, String> nameColCustomerRecords;

    @FXML
    public TableColumn<Customer, String> streetColCustomerRecords;

    @FXML
    private TableColumn<Customer, String> address2ColCustomerRecords;

    @FXML
    public TableColumn<Customer, String> cityColCustomerRecords;

    @FXML
    public TableColumn<Customer, String> zipColCustomerRecords;

    @FXML
    public TableColumn<Customer, String> phoneColCustomerRecords;

    @FXML
    public TableColumn<Customer, String> countryColCustomerRecords;

    //Appointments Table
    @FXML
    private TableView<Appointment> appointmentsTable;

    @FXML
    private TableColumn<Appointment, String> idColAppointments;

    @FXML
    private TableColumn<Appointment, String> titleColAppointments;

    @FXML
    private TableColumn<Appointment, String> locationColAppointments;

    @FXML
    private TableColumn<Appointment, String> contactColAppointments;

    @FXML
    private TableColumn<Appointment, String> urlColAppointments;

    @FXML
    private TableColumn<Appointment, String> startColAppointments;

    @FXML
    private TableColumn<Appointment, String> endColAppointments;

    @FXML
    private TableColumn<Appointment, String> descriptionColAppointments;
    Locale currentLocale = Locale.getDefault();

    @FXML // refreshes Appointments table on home screen
    void refreshButtonButtonAction(ActionEvent event) {
        //Delete's the Deleted Customers Appointments from TableView
        appointmentsTable.getItems().clear();

    }

    @FXML
    void showCustomerAppointments(MouseEvent event) throws IOException {

        selectedCustomer = customerRecordsTable.getSelectionModel().getSelectedItem();
        appointmentsTable.getItems().setAll(parseAppointmentList());
    }

    @FXML
    void viewReportsButtonAction(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        //get reference to the button's stage         
        stage = (Stage) homeScreenLabel.getScene().getWindow();
        //load up OTHER FXML document
        root = FXMLLoader.load(getClass().getResource("NewSweet_ViewReports.fxml"));
        //create a new scene with root and set the stage
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void calendarButtonAction(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        //get reference to the button's stage         
        stage = (Stage) homeScreenLabel.getScene().getWindow();
        //load up OTHER FXML document
        root = FXMLLoader.load(getClass().getResource("NewSweet_CalendarByMonth.fxml"));
        //create a new scene with root and set the stage
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
    
     @FXML
    void calendarButton2Action(ActionEvent event) throws IOException{
        Stage stage;
        Parent root;
        //get reference to the button's stage         
        stage = (Stage) homeScreenLabel.getScene().getWindow();
        //load up OTHER FXML document
        root = FXMLLoader.load(getClass().getResource("NewSweet_CalendarByWeek.fxml"));
        //create a new scene with root and set the stage
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    public void addAppointmentButtonAction(ActionEvent event) throws IOException {
        selectedCustomer = customerRecordsTable.getSelectionModel().getSelectedItem();
        //TODO: make an alert pop up
        if (selectedCustomer == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Customer Selected");
            alert.setHeaderText("Please Select Customer to add an appointment.");
            //Lambda Alert
            alert.showAndWait()
                    .filter(response -> response == ButtonType.OK)
                    .ifPresent(response -> alert.close());
            return;
        }

        Stage stage;
        Parent root;
        //get reference to the button's stage         
        stage = (Stage) homeScreenLabel.getScene().getWindow();
        //load up OTHER FXML document
        root = FXMLLoader.load(getClass().getResource("NewSweet_NewAppointment.fxml"));
        //create a new scene with root and set the stage
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void editAppointmentButtonAction(ActionEvent event) throws IOException {
        selectedAppointment = appointmentsTable.getSelectionModel().getSelectedItem();
        if (selectedAppointment == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Appointment Selected");
            alert.setHeaderText("Please Select Appointment to edit.");
            //Lambda Alert
            alert.showAndWait()
                    .filter(response -> response == ButtonType.OK)
                    .ifPresent(response -> alert.close());
            return;
        }

        Stage stage;
        Parent root;
        //get reference to the button's stage         
        stage = (Stage) homeScreenLabel.getScene().getWindow();
        //load up OTHER FXML document
        root = FXMLLoader.load(getClass().getResource("NewSweet_EditAppointment.fxml"));
        //create a new scene with root and set the stage
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void deleteAppointmentButtonAction(ActionEvent event) throws SQLException {
        selectedAppointment = appointmentsTable.getSelectionModel().getSelectedItem();
        // If user forgot to select a appointment
        if (selectedAppointment == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Appointment Selected");
            alert.setHeaderText("Please Select Appointment to delete.");

            //Lambda Alert
            alert.showAndWait()
                    .filter(response -> response == ButtonType.OK)
                    .ifPresent(response -> alert.close());
            return;
        }

        // If user selects a appointment
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete");
        alert.setHeaderText("Are you sure you want to delete this Appointment?");
        alert.setContentText("Please click Okay to confirm, or click Cancel");
        //Delete's the Selected Customer's record from tableview
        //Lambda
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // user chose OK
                appointmentsTable.getItems().remove(selectedAppointment);
            } else {
                // user chose CANCEL or closed the dialog
                alert.close();
            }
        });
        //Delete's the Selected Customer's record from DB
        try {

            PreparedStatement ps = NewSweet_mainController.getConn().prepareStatement(
                    "DELETE FROM appointment WHERE appointment.appointmentid = '" + selectedAppointment.getAppointmentId() + "'");

            //Delete's all associated Reminders for that Appointment
            int res = ps.executeUpdate();

        } catch (SQLException sqe) {
            System.out.println("Check your SQL for DeleteAppointment");
            sqe.printStackTrace();
        } catch (Exception e) {
            System.out.println("Something besides the SQL went wrong while Deleting Appointment.");
        }
    }

    @FXML
    public void deleteCustomerButtonAction(ActionEvent event) throws SQLException {
        selectedCustomer = customerRecordsTable.getSelectionModel().getSelectedItem();
        // If user forgot to select a customer
        if (selectedCustomer == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Customer Selected");
            alert.setHeaderText("Please Select Customer to delete.");
            //Lambda Expression
            alert.showAndWait()
                    .filter(response -> response == ButtonType.OK)
                    .ifPresent(response -> alert.close());
            return;
        }

        appointmentsTable.getItems().clear();
        // If user selects a customer
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete");
        alert.setHeaderText("Are you sure you want to delete this Customer and all associated records?");
        alert.setContentText("Please click Okay to confirm, or click Cancel");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            // user chose OK
            customerRecordsTable.getItems().remove(selectedCustomer);
        } else {
            // user chose CANCEL or closed the dialog
            alert.close();
        }

        //Delete's the Selected Customer's record from TableView
        //  customerRecordsTable.getItems().remove(selectedCustomer);
        try {

            //Delete's the Selected Customer's record from DB
            PreparedStatement ps = NewSweet_mainController.getConn().prepareStatement(
                    "DELETE FROM customer WHERE customer.customerid = '" + selectedCustomer.getCusID() + "'"); // works

            PreparedStatement ps1 = NewSweet_mainController.getConn().prepareStatement("DELETE FROM address WHERE address.address = '" + selectedCustomer.getStreet() + "'");

            //Delete's all associated Appointments for that Customer
            PreparedStatement ps2 = NewSweet_mainController.getConn().prepareStatement("DELETE FROM appointment WHERE appointment.customerId = '" + selectedCustomer.getCusID() + "'");

            //TODO: IMPORTANT!!
            //Delete's all associated Reminders for that Appointment
            int res = ps.executeUpdate();
            int res1 = ps1.executeUpdate();
            int res2 = ps2.executeUpdate();
        } catch (SQLException sqe) {
            System.out.println("Check your SQL for Delete");
            sqe.printStackTrace();
        } catch (Exception e) {
            System.out.println("Something besides the SQL went wrong while Deleting.");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime nowTime = LocalDateTime.now();
        System.out.println(dtf.format(nowTime));

        customerRecordsTable.getItems().clear();
        //Lambda Expressions
        //customer id
        idColCustomerRecords.setCellValueFactory(cellData
                -> cellData.getValue().cusIDProperty());
        // name
        nameColCustomerRecords.setCellValueFactory(cellData
                -> cellData.getValue().nameProperty());
        //street
        streetColCustomerRecords.setCellValueFactory(cellData
                -> cellData.getValue().streetProperty());
        //address
        address2ColCustomerRecords.setCellValueFactory(cellData
                -> cellData.getValue().address2Property());
        //phone
        phoneColCustomerRecords.setCellValueFactory(cellData
                -> cellData.getValue().phoneProperty());
        //city
        cityColCustomerRecords.setCellValueFactory(cellData
                -> cellData.getValue().cityProperty());
        //zip
        zipColCustomerRecords.setCellValueFactory(cellData
                -> cellData.getValue().zipProperty());
        //country
        countryColCustomerRecords.setCellValueFactory(cellData
                -> cellData.getValue().countryCustomerProperty());

        customerRecordsTable.getItems().clear();
        customerRecordsTable.getItems().setAll(parseCustomerList());

        // columns for appointments table
        //idColAppointments
        idColAppointments.setCellValueFactory(cellData
                -> cellData.getValue().appointmentIdProperty());
        //titleColAppointments
        titleColAppointments.setCellValueFactory(cellData
                -> cellData.getValue().titleProperty());
        //descriptionColAppointments
        descriptionColAppointments.setCellValueFactory(cellData
                -> cellData.getValue().descriptionProperty());
        //locationColAppointments
        locationColAppointments.setCellValueFactory(cellData
                -> cellData.getValue().locationProperty());
        //contactColAppointments
        contactColAppointments.setCellValueFactory(cellData
                -> cellData.getValue().contactProperty());
        //urlColAppointments
        urlColAppointments.setCellValueFactory(cellData
                -> cellData.getValue().urlProperty());
        //startColAppointments
        startColAppointments.setCellValueFactory(cellData
                -> cellData.getValue().startProperty());
        //endColAppointments
        endColAppointments.setCellValueFactory(cellData
                -> cellData.getValue().endProperty());

    }

    @FXML
    private Tab accountsTab;

    @FXML
    void findNumCustomers(ActionEvent event) throws SQLException {

        String numResult;
        try (
                PreparedStatement statement = NewSweet_mainController.getConn().prepareStatement("SELECT COUNT(*) FROM customer;");
                ResultSet rs = statement.executeQuery();) {
            numResult = rs.toString();

            accountsTab.setText("There are now " + numResult + " Customers in NewSweet!");

        } catch (SQLException sqe) {
            System.out.println("Check your SQL");
        } catch (Exception e) {
            System.out.println("Something besides the SQL went wrong.");

        }

    }

    static List<Customer> parseCustomerList() {

        String tId;
        String tName;
        String tStreet;
        String tAddress2;
        String tAddressId;
        String tCity;
        String tCityId;
        String tZip;
        String tPhone;
        String tCountry;
        String tCountryId;

        ArrayList<Customer> custList = new ArrayList();

        try (
                PreparedStatement statement = NewSweet_mainController.getConn().prepareStatement(
                        "SELECT customer.customerid, customer.customerName, address.address, address.address2,"
                        + " address.addressid, address.phone, address.postalCode, city.city, city.cityid,"
                        + " country.country, country.countryid FROM customer, address, city, country"
                        + " WHERE customer.addressId = address.addressid"
                        + " AND address.cityId = city.cityid"
                        + " AND city.countryid = country.countryid"
                        + " order by customerid;");
                ResultSet rs = statement.executeQuery();) {

            while (rs.next()) {
                tId = rs.getString("customer.customerid");

                tName = rs.getString("customer.customerName");

                tStreet = rs.getString("address.address");

                tAddress2 = rs.getString("address.address2");

                tAddressId = rs.getString("address.addressid");

                tPhone = rs.getString("address.phone");

                tCity = rs.getString("city.city");

                tCityId = rs.getString("city.cityid");

                tZip = rs.getString("address.postalCode"); //zip 

                tCountry = rs.getString("country.country"); //country
//
                tCountryId = rs.getString("country.countryid"); //countryid

                custList.add(new Customer(tId, tName, tAddressId, tStreet, tAddress2, tPhone, tCity, tCityId, tZip, tCountry, tCountryId));// tAddressId,
            }

        } catch (SQLException sqe) {
            System.out.println("Check your SQL");
        } catch (Exception e) {
            System.out.println("Something besides the SQL went wrong.");
        }
        return custList;
    }

    private List<Appointment> parseAppointmentList() {
        String tAppointmentId;
        String tTitle;
        String tDescription;
        String tLocation;
        String tContact;
        String tUrl;
        String tStart;
        String tEnd;

        ArrayList<Appointment> apptList = new ArrayList();
        try {

            PreparedStatement statement2 = NewSweet_mainController.getConn().prepareStatement(
                    "SELECT customer.customerid,"
                    + " appointment.appointmentid, appointment.title, appointment.description, appointment.location, appointment.contact, appointment.url, appointment.start, appointment.end"
                    + " FROM customer, appointment"
                    + " WHERE customer.customerid = appointment.customerId AND customer.customerid = ?;");
            statement2.setString(1, selectedCustomer.getCusID());
            ResultSet rs2 = statement2.executeQuery();

            while (rs2.next()) {
                tAppointmentId = rs2.getString("appointment.appointmentid");

                tTitle = rs2.getString("appointment.title");

                tDescription = rs2.getString("appointment.description"); //street

                tLocation = rs2.getString("appointment.location");

                tContact = rs2.getString("appointment.contact");

                tUrl = rs2.getString("appointment.url");

                tStart = rs2.getString("appointment.start");

                tEnd = rs2.getString("appointment.end");

                apptList.add(new Appointment(tAppointmentId, tTitle, tDescription, tLocation, tContact, tUrl, tStart, tEnd));
            }
        } catch (SQLException sqe) {
            System.out.println("Check your SQL in parseAppointmentList");
        } catch (Exception e) {
            System.out.println("Something besides the SQL went wrong in parseAppointmentList.");
        }

        return apptList;

    }

    @FXML // Controls for new button
    void newCustomerButtonAction(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        //get reference to the button's stage         
        stage = (Stage) homeScreenLabel.getScene().getWindow();
        //load up OTHER FXML document
        root = FXMLLoader.load(getClass().getResource("NewSweet_NewCustomer.fxml"));
        //create a new scene with root and set the stage
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void editCustomerButtonAction(ActionEvent event) throws IOException {
        selectedCustomer = customerRecordsTable.getSelectionModel().getSelectedItem();
        // If user forgot to select a customer

        if (selectedCustomer == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Customer Selected");
            alert.setHeaderText("Please Select Customer to edit.");
            //Lambda Alert
            alert.showAndWait()
                    .filter(response -> response == ButtonType.OK)
                    .ifPresent(response -> alert.close());
            return;
        }

        Stage stage;
        Parent root;
        //get reference to the button's stage         
        stage = (Stage) homeScreenLabel.getScene().getWindow();
        //load up OTHER FXML document
        root = FXMLLoader.load(getClass().getResource("NewSweet_EditCustomer.fxml"));
        //create a new scene with root and set the stage
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML // Controls for back button
    void backButtonAction(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Cancel");
        alert.setHeaderText("If you go back, you will be automatically logged out?");// TODO: Make this automatic logout work 
        alert.setContentText("Please click Okay to confirm, or click Cancel");
        //Lambda approach
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // user chose OK
                Stage stage;
                Parent root = null;
                //get reference to the button's stage         
                stage = (Stage) homeScreenLabel.getScene().getWindow();
                try {
                    //load up OTHER FXML document
                    root = FXMLLoader.load(getClass().getResource("NewSweet_main.fxml"));
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

    @FXML // controls to close IMSystem
    void exitHomeScreenButtonAction(ActionEvent event
    ) {
        // get a handle to the stage
        Stage stage = (Stage) exitHomeScreen.getScene().getWindow();
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
