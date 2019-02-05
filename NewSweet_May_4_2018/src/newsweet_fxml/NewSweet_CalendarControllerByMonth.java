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
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import newsweet_classes.Appointment;
import static newsweet_fxml.NewSweet_homeController.parseCustomerList;
import static newsweet_fxml.NewSweet_homeController.selectedCustomer;

/**
 * FXML Controller class
 *
 * @author there
 */
public class NewSweet_CalendarControllerByMonth implements Initializable {

    // fx id's
    @FXML
    private Label calendarLabel;

    @FXML
    private Button calendarBackButton;

    @FXML
    private Button calendarExitButton;

    @FXML
    private TableView<Appointment> calendarTop;

    @FXML
    private TableColumn<Appointment, String> idtopCal;

    @FXML
    private TableColumn<Appointment, String> titleTopCal;

    @FXML
    private TableColumn<Appointment, String> descriptionTopCal;

    @FXML
    private TableColumn<Appointment, String> locationTopCal;

    @FXML
    private TableColumn<Appointment, String> contactTopCal;

    @FXML
    private TableColumn<Appointment, String> urlTopCal;

    @FXML
    private TableColumn<Appointment, String> startTopCal;

    @FXML
    private TableColumn<Appointment, String> endTopCal;

    

  
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // columns for top table for Calendar Views
        //idColAppointments
        idtopCal.setCellValueFactory(cellData
                -> cellData.getValue().appointmentIdProperty());
        //titleColAppointments
        titleTopCal.setCellValueFactory(cellData
                -> cellData.getValue().titleProperty());
        //descriptionColAppointments
        descriptionTopCal.setCellValueFactory(cellData
                -> cellData.getValue().descriptionProperty());
        //locationColAppointments
        locationTopCal.setCellValueFactory(cellData
                -> cellData.getValue().locationProperty());
        //contactColAppointments
        contactTopCal.setCellValueFactory(cellData
                -> cellData.getValue().contactProperty());
        //urlColAppointments
        urlTopCal.setCellValueFactory(cellData
                -> cellData.getValue().urlProperty());
        //startColAppointments
        startTopCal.setCellValueFactory(cellData
                -> cellData.getValue().startProperty());
        //endColAppointments
        endTopCal.setCellValueFactory(cellData
                -> cellData.getValue().endProperty());

        calendarTop.getItems().setAll(parseAppointmentList1());
        // columns for top table for Calendar Views
    }

    //TODO: Order By Month
    @FXML
    private List<Appointment> parseAppointmentList1() {
        String topId;
        String topTitle;
        String topDescription;
        String topLocation;
        String topContact;
        String topUrl;
        String topStart;
        String topEnd;

        ArrayList<Appointment> apptList1 = new ArrayList();
        try {

            PreparedStatement statement2 = NewSweet_mainController.getConn().prepareStatement(
                    "SELECT appointmentid, customerid, title, description, "
                    + "location, contact, url, start, end FROM appointment "
                    + "order by month(start);");

            ResultSet rs2 = statement2.executeQuery();

            while (rs2.next()) {
                topId = rs2.getString("appointment.appointmentid");

                topTitle = rs2.getString("appointment.title");

                topDescription = rs2.getString("appointment.description"); //street

                topLocation = rs2.getString("appointment.location");

                topContact = rs2.getString("appointment.contact");

                topUrl = rs2.getString("appointment.url");

                topStart = rs2.getString("appointment.start");

                topEnd = rs2.getString("appointment.end");

                apptList1.add(new Appointment(topId, topTitle, topDescription, topLocation, topContact, topUrl, topStart, topEnd));
            }
        } catch (SQLException sqe) {
            System.out.println("Check your SQL in parseAppointmentList1");
        } catch (Exception e) {
            System.out.println("Something besides the SQL went wrong in parseAppointmentList1.");
        }

        return apptList1;

    }

    @FXML
    void calendarBackButtonAction(ActionEvent event) throws IOException {

        Stage stage;
        Parent root = null;
        //get reference to the button's stage         
        stage = (Stage) calendarLabel.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("NewSweet_home.fxml"));
        //create a new scene with root and set the stage
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void calendarExitButtonAction(ActionEvent event) throws IOException {
        // get a handle to the stage
        Stage stage = (Stage) calendarLabel.getScene().getWindow();
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
