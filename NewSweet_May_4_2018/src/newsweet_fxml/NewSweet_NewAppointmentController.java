/*Software II - Advanced Java Concepts (UG, C195, GZP1-0217) */
package newsweet_fxml;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.*; // import time classes
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javax.swing.DefaultComboBoxModel;
import newsweet_classes.Customer;
import static newsweet_fxml.NewSweet_homeController.selectedCustomer;
import static newsweet_fxml.NewSweet_homeController.parseCustomerList;

/**
 * RUBRIC POINT C. APPOINTMENTS The application code includes lambda expressions
 * to schedule and maintain appointments, capture the type of appointment, and
 * link the appointments to the specific customer record in the database. The
 * code is complete and functions properly.
 *
 * RUBRIC POINT F. EXCEPTION CONTROLS The application code includes exception
 * controls to prevent each of the given points and uses at least 2 different
 * mechanisms. The code is complete and functions properly.
 *
 * RUBRIC POINT: G. POP-UPS The application code uses lambda expressions to
 * create standard pop-up and alert messages. The code is complete and functions
 * properly.
 *
 * @author Therese Parks
 */
public class NewSweet_NewAppointmentController extends NewSweet_homeController implements Initializable {

    @FXML
    private Label newAppointmentLabel;

    @FXML
    private TextField titleAppointment;//

    @FXML
    private TextField descriptionAppointment;

    @FXML
    private TextField locationAppointment;

    @FXML
    private TextField contactAppointments;

    @FXML
    private TextField urlAppointment;

    @FXML
    private ComboBox<String> dayAppointment;

    @FXML
    private DatePicker dayDatePicker;

    @FXML
    private ComboBox<String> startAppointment;

    @FXML
    private ComboBox<String> endAppointment;

    @FXML
    private Button saveAppointmentButton;

    @FXML
    private Button exitNewAppointmentButton;

    @FXML
    private Button resetNewCustomer;

    @FXML
    private Button cancelAddAppointmentButton;

    @FXML
    private Text apptErrorText;

    @FXML  // Insert into DB
    void saveAppointmentButtonAction(ActionEvent event) throws IOException, SQLException {
        String title = titleAppointment.getText();
        String description = descriptionAppointment.getText();
        String location = locationAppointment.getText();
        String contact = contactAppointments.getText();
        String url = urlAppointment.getText();

        List<String> validateAddress = new ArrayList();
        validateAddress.add(title);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");

        try {
            if (startAppointment.getSelectionModel().getSelectedItem() == null
                    || endAppointment.getSelectionModel().getSelectedItem() == null
                    || dayDatePicker.getValue() == null) {
                System.out.print("Null Date or Time");
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Invalid Customer Information!");
                alert.setHeaderText("Must Select date, start time, and end time.");
                //Lambda Alert
                alert.showAndWait()
                        .filter(response -> response == ButtonType.OK)
                        .ifPresent(response -> alert.close());
                return;
            }
        } catch (NullPointerException ne) {
            System.out.println("Null Date or Time Values");
        }

        LocalTime startTime = LocalTime.parse(startAppointment.getSelectionModel().getSelectedItem());
        LocalTime endTime = LocalTime.parse(endAppointment.getSelectionModel().getSelectedItem());
        LocalDate dayofAppointment = dayDatePicker.getValue();

        LocalDateTime startLdt = LocalDateTime.of(dayofAppointment, startTime);
        LocalDateTime endLdt = LocalDateTime.of(dayofAppointment, endTime);

        String startPlusDay = dayofAppointment.toString() + " " + startTime.toString();
        String endPlusDay = dayofAppointment.toString() + " " + endTime.toString();
        // Pull out the start and end time(with dates)

//        LocalDateTime now = LocalDateTime.now();
//        System.out.println("Current LocalDateTime" + dtf.format(now));
//        
        Locale locale = new Locale("fr", "FR");
        // set Default Locale to France

        LocalDate today = LocalDate.now();
        // System.out.println("LocaleDate " + today);

        LocalTime time = LocalTime.now();
        //System.out.println("LocalTime " + time);

        LocalDateTime currentDatTime = LocalDateTime.now();
        //System.out.println("Current Date and Time = " + currentDatTime);

        ZonedDateTime zoneddt = ZonedDateTime.now();
        //  System.out.println("Currrent Zoned Date Time =  "+ zoneddt);

        String start;
        String end;
        List list = new ArrayList();

        //---------------------------Validate user input--------------------------------//
        //titleAppointment
        if (titleAppointment.getText() == null || titleAppointment.getText().length() < 2) {
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

        } else if (titleAppointment.getText().length() > 25) {
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

        } else //descriptionAppointment
        if (descriptionAppointment.getText() == null || descriptionAppointment.getText().length() < 2) {
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

        } else if (descriptionAppointment.getText().length() > 25) {
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

        } else //locationAppointment
        if (locationAppointment.getText() == null || locationAppointment.getText().length() < 2) {
            System.out.println("location : must have more than 2 characters.");
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

        } else if (locationAppointment.getText().length() > 25) {
            System.out.println("location : must have less than 25 characters.");
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
        if (contactAppointments.getText() == null || contactAppointments.getText().length() < 2) {
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

        } else if (contactAppointments.getText().length() > 30) {
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

        }

        try {
            Statement s = NewSweet_mainController.getConn().createStatement();
            // Pull correct address id from address table and store it in a result set
            // which is then stored in a String variable to use add to the customer table
            ResultSet rs = s.executeQuery(
                    "SELECT appointment.title, appointment.start, appointment.end "
                    + "FROM appointment WHERE appointment.contact = '" + NewSweet_mainController.loggedInUser + "' "
                    + "AND appointment.start < '" + startPlusDay + "' AND appointment.end > '" + startPlusDay + "' "
                    + "OR appointment.end < '" + startPlusDay + "' AND appointment.end > '" + endPlusDay + "' "
                    + "OR appointment.start < '" + startPlusDay + "' AND appointment.end > '" + startPlusDay + "' "
                    + "OR appointment.end > '" + endPlusDay + "' AND appointment.start < '" + endPlusDay + "' "
                    + "OR appointment.start > '" + startPlusDay + "' AND appointment.end < '" + endPlusDay + "' ");//put the mySql version of conditions here

            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            StringBuffer output = new StringBuffer();//use append
            LocalDateTime timeNow = LocalDateTime.now();
            while (rs.next()) {

                //appointment values
                title = rs.getString("appointment.title");
                start = rs.getString("appointment.start");
                end = rs.getString("appointment.end");
                String thisTimeDay = LocalDateTime.now().toString();
                //Convert String into LDT in order to manipulate as LDT(impossible to do as Strings).
                ArrayList list2 = new ArrayList();
                LocalDateTime alreadyScheduledStart = LocalDateTime.parse(start, dtf);
                LocalDateTime alreadyScheduledEnd = LocalDateTime.parse(end, dtf);
                LocalDateTime newScheduleStart = startLdt;
                LocalDateTime newScheduleEnd = endLdt;

                list2.add(alreadyScheduledStart);
                list2.add(alreadyScheduledEnd);

                for (int i = 1; i <= list2.size(); i++) {

                    //condition:1
                    //appointment.start < startPlusDay AND appointment.end > startPlusDay
                    if (newScheduleStart.isAfter(alreadyScheduledStart)
                            && newScheduleStart.isBefore(alreadyScheduledEnd)
                            && newScheduleEnd.isAfter(alreadyScheduledEnd)) {
                        System.out.print("overlapping times!! condition: 1 " + "\n");
                        apptErrorText.setText("Overlapping Appointments!! Check your schedule.");
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Cannot Schedule Appointment!! Check your schudule.");
                        alert.setHeaderText("You already have a scheduled appointment that interferes with these selected times!");// TODO: Make this automatic logout work 
                        //Lambda Alert
                        alert.showAndWait()
                                .filter(response -> response == ButtonType.OK)
                                .ifPresent(response -> alert.close());
                        return;
                    } else {
                        //condition:2
                        //appointment.end < startPlusDay AND appointment.end > endPlusDay
                        if (newScheduleStart.isBefore(alreadyScheduledStart)
                                && newScheduleEnd.isAfter(alreadyScheduledStart)
                                && newScheduleEnd.isBefore(alreadyScheduledEnd)) {
                            System.out.print("overlapping times!! condition: 2 " + "\n");
                            apptErrorText.setText("Overlapping Appointments!! Check your schedule.");
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("Cannot Schedule Appointment!! Check your schudule.");
                            alert.setHeaderText("You already have a scheduled appointment that interferes with these selected times!");// TODO: Make this automatic logout work 
                            //Lambda Alert
                            alert.showAndWait()
                                    .filter(response -> response == ButtonType.OK)
                                    .ifPresent(response -> alert.close());
                            return;
                        } else {
                            //condition:3
                            //appointment.start < startPlusDay AND appointment.end > startPlusDay
                            //AND appointment.end > endPlusDay AND appointment.start < endPlusDay
                            if (newScheduleStart.isAfter(alreadyScheduledStart)
                                    && newScheduleStart.isBefore(alreadyScheduledEnd)
                                    && newScheduleEnd.isBefore(alreadyScheduledEnd)
                                    && newScheduleEnd.isAfter(alreadyScheduledStart)) {
                                System.out.print("overlapping times!! condition: 3 " + "\n");
                                apptErrorText.setText("Overlapping Appointments!! Check your schedule.");
                                Alert alert = new Alert(Alert.AlertType.WARNING);
                                alert.setTitle("Cannot Schedule Appointment!! Check your schudule.");
                                alert.setHeaderText("You already have a scheduled appointment that interferes with these selected times!");// TODO: Make this automatic logout work 
                                //Lambda Alert
                                alert.showAndWait()
                                        .filter(response -> response == ButtonType.OK)
                                        .ifPresent(response -> alert.close());
                                return;
                            } else {
                                //condition:4
                                //appointment.start > startPlusDay AND appointment.end < endPlusDay 
                                if (newScheduleStart.isBefore(alreadyScheduledStart)
                                        && newScheduleEnd.isAfter(alreadyScheduledEnd)) {
                                    System.out.print("overlapping times!! condition: 4 " + "\n");
                                    apptErrorText.setText("Overlapping Appointments!! Check your schedule.");
                                    Alert alert = new Alert(Alert.AlertType.WARNING);
                                    alert.setTitle("Cannot Schedule Appointment!! Check your schudule.");
                                    alert.setHeaderText("You already have a scheduled appointment that interferes with these selected times!");// TODO: Make this automatic logout work 
                                    //Lambda Alert
                                    alert.showAndWait()
                                            .filter(response -> response == ButtonType.OK)
                                            .ifPresent(response -> alert.close());
                                    return;
                                } else {
                                    //condition:5
                                    //appointment.start > startPlusDay AND appointment.end < endPlusDay 
                                    if (newScheduleStart.isBefore(timeNow)
                                            || newScheduleEnd.isBefore(timeNow)
                                            || newScheduleEnd.isBefore(newScheduleStart)
                                            || newScheduleEnd.equals(timeNow)
                                            || newScheduleStart.compareTo(newScheduleEnd) == 0) {
                                        System.out.print("invalid Times! : condition: 5 " + "\n");
                                        apptErrorText.setText("Invalid Start or End times!");
                                        Alert alert = new Alert(Alert.AlertType.WARNING);
                                        alert.setTitle("Cannot Schedule Appointment!! ");
                                        alert.setHeaderText("The times you have chosen are impossible or invalid.");// TODO: Make this automatic logout work 
                                        //Lambda Alert
                                        alert.showAndWait()
                                                .filter(response -> response == ButtonType.OK)
                                                .ifPresent(response -> alert.close());
                                        return;
                                    }
                                }

                            }
                        }
                    }

                }//end for

            }//end while

        } catch (SQLException sqe) {
            System.out.println("Check your SQL overlapping times");
            sqe.printStackTrace();
        } catch (Exception e) {
            System.out.println("Something besides the SQL went wrong overlapping times.");
            e.printStackTrace();
        }

        ///////////////////////////////////////////////////////////////////
        try {
            //***********************NEW APPOINTMENT***************************//
            Statement s = NewSweet_mainController.getConn().createStatement();
            // Pull correct address id from address table and store it in a result set which is then stored in a String variable to use add to the customer table
            ResultSet rs = s.executeQuery("SELECT customer.customerid FROM customer WHERE customer.customerid = '" + selectedCustomer.getCusID() + "'");

            while (rs.next()) {
                String customerID = rs.getString("customer.customerid");
                //-----------------------------------------------------------//
                PreparedStatement ps = NewSweet_mainController.getConn().prepareStatement(
                        "INSERT INTO appointment(customerId, title, description, location, contact, url, start, end, createDate, createdBy, lastUpdate, lastUpdateBy) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
                ps.setString(1, customerID);
                ps.setString(2, title);
                ps.setString(3, description);
                ps.setString(4, location);
                ps.setString(5, contact);
                ps.setString(6, url);
                ps.setString(7, startPlusDay);// start
                ps.setString(8, endPlusDay);//end
                ps.setString(9, LocalDateTime.now().toString()); //createDate
                ps.setString(10, NewSweet_mainController.loggedInUser);//createBy
                ps.setString(11, LocalDateTime.now().toString());//lastUpdateBy
                ps.setString(12, NewSweet_mainController.loggedInUser);
                ps.executeUpdate();
//       
            }
        } catch (SQLException sqe) {
            System.out.println("Check your SQL in parseAppointmentList");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Something besides the SQL went wrong in parseAppointmentList.");
        }
        Stage stage;
        Parent root;
        //get reference to the button's stage         
        stage = (Stage) newAppointmentLabel.getScene().getWindow();
        //load up OTHER FXML document
        root = FXMLLoader.load(getClass().getResource("NewSweet_home.fxml"));
        //create a new scene with root and set the stage
        Scene scene = new Scene(root);

        stage.setScene(scene);

        stage.show();

    }

    @FXML
    void cancelAddAppointmentAction(ActionEvent event) throws IOException {
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
                stage = (Stage) newAppointmentLabel.getScene().getWindow();
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

    @FXML
    void exitNewAppointmentButtonAction(ActionEvent event) throws IOException {
        // get a handle to the stage
        Stage stage = (Stage) exitNewAppointmentButton.getScene().getWindow();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Exit");
        alert.setHeaderText("Changes may not be saved. Are you sure you want to exit netSweet ?");
        alert.setContentText("Please click Okay to confirm, or click Cancel");

        //Lambda Alert
        alert.showAndWait()
                .filter(response -> response == ButtonType.OK)
                .ifPresent(response -> alert.close());
        return;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb
    ) {
        //To Fill the Values in dayDatePicker 
        Customer selectedCustomer = NewSweet_homeController.selectedCustomer;
        LocalDate localDate = dayDatePicker.getValue();

        //Lambda
        dayDatePicker.setOnAction(event -> {
            LocalDate date = dayDatePicker.getValue();
            System.out.println("Selected date: " + date);
        });

        dayDatePicker.setShowWeekNumbers(true);

        //Set the Pattern for date
        String pattern = "yyyy-MM-dd";

        dayDatePicker.setPromptText(pattern.toLowerCase());

        dayDatePicker.setConverter(new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });

        //To Fill the Values for startDateTime and endDateTime
        String[] times = {"08:00:00", "08:15:00", "08:30:00", "08:45:00", "09:00:00", "09:15:00", "09:30:00", "09:45:00", "10:00:00",
            "10:15:00", "10:30:00", "10:45:00", "11:00:00", "11:15:00", "11:30:00", "11:45:00", "12:00:00", "12:15:00", "12:30:00", "12:45:00",
            "13:00:00", "13:15:00", "13:30:00", "13:45:00", "14:00:00", "14:15:00", "14:30:00", "14:45:00", "15:00:00", "15:15:00",
            "15:30:00", "15:45:00", "16:00:00", "16:15:00", "16:30:00"};
        //To Fill the start Time ComboBox

        try {

            for (String str : times) {
                startAppointment.getItems().addAll(str);
                endAppointment.getItems().addAll(str);
            }

        } catch (Exception e) {
            System.out.println("Something went wrong with startAppointment Combo Box");
        }
    }

}
