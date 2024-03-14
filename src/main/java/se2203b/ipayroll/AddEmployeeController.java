package se2203b.ipayroll;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

public class AddEmployeeController extends Subcontroller {
    @FXML
    private Label messageLabel;
    @FXML
    private Button saveButton, cancelButton;
    @FXML
    private TextField employeeIDField, employeeNameField, provinceField, cityField, phoneField,
            postalCodeField, marriedField, skillCodeField, jobNameField, sinField;

    @FXML
    private DatePicker lastPromotionDatePicker, hireDatePicker, birthDatePicker;

    public void clearMessageLabel(){
        messageLabel.setText("");
    }

    @Override
    public void save() throws SQLException {
        // Validate the employee id is in the correct form: E### for a regular employee or A### for an admin
        if (!employeeIDField.getText().matches("^[EA]\\d{3}$")){
            messageLabel.setText("Employee ID must match format E### or A###");
        }
        // Validate the employee's name has only a first name and last name
        else if (employeeNameField.getText().split(" ").length != 2){
            messageLabel.setText("Employee must have a first and last name, separated by a space");
        }
        else{
            // Get the information from the text fields
            String[] name = employeeNameField.getText().split(" ");
            String[] address = new String[]{provinceField.getText(), cityField.getText(), postalCodeField.getText()};
            String phoneNumber = phoneField.getText();
            String maritalStatus = marriedField.getText();
            String skillCode = skillCodeField.getText();
            String sin = sinField.getText();
            String jobName = jobNameField.getText();
            String employeeID = employeeIDField.getText();

            HashMap<LocalDate, String> keyDates = new HashMap<>();
            keyDates.put(birthDatePicker.getValue(), "Date of Birth");
            keyDates.put(hireDatePicker.getValue(), "Date of Hire");
            keyDates.put(lastPromotionDatePicker.getValue(), "Date of Last Promotion");

            // Create the employee and add the profile to the DB
            EmployeeProfile newEmployee = new EmployeeProfile(name, address, null, phoneNumber, maritalStatus,
                    sin, employeeID, null, null, jobName, skillCode, keyDates);

            EmployeeTableAdapter.addNewProfile(newEmployee);

            // Close the tab
            cancelButton.fire();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Prevent manually editing the date fields so non-dates cannot be typed
        lastPromotionDatePicker.setEditable(false);
        birthDatePicker.setEditable(false);
        hireDatePicker.setEditable(false);
    }
}
