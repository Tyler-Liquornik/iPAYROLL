package se2203b.ipayroll;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class DeleteAccountController extends Subcontroller{

    @FXML
    private ComboBox<String> usernameComboBox;
    @FXML
    private TextField employeeIDField, nameField;
    @FXML
    private Label messageLabel;
    @FXML
    private Button cancelButton, deleteButton;

    public void delete() throws SQLException {
        // Ensure an option has been chosen in the Combo Box
        if (Objects.equals(usernameComboBox.getValue(), null)) {
            messageLabel.setText("Choose a username from the combo box");
        }
        else{
            // Deleting the account means deleting the username, password, and email. The employee profile still exists
            EmployeeProfile selected = EmployeeTableAdapter.getProfile("Username", usernameComboBox.getValue());
            selected.setUsername(null);
            selected.setPassword(null);
            selected.setEmail(null);
            EmployeeTableAdapter.updateProfile(selected);
            cancelButton.fire();
        }
    }

    public void populateFields(){
        EmployeeProfile selected = EmployeeTableAdapter.getProfile("Username", usernameComboBox.getValue());
        employeeIDField.setText(selected.getEmployeeID());

        if (selected.getName() == null){
            nameField.setText("No name on file");
            nameField.setStyle("-fx-font-style: italic;");
        }
        else
            nameField.setText(String.format("%s %s", selected.getName()[0], selected.getName()[1]));
    }


    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Populate the Combo Box of usernames
        try {
            List<EmployeeProfile> employeeProfileList = EmployeeTableAdapter.getAllEmployeeProfiles();
            for (EmployeeProfile employeeProfile : employeeProfileList){
                if (employeeProfile.getUsername() != null){
                    usernameComboBox.getItems().add(employeeProfile.getUsername());
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Prevent manually editing the id and name fields
        nameField.setDisable(true);
        employeeIDField.setDisable(true);
    }
}
