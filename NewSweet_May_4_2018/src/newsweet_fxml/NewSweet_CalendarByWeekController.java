/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newsweet_fxml;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import newsweet_classes.Appointment;

/**
 * FXML Controller class
 *
 * @author there
 */
public class NewSweet_CalendarByWeekController implements Initializable {

    @FXML
    private Button calendar2BackButton;

    @FXML
    private Label calendarLabel2;
    
    @FXML
    private Button calendarExitButton2;

    @FXML
    private TableView<Appointment> calendarBottom;

    @FXML
    private TableColumn<Appointment, String> idBottomCal;

    @FXML
    private TableColumn<Appointment, String> titleBottomCal;

    @FXML
    private TableColumn<Appointment, String> descriptionBottomCal;

    @FXML
    private TableColumn<Appointment, String> locationBottomCal;

    @FXML
    private TableColumn<Appointment, String> contactBottomCal;

    @FXML
    private TableColumn<Appointment, String> urlBottomCal;

    @FXML
    private TableColumn<Appointment, String> startBottomCal;

    @FXML
    private TableColumn<Appointment, String> endBottomCal;

    //TODO: Order By WEEK
    @FXML
    private List<Appointment> parseAppointmentList2() {
        String bottomId;
        String bottomTitle;
        String bottomDescription;
        String bottomLocation;
        String bottomContact;
        String bottomURL;
        String bottomStart;
        String bottomEnd;

        ArrayList<Appointment> apptList2 = new ArrayList();
        try {

            PreparedStatement statement2 = NewSweet_mainController.getConn().prepareStatement(
                    "SELECT appointmentid, customerid, title, description, "
                    + "location, contact, url, start, end FROM appointment "
                    + "order by week(start), day(start);");

            ResultSet rs2 = statement2.executeQuery();

            while (rs2.next()) {
                bottomId = rs2.getString("appointment.appointmentid");

                bottomTitle = rs2.getString("appointment.title");

                bottomDescription = rs2.getString("appointment.description"); //street

                bottomLocation = rs2.getString("appointment.location");

                bottomContact = rs2.getString("appointment.contact");

                bottomURL = rs2.getString("appointment.url");

                bottomStart = rs2.getString("appointment.start");

                bottomEnd = rs2.getString("appointment.end");

                apptList2.add(new Appointment(bottomId, bottomTitle, bottomDescription, bottomLocation, bottomContact, bottomURL, bottomStart, bottomEnd));
            }
        } catch (SQLException sqe) {
            System.out.println("Check your SQL in parseAppointmentList2");
            sqe.printStackTrace();
        } catch (Exception e) {
            System.out.println("Something besides the SQL went wrong in parseAppointmentList2.");
        }

        return apptList2;

    }

    @FXML
    void calendar2BackButtonAction(ActionEvent event) throws IOException {
        Stage stage;
        Parent root = null;
        //get reference to the button's stage         
        stage = (Stage) calendarLabel2.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("NewSweet_home.fxml"));
        //create a new scene with root and set the stage
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
      @FXML
    void calendarExitButtonAction2(ActionEvent event) throws IOException {
        // get a handle to the stage
        Stage stage = (Stage) calendarLabel2.getScene().getWindow();
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // columns for bottom table for Calendar Views
        //idColAppointments
        idBottomCal.setCellValueFactory(cellData
                -> cellData.getValue().appointmentIdProperty());
        //titleColAppointments
        titleBottomCal.setCellValueFactory(cellData
                -> cellData.getValue().titleProperty());
        //descriptionColAppointments
        descriptionBottomCal.setCellValueFactory(cellData
                -> cellData.getValue().descriptionProperty());
        //locationColAppointments
        locationBottomCal.setCellValueFactory(cellData
                -> cellData.getValue().locationProperty());
        //contactColAppointments
        contactBottomCal.setCellValueFactory(cellData
                -> cellData.getValue().contactProperty());
        //urlColAppointments
        urlBottomCal.setCellValueFactory(cellData
                -> cellData.getValue().urlProperty());
        //startColAppointments
        startBottomCal.setCellValueFactory(cellData
                -> cellData.getValue().startProperty());
        //endColAppointments
        endBottomCal.setCellValueFactory(cellData
                -> cellData.getValue().endProperty());

        calendarBottom.getItems().setAll(parseAppointmentList2());

    }

}
