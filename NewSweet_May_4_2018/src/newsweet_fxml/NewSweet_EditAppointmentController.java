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
import newsweet_classes.Appointment;
import newsweet_classes.Customer;
import static newsweet_fxml.NewSweet_homeController.selectedCustomer;
import static newsweet_fxml.NewSweet_homeController.parseCustomerList;

/**
 * RUBRIC POINT C. APPOINTMENTS The application code includes lambda expressions
 * to schedule and maintain appointments, capture the type of appointment, and
 * link the appointments to the specific customer record in the database. The
 * code is complete and functions properly.
 *
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
public class NewSweet_EditAppointmentController extends NewSweet_homeController implements Initializable {

    @FXML
    private Label editAppointmentLabel;
    @FXML
    private Text oldStartText;

    @FXML
    private Text oldEndText;

    @FXML
    private TextField titleEdit;

    @FXML
    private TextField descriptionEdit;

    @FXML
    private TextField locationEdit;

    @FXML
    private TextField contactEdit;

    @FXML
    private TextField urlEdit;

    @FXML
    private DatePicker dayPicker;

    @FXML
    private ComboBox<String> startTimeCB;

    @FXML
    private ComboBox<String> endTimeCB;

    @FXML
    private Button saveEditAppointment;

    @FXML
    private Button exitNewAppointmentButton;

    @FXML
    private Button resetEditButton;

    @FXML
    private Button cancelEditAppointment;

    @FXML
    private Text appointmentErrText;

    @FXML  // Insert into DB
    void saveEditAppointmentButtonAction(ActionEvent event) throws IOException, SQLException {
        String title = titleEdit.getText();
        String description = descriptionEdit.getText();
        String location = locationEdit.getText();
        String contact = contactEdit.getText();
        String url = urlEdit.getText();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");

        try {
            if (startTimeCB.getSelectionModel().getSelectedItem() == null
                    || endTimeCB.getSelectionModel().getSelectedItem() == null
                    || dayPicker.getValue() == null) {
                System.out.print("Null Date or Time");
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Invalid Customer Information!");
                alert.setHeaderText("Must Select date, start time, and end time.");
                alert.showAndWait()
                        .filter(response -> response == ButtonType.OK)
                        .ifPresent(response -> alert.close());
                return;
            }
        } catch (NullPointerException ne) {
            System.out.println("Null Date or Time Values");
        }

        //---------------------------Validate user input--------------------------------//
        //titleAppointment
        if (titleEdit.getText() == null || titleEdit.getText().length() < 2) {
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
        } else if (titleEdit.getText().length() > 25) {
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
        if (descriptionEdit.getText() == null || descriptionEdit.getText().length() < 2) {
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

        } else if (descriptionEdit.getText().length() > 25) {
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
        if (locationEdit.getText() == null || locationEdit.getText().length() < 2) {
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

        } else if (locationEdit.getText().length() > 25) {
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
        if (contactEdit.getText() == null || contactEdit.getText().length() < 2) {
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

        } else if (contactEdit.getText().length() > 30) {
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

        //Check for day and times being null
        if (dayPicker.getValue() == null) {
            System.out.println("Choose Day.");
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

        } else if (startTimeCB.getValue() == null) {
            System.out.println("Choose a start time.");
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
        if (endTimeCB.getValue() == null) {
            System.out.println("Choose a end time.");
            //  apptErrorText.setText("Choose end time.");
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
        LocalTime startTime = LocalTime.parse(startTimeCB.getSelectionModel().getSelectedItem());
        LocalTime endTime = LocalTime.parse(endTimeCB.getSelectionModel().getSelectedItem());
        LocalDate dayofAppointment = dayPicker.getValue();

        LocalDateTime startLdt = LocalDateTime.of(dayofAppointment, startTime);
        LocalDateTime endLdt = LocalDateTime.of(dayofAppointment, endTime);

        String startPlusDay = dayofAppointment.toString() + " " + startTime.toString();
        String endPlusDay = dayofAppointment.toString() + " " + endTime.toString();
        // Pull out the start and end time(with dates)

        LocalDateTime now = LocalDateTime.now();
        System.out.println(dtf.format(now));
        String start;
        String end;
        List list = new ArrayList();

        try {
            Statement s = NewSweet_mainController.getConn().createStatement();
            // Pull correct address id from address table and store it in a 
            // result set which is then stored in a String variable to use add to the customer table
            ResultSet rs = s.executeQuery(
                    "SELECT appointment.title, appointment.start, appointment.end "
                    + "FROM appointment WHERE appointment.contact = '" + NewSweet_mainController.loggedInUser + "' "
                    + "AND appointment.start < '" + startPlusDay + "' AND appointment.end > '" + startPlusDay + "' "
                    + "OR appointment.end < '" + startPlusDay + "' AND appointment.end > '" + endPlusDay + "' "
                    + "OR appointment.start < '" + startPlusDay + "' AND appointment.end > '" + startPlusDay + "' "
                    + "OR appointment.end > '" + endPlusDay + "' AND appointment.start < '" + endPlusDay + "' "
                    + "OR appointment.start > '" + startPlusDay + "' AND appointment.end < '" + endPlusDay + "' ");
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber2 = rsmd.getColumnCount();
            StringBuffer output = new StringBuffer();//use append
            while (rs.next()) {

                //appointment values
                title = rs.getString("appointment.title");
                start = rs.getString("appointment.start");
                end = rs.getString("appointment.end");
                //turn into LDT in order to manipulate as LDT(impossible to do as Strings).
                ArrayList list2 = new ArrayList();
                LocalDateTime alreadyScheduledStart = LocalDateTime.parse(start, dtf);
                LocalDateTime alreadyScheduledEnd = LocalDateTime.parse(end, dtf);
                LocalDateTime newScheduleStart = startLdt;
                LocalDateTime newScheduleEnd = endLdt;
                LocalDateTime timeNow = LocalDateTime.now();
                list2.add(alreadyScheduledStart);
                list2.add(alreadyScheduledEnd);

                for (int i = 1; i <= list2.size(); i++) {
                    // System.out.print(i);
                    //condition:1
                    //appointment.start < startPlusDay AND appointment.end > startPlusDay
                    if (newScheduleStart.isAfter(alreadyScheduledStart)
                            && newScheduleStart.isBefore(alreadyScheduledEnd)
                            && newScheduleEnd.isAfter(alreadyScheduledEnd)) {
                        System.out.print("overlapping times!! condition: 1 " + "\n");
                        appointmentErrText.setText("Overlapping Appointments!! Check your schedule.");
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
                            appointmentErrText.setText("Overlapping Appointments!! Check your schedule.");
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
                                appointmentErrText.setText("Overlapping Appointments!! Check your schedule.");
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
                                        && newScheduleEnd.isAfter(alreadyScheduledStart)
                                        && newScheduleEnd.isAfter(alreadyScheduledEnd)) {
                                    System.out.print("overlapping times!! condition: 4 " + "\n");
                                    appointmentErrText.setText("Overlapping Appointments!! Check your schedule.");
                                    Alert alert = new Alert(Alert.AlertType.WARNING);
                                    alert.setTitle("Cannot Schedule Appointment!! Check your schudule.");
                                    alert.setHeaderText("You already have a scheduled appointment that interferes with these selected times!");// TODO: Make this automatic logout work 
                                    //Lambda Alert
                                    alert.showAndWait()
                                            .filter(response -> response == ButtonType.OK)
                                            .ifPresent(response -> alert.close());

                                } else {
                                    //condition:5
                                    //appointment.start > startPlusDay AND appointment.end < endPlusDay 
                                    if (newScheduleStart.isBefore(timeNow)
                                            || newScheduleEnd.isBefore(timeNow)
                                            || newScheduleEnd.isBefore(newScheduleStart)
                                            || newScheduleEnd.equals(timeNow)
                                            || newScheduleStart.compareTo(newScheduleEnd) == 0) {
                                        System.out.print("invalid Times! : condition: 5 " + "\n");
                                        appointmentErrText.setText("Invalid Start or End times!");
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
            //}//end while
            // sheduleTableView.getItems().

            // sheduleTableView.getItems().
        } catch (SQLException sqe) {
            System.out.println("Check your SQL overlapping times");
            sqe.printStackTrace();
        } catch (Exception e) {
            System.out.println("Something besides the SQL went wrong overlapping times.");
            e.printStackTrace();
        }
        // TODO: Praise Jesus!! Next step is to make the text boxes auto formatted so user will enter the right format!!
        try {
            Statement s = NewSweet_mainController.getConn().createStatement();
            // Pull correct address id from address table and store it in a result set which is then stored in a String variable to use add to the customer table
            ResultSet rs = s.executeQuery("SELECT appointment.appointmentid FROM appointment WHERE appointment.appointmentid = '" + selectedAppointment.getAppointmentId() + "'");
            rs.next();
            String appointmentID = rs.getString(1);

            PreparedStatement ps = NewSweet_mainController.getConn().prepareStatement(
                    "UPDATE appointment SET "
                    + "title = ?, "
                    + "description = ?, "
                    + "location = ?, "
                    + "contact= ?, "
                    + "url  = ?, "
                    + "start = ?, "
                    + "end = ?, "
                    + "lastUpdate = ?, "
                    + "lastUpdateBy = ? WHERE appointment.appointmentid =  '" + appointmentID + "'");

            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, contact);
            ps.setString(5, url);
            ps.setString(6, startPlusDay);// start
            ps.setString(7, endPlusDay);//end
            ps.setString(8, LocalDateTime.now().toString());//lastUpdateBy
            ps.setString(9, NewSweet_mainController.loggedInUser);
            ps.executeUpdate();
        } catch (SQLException sqe) {
            System.out.println("Check your SQL for insterting into appointment table");
            sqe.printStackTrace();
        } catch (Exception e) {
            System.out.println("Something besides the SQL went wrong while inserting into appointment table.");
            e.printStackTrace();
        }

        // Take us back to the home screen
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Successfully Edited Appointment");
        alert.setHeaderText("You have successfully edited this Appointment!");// TODO: Make this automatic logout work 
        //  alert.setContentText("Please click Okay to confirm, or click Cancel");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            // alert.close();
            Stage stage;
            Parent root;
            //get reference to the button's stage         
            stage = (Stage) editAppointmentLabel.getScene().getWindow();
            //load up OTHER FXML document
            root = FXMLLoader.load(getClass().getResource("NewSweet_home.fxml"));
            //create a new scene with root and set the stage
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } else {
            // user chose CANCEL or closed the dialog
            alert.close();
        }

    }

    @FXML
    void cancelEditAppointmentButtonAction(ActionEvent event) throws IOException {
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
                stage = (Stage) editAppointmentLabel.getScene().getWindow();
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
    void exitEditAppointmentButtonAction(ActionEvent event) throws IOException {
        // get a handle to the stage
        Stage stage = (Stage) exitNewAppointmentButton.getScene().getWindow();
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
        //To Fill the Values in dayDatePicker 
        Customer selectedCustomer = NewSweet_homeController.selectedCustomer;
        Appointment selectedAppointment = NewSweet_homeController.selectedAppointment;

        titleEdit.setText(selectedAppointment.getTitle());
        descriptionEdit.setText(selectedAppointment.getDescription());
        locationEdit.setText(selectedAppointment.getLocation());
        contactEdit.setText(selectedAppointment.getContact());
        urlEdit.setText(selectedAppointment.getUrl());
        oldStartText.setText(selectedAppointment.getStart());
        oldEndText.setText(selectedAppointment.getEnd());

        LocalDate localDate = dayPicker.getValue();

        // Add some action (in Java 8 lambda syntax style).
        dayPicker.setOnAction(event -> {
            LocalDate date = dayPicker.getValue();
            System.out.println("Selected date: " + date);
        });

        dayPicker.setShowWeekNumbers(false);

        //Set the Pattern for date
        String pattern = "yyyy-MM-dd";

        dayPicker.setPromptText(pattern.toLowerCase());

        dayPicker.setConverter(new StringConverter<LocalDate>() {
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
                startTimeCB.getItems().addAll(str);
                endTimeCB.getItems().addAll(str);

            }
            // startAppointment.getSelectionModel().select();
        } catch (Exception e) {
            System.out.println("Something went wrong with startAppointment Combo Box");
        }
        //TODO: Add Exception to not allow user to schedule appointment if that user has an appointment already scheduled at that time. 
        // maybe pull from db, then check, then evaluate from there whether the user does or not. and throw exception if he does, otherwise let

    }

}
