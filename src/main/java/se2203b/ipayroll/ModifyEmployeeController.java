package se2203b.ipayroll;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class ModifyEmployeeController extends Subcontroller{

    @FXML
    private ComboBox<String> employeeIDComboBox;
    @FXML
    private Label messageLabel;
    @FXML
    private Button saveButton, cancelButton, deleteButton;
    @FXML
    private TextField employeeNameField, provinceField, cityField, phoneField,
            postalCodeField, marriedField, skillCodeField, jobNameField, sinField;

    @FXML
    private DatePicker lastPromotionDatePicker, hireDatePicker, birthDatePicker;

    public void delete() throws SQLException {
        EmployeeTableAdapter.deleteProfile("EmployeeID", employeeIDComboBox.getValue());
        cancelButton.fire();
    }

    public void clearMessageLabel(){
        messageLabel.setText("");
    }

    @Override
    public void save() throws SQLException {
        // Ensure an option has been chosen in the Combo Box
        if (Objects.equals(employeeIDComboBox.getValue(), null)) {
            messageLabel.setText("Choose an employeeID from the combo box");
        }
        // Validate the employee's name has only a first name and last name
        else if (employeeNameField.getText().split(" ").length != 2){
            messageLabel.setText("Employee must have a first and last name, separated by a space");
        }
        else{
            // Get the information from the text fields
            // Blank values will ensure the database still has null values
            String[] name = Objects.equals(employeeNameField.getText(), "") ? null : employeeNameField.getText().split(" ");
            String[] address = new String[]{
                    Objects.equals(provinceField.getText(), "") ? null : provinceField.getText(),
                    Objects.equals(cityField.getText(), "") ? null : cityField.getText(),
                    Objects.equals(postalCodeField.getText(), "") ? null : postalCodeField.getText() };
            String phoneNumber = Objects.equals(phoneField.getText(), "") ? null : phoneField.getText();
            String maritalStatus = Objects.equals(marriedField.getText(), "") ? null : marriedField.getText();
            String skillCode = Objects.equals(skillCodeField.getText(), "") ? null : skillCodeField.getText();
            String sin = Objects.equals(sinField.getText(), "") ? null : sinField.getText();
            String jobName = Objects.equals(jobNameField.getText(), "") ? null : jobNameField.getText();
            String employeeID = employeeIDComboBox.getValue();

            HashMap<LocalDate, String> keyDates = new HashMap<>();
            keyDates.put(birthDatePicker.getValue(), "Date of Birth");
            keyDates.put(hireDatePicker.getValue(), "Date of Hire");
            keyDates.put(lastPromotionDatePicker.getValue(), "Date of Last Promotion");

            // Create the employee and add the profile to the DB
            EmployeeProfile newEmployee = new EmployeeProfile(name, address, null, phoneNumber, maritalStatus,
                    sin, employeeID, null, null, jobName, skillCode, keyDates);

            EmployeeTableAdapter.updateProfile(newEmployee);

            // Close the tab
            cancelButton.fire();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // Populate the Combo Box of Employee IDs
        try {
            List<EmployeeProfile> employeeProfileList = EmployeeTableAdapter.getAllEmployeeProfiles();
            for (EmployeeProfile employeeProfile : employeeProfileList){
                employeeIDComboBox.getItems().add(employeeProfile.getEmployeeID());
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Prevent manually editing the date fields so non-dates cannot be typed
        lastPromotionDatePicker.setEditable(false);
        birthDatePicker.setEditable(false);
        hireDatePicker.setEditable(false);

    }
}
