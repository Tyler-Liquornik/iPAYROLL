package se2203b.ipayroll;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class ChangePasswordController extends Subcontroller{
    @FXML
    private Label titleLabel, messageLabel;
    @FXML
    private PasswordField oldPasswordField, newPasswordField, confirmNewPasswordField;
    @FXML
    private Button saveButton, cancelButton;

    public Label getTitleLabel() {
        return titleLabel;
    }

    @Override
    public void save() throws SQLException {
        // Ensure the form is filled out properly
        if (!Objects.equals(getMainController().getLoggedIn().getPassword(), oldPasswordField.getText()))
            messageLabel.setText("Incorrect Password");
        else if (!Objects.equals(newPasswordField.getText(), confirmNewPasswordField.getText())){
            messageLabel.setText("Passwords do not match");
        }
        else{
            // Change the password
            EmployeeProfile loggedIn = getMainController().getLoggedIn();
            loggedIn.setPassword(newPasswordField.getText());
            EmployeeTableAdapter.updateProfile(loggedIn);
            cancelButton.fire();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}
