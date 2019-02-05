/*Software II - Advanced Java Concepts (UG, C195, GZP1-0217) */
package newsweet_fxml;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import static java.util.Locale.US;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.TimeZone;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.tools.FileObject;
import newsweet_classes.Appointment;
import newsweet_classes.Reminder;
import static newsweet_fxml.NewSweet_homeController.selectedCustomer;

/**
 * RUBRIC POINT: A. LOGIN-IN FORM The log-in form has functionality to determine
 * the user’s location and translate log-in and error control messages into 2
 * languages. The code is complete and functions properly.
 *
 * RUBRIC POINT: F. EXCEPTION CONTROL The application code includes exception
 * controls to prevent each of the given points and uses at least 2 different
 * mechanisms. The code is complete and functions properly.
 *
 * RUBRIC POINT: G. POP-UPS The application code uses lambda expressions to
 * create standard pop-up and alert messages. The code is complete and functions
 * properly.
 *
 * RUBRIC POINT: H. The application has functionality for reminders and alerts
 * based on the user’s log-in. The code is complete and functions properly.
 *
 * The application has functionality to track user activity by recording
 * timestamps for user log-ins in a .txt file, and each new record is appended
 * to the log file if the file already exists. The code is complete and
 * functions properly.
 *
 * @author Therese Parks
 */
public class NewSweet_mainController implements Initializable {

    //  private static Connection conn;
    @FXML
    private AnchorPane anchor;

    @FXML
    private Text LoginScreenLabel;

    @FXML
    private Label nameTextLabel;

    @FXML
    private Label passwordTextLabel;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField userNameField;

    static String loggedInUser;
    static String userPassword;

    @FXML
    private Text locationText1;// US

    @FXML
    private Text locationText2;// France

    @FXML
    private Button loginButton;

    @FXML
    private Button resetButton;

    @FXML
    private Button exitButton;

    private static Connection conn = null;

    public static Connection getConn() {

        return conn;
    }

    @FXML
    void handleLoginButtonAction(ActionEvent event) throws SQLException, IOException, ClassNotFoundException {
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("UserTimeStamps_NewSweet2018.txt", true)));
        Stage stage = (Stage) anchor.getScene().getWindow();
        Parent root = null;

        Locale currentLocale = Locale.getDefault();

        PreparedStatement ps = null;

        // JDBC driver name and database URL
        final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
        final String DB_URL = "jdbc:mysql://52.206.157.109/U040ob";
        //  Database credentials
        final String DBUSER = "U040ob";
        final String DBPASS = "53688142285";

        ResultSet rs;

        //STEP 2: Register JDBC driver
        Class.forName(JDBC_DRIVER);
        //STEP 3: Open a connection
        System.out.println("Connecting to database...");
        conn = DriverManager.getConnection(DB_URL, DBUSER, DBPASS);// location of db file
        ps = conn.prepareStatement("SELECT * FROM user WHERE userName = ? and password = ?");
        ps.setString(1, userNameField.getText());
        loggedInUser = userNameField.getText();
        ps.setString(2, passwordField.getText());
        userPassword = passwordField.getText();
        rs = ps.executeQuery();

        //Pop Up Reminder for Appointments in 15 minutes or less from login for that user
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
        LocalDateTime now = LocalDateTime.now();
        //System.out.println(dtf.format(now));
        try {

            PreparedStatement statement2 = NewSweet_mainController.getConn().prepareStatement(
                    "SELECT appointment.start, appointment.contact FROM appointment;");
            ResultSet rs2 = statement2.executeQuery();

            while (rs2.next()) {
                System.out.print(rs2.getString("appointment.start"));
                LocalDateTime startTime = LocalDateTime.parse(rs2.getString("appointment.start"), dtf);
                String contact = rs2.getString("appointment.contact");
                if (startTime.isBefore(now.plusMinutes(15)) && contact.equalsIgnoreCase(loggedInUser)) {
                    System.out.println("appointment reminder!!!");
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Schedule Appointment!"); // login was successful
                    alert.setHeaderText("You have an Appointment in 15 minutes."); // Welcome to NetSweet!
                    alert.setContentText("Please check your schedule.");
                    // alert.showAndWait(); //TODO: keep this commented or it makes the alert pop up twice
                    //Lambda Alert
                    alert.showAndWait()
                            .filter(response -> response == ButtonType.OK)
                            .ifPresent(response -> alert.close());
                    // return;
                    break;
                }

            }
        } catch (SQLException sqe) {
            System.out.println("Check your SQL in parseAppointmentList");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Something besides the SQL went wrong in parseAppointmentList.");
        }
        if (rs.next()) {

            // records timestamps: for user login.
            List list = new ArrayList();
            String userNAME;
            String passWORD;
            //Requirement F: Try with Resources for Entering incorrect Username and password
            try {
                Statement s2 = NewSweet_mainController.getConn().createStatement();
                ResultSet rs2 = s2.executeQuery("SELECT user.userName, user.password FROM user WHERE user.userName = '" + loggedInUser + "' "
                        + "AND user.password = '" + userPassword + "' ");

                StringBuffer output = new StringBuffer();
                while (rs2.next()) {

                    userNAME = rs2.getString("user.userName");
                    passWORD = rs2.getString("user.password");
                    output.append(userNAME + " " + passWORD + " \n");
                    if (loggedInUser.equals(userNAME) && userPassword.equals(passWORD)) {
                        System.out.println(loggedInUser + "  Successfully Logged in at: " + LocalDateTime.now());
                        out.println(loggedInUser + "  Successfully Logged in at: " + LocalDateTime.now());
                        out.close();
                    }
                }
            } catch (SQLException sqe) {
                sqe.printStackTrace();
            }
            //end sucessful

            //Condition 1: User is in France
            //if (currentLocale.getCountry() == "FR") {
            if (currentLocale.getCountry().equals("FR")) {

                String message = userNameField.getText();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("la connexion a été réussie"); // login was successful
                alert.setHeaderText("Bienvenue sur NetSweet!"); // Welcome to NetSweet!
                alert.setContentText(message);

                //Lambda Alert
                alert.showAndWait()
                        .filter(response -> response == ButtonType.OK)
                        .ifPresent(response -> alert.close());

                Stage stage2;
                // Parent root;
                //get reference to the button's stage         
                stage2 = (Stage) anchor.getScene().getWindow();
                //load up OTHER FXML document
                root = FXMLLoader.load(getClass().getResource("NewSweet_home.fxml"));
                //create a new scene with root and set the stage
                Scene scene = new Scene(root);
                stage2.setScene(scene);
                stage2.show();
            } else {// end if local == "FR"

                //Condition 2: User is in the United States
                String message = userNameField.getText();

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("You have successfully logged in."); //login was successful
                alert.setHeaderText("Welcome to NetSweet!"); // Welcome to NetSweet!
                alert.setContentText(message);
                //Lambda Alert
                alert.showAndWait()
                        .filter(response -> response == ButtonType.OK)
                        .ifPresent(response -> alert.close());

                Stage stage2;
                // Parent root;
                //get reference to the button's stage         
                stage2 = (Stage) anchor.getScene().getWindow();
                //load up OTHER FXML document
                root = FXMLLoader.load(getClass().getResource("NewSweet_home.fxml"));
                //create a new scene with root and set the stage
                Scene scene = new Scene(root);
                stage2.setScene(scene);
                stage2.show();

            }
        } else {

            //if (currentLocale.getCountry() == "FR") {
            if (currentLocale.getCountry().equals("FR")) {
                out.println(loggedInUser + "  Failed Log in at: " + LocalDateTime.now());
                out.close();
                locationText1.setText(" Échec de la connexion");
                String message = userNameField.getText();
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Échec de la connexion!"); // login was successful
                alert.setHeaderText("Le nom d'utilisateur et le mot de passe ne correspondent pas."); // Welcome to NetSweet!
                // alert.showAndWait(); //TODO: keep this commented or it makes the alert pop up twice
                //lambda
                alert.showAndWait()
                        .filter(response -> response == ButtonType.OK)
                        .ifPresent(response -> alert.close());
                return;
            } else {

                // File file = new File("UserTimeStamps_NewSweet2018.txt");
                // PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter("UserTimeStamps_NewSweet2018.txt")));
                out.println(loggedInUser + "  Failed Log in at: " + LocalDateTime.now());
                out.close();
                locationText1.setText("Login Failed!");
                String message = userNameField.getText();
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Login Failed!"); // login was successful
                alert.setHeaderText("The username and password did not match."); // Welcome to NetSweet!
                alert.setContentText("Please try again.");
                //Lambda Expression
                alert.showAndWait()
                        .filter(response -> response == ButtonType.OK)
                        .ifPresent(response -> alert.close());
                return;
            }

        }
        /*Write code to provide reminders and alerts 15 minutes in advance of an appointment based on the user's login*/
        // customerid, customerName, addressId, active, createDate, createBy, lastUpdate, lastUpdateBy

    }

    @FXML // controls to close IMSystem
    void exitButtonAction(ActionEvent event
    ) {
        // get a handle to the stage
        Stage stage = (Stage) exitButton.getScene().getWindow();
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

    @FXML
    void resetButtonAction(ActionEvent event
    ) {
        Stage stage = (Stage) resetButton.getScene().getWindow();
        userNameField.setText(null);
        passwordField.setText(null);

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        System.out.println(Locale.getDefault());
        Locale locale = new Locale("fr", "FR");
        // set Default Locale to France
        Locale.setDefault(locale);
        System.out.println(Locale.getDefault());

        if (locale.getCountry().equals("FR")) {

            locationText1.setText(locale.getCountry());
            nameTextLabel.setText("Nom d'utilisateur ou adresse e-mail");
            passwordTextLabel.setText("Mot de Passe");

        } else if (locale.getCountry().equals("US")) {

            locationText1.setText(locale.getCountry());
            nameTextLabel.setText("Name or email address");
            passwordTextLabel.setText("Password");

        }

    }
}
