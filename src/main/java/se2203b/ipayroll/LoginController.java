package se2203b.ipayroll;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

public class LoginController extends Subcontroller{

    @FXML
    private TextField usernameField, passwordField;
    @FXML
    private Button saveButton, cancelButton;
    @FXML
    private Label messageLabel;
    @FXML
    private ImageView logoImageView;

    public void displayMessage(String message){
        messageLabel.setText(message);
    }

    @Override
    public void save() throws SQLException {
        // Check that the profile exists by username
        if (EmployeeTableAdapter.getProfile("Username", usernameField.getText()) != null)
        {
            // Get the profile from the username
            EmployeeProfile profile = EmployeeTableAdapter.getProfile("Username", usernameField.getText());

            // Check that the password is correct
            if (Objects.equals(profile.getPassword(), passwordField.getText() == "" ? null : passwordField.getText()))
            {
                login(profile);
                cancelButton.fire();
            }
            else
                displayMessage("Incorrect password");
        }
        else
            displayMessage("User not found");
    }

    public void login(EmployeeProfile profile){
        getMainController().addUserMenuItem(profile.getUsername());
        getMainController().setLoggedIn(profile);
        getMainController().setPrivileges(profile.getEmployeeID().charAt(0) == 'A'); // first char A -> admin profile
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Load the western logo image
        // Loading Images
        Image westernLogo = new Image("file:src/main/resources/se2203b/ipayroll/WesternLogo.png");
        logoImageView.setImage(westernLogo);
    }
}
