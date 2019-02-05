/*Software II - Advanced Java Concepts (UG, C195, GZP1-0217) */
package newsweet_fxml;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import newsweet_classes.Appointment;
import newsweet_classes.Customer;
import static newsweet_fxml.NewSweet_homeController.parseCustomerList;

/**
 * RUBRIC POINT: I. REPORTS The application has functionality to generate each
 * of the given reports. The code is complete and functions properly.
 *
 * • number of appointment types by month • the schedule for each consultant •
 * one additional report of your choice
 *
 * @author Therese Parks
 */
public class NewSweet_ViewReportsController implements Initializable {

    @FXML
    private AnchorPane anchorReports;

    @FXML
    private TextArea reportsTextField;

    @FXML
    private ToggleGroup report_choices;

    @FXML
    private RadioButton appointmentAmountRadio;

    @FXML
    private RadioButton scheduleRadio;

    @FXML
    private RadioButton numCusRadio;

    @FXML
    private TextField locationSchedule;

    @FXML
    private TextField titleSchedule;

    @FXML
    private TextField startSchedule;

    @FXML
    private Button backButtonReports;

    @FXML
    private Button exitReports;

    @FXML
    void backButtonReportsButtonAction(ActionEvent event) throws IOException {

        // user chose OK
        Stage stage;
        Parent root;
        //get reference to the button's stage         
        stage = (Stage) backButtonReports.getScene().getWindow();
        //load up OTHER FXML document
        root = FXMLLoader.load(getClass().getResource("NewSweet_home.fxml"));
        //create a new scene with root and set the stage
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void exitReportsButtonAction(ActionEvent event) {
        // get a handle to the stage
        Stage stage = (Stage) anchorReports.getScene().getWindow();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Exit");
        alert.setHeaderText("Are you sure you want to exit netSweet?");
        alert.setContentText("Please click Okay to confirm, or click Cancel");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            // user chose OK
            stage.close();

        } else {
            // user chose CANCEL or closed the dialog
            alert.close();
        }

    }

    @FXML
    void scheduleRadioAction(ActionEvent event) throws SQLException, IOException {
        String title;
        String location;
        String start;
        String end;
        List list = new ArrayList();
        try {
            Statement s = NewSweet_mainController.getConn().createStatement();
            // Pull correct address id from address table and store it in a result 
            // set which is then stored in a String variable to use add to the customer table
            ResultSet rs = s.executeQuery(
                    "SELECT appointment.title, appointment.location, appointment.start,"
                    + " appointment.end FROM appointment WHERE appointment.contact = '" + NewSweet_mainController.loggedInUser + "' ");
            StringBuffer output = new StringBuffer();
            while (rs.next()) {

                title = rs.getString("appointment.title");
                location = rs.getString("appointment.location");
                start = rs.getString("appointment.start");

                list.add(title);
                list.add(location);
                list.add(start);
                output.append(title + " " + location + " " + start + " \n");
            }
            reportsTextField.setText(output.toString());
            // sheduleTableView.getItems().
        } catch (SQLException sqe) {
            System.out.println("Check your SQL for insterting into address table");
            sqe.printStackTrace();
        } catch (Exception e) {
            System.out.println("Something besides the SQL went wrong while inserting into address table.");
        }
    }

    @FXML
    void appointmentAmountRadioAction(ActionEvent event) throws IOException {

        try {
            PreparedStatement statement = NewSweet_mainController.getConn().prepareStatement(
                    "SELECT YEAR(start) as year_val, MONTH(start) as month_val, "
                    + "COUNT(*) as total "
                    + "FROM appointment "
                    + "GROUP BY MONTH(start), YEAR(start);");
            ResultSet rs = statement.executeQuery();

            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();

            StringBuffer output = new StringBuffer();
            while (rs.next()) {

                int i;
                for (i = 1; i <= columnsNumber; i++) {

                    output.append(rs.getString(i) + " ");

                }
                output.append("\n");

                System.out.println();
            }
            reportsTextField.setText(output.toString());
        } catch (SQLException sqe) {
            System.out.println("Check your SQL");
            sqe.printStackTrace();

        } catch (Exception e) {
            System.out.println("Something besides the SQL went wrong.");

        }

    }

    @FXML
    void numCusRadioAction(ActionEvent event) throws SQLException, IOException {

        try (
                PreparedStatement statement = NewSweet_mainController.getConn().prepareStatement(
                        "SELECT COUNT(customerid) "
                        + "FROM customer;");
                ResultSet rs = statement.executeQuery();) {

            rs.next();

            reportsTextField.setText("There are Currently " + rs.getString(1) + " Customers in NewSweet!");

        } catch (SQLException sqe) {
            System.out.println("Check your SQL");
            sqe.printStackTrace();
        } catch (Exception e) {
            System.out.println("Something besides the SQL went wrong.");
        }

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
}
