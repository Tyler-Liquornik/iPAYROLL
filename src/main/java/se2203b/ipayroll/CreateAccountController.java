package se2203b.ipayroll;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class CreateAccountController extends Subcontroller{
    @FXML
    private Label messageLabel;
    @FXML
    private Button saveButton, cancelButton;
    @FXML
    private ComboBox<String> employeeIDComboBox;
    @FXML
    private TextField nameField, emailField, usernameField, passwordField, confirmPasswordField;

    public void save() throws SQLException {

        // Ensure an option has been chosen in the Combo Box
        if (Objects.equals(employeeIDComboBox.getValue(), null)) {
            messageLabel.setText("Choose an Employee ID");
        }
        // Ensure the passwords are the same
        else if (!Objects.equals(passwordField.getText(), confirmPasswordField.getText())) {
            messageLabel.setText("Passwords do not match");
        }
        // Ensure fields are filled (cannot have an account with no username / password / email)
        else if (usernameField.getText().isEmpty() || passwordField.getText().isEmpty()|| confirmPasswordField.getText().isEmpty()){
            messageLabel.setText("Fill out all the fields");
        }
        else{
            // Update the necessary fields of the
            EmployeeProfile selected = EmployeeTableAdapter.getProfile("EmployeeID", employeeIDComboBox.getValue());
            selected.setUsername(usernameField.getText());
            selected.setPassword(passwordField.getText());
            selected.setEmail(emailField.getText());
            EmployeeTableAdapter.updateProfile(selected);
            // Close the tab
            cancelButton.fire();
        }
    }

    public void displayName(){
        EmployeeProfile selected = EmployeeTableAdapter.getProfile("EmployeeID", employeeIDComboBox.getValue());
        if (selected.getName() == null){
            nameField.setText("No name on file");
            nameField.setStyle("-fx-font-style: italic;");
        }
        else
            nameField.setText(String.format("%s %s", selected.getName()[0], selected.getName()[1]));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Populate the Combo Box of Employee IDs for Employees who don't have accounts
        try {
            List<EmployeeProfile> employeeProfileList = EmployeeTableAdapter.getAllEmployeeProfiles();
            for (EmployeeProfile employeeProfile : employeeProfileList){
                // Check if the employee already has an account (only need to check username)
                if (employeeProfile.getUsername() == null){
                    employeeIDComboBox.getItems().add(employeeProfile.getEmployeeID());
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
