package se2203b.ipayroll;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
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

    public void clearMessageLabel(){
        messageLabel.setText("");
    }

    public void populateFields(){
        EmployeeProfile selected = EmployeeTableAdapter.getProfile("EmployeeID", employeeIDComboBox.getValue());
        employeeNameField.setText(String.format("%s %s", selected.getName()[0], selected.getName()[1]));
        provinceField.setText(selected.getAddress()[0]);
        cityField.setText(selected.getAddress()[1]);
        phoneField.setText(selected.getPhoneNumber());
        postalCodeField.setText(selected.getAddress()[2]);
        marriedField.setText(selected.getMaritalStatus());
        skillCodeField.setText(selected.getSkillCode());
        sinField.setText(selected.getSin());
        jobNameField.setText(selected.getJobName());
        birthDatePicker.setValue(selected.getKeyDates().get("Date of Birth"));
        hireDatePicker.setValue(selected.getKeyDates().get("Date of Hire"));
        lastPromotionDatePicker.setValue(selected.getKeyDates().get("Date of Last Promotion"));
    }

    public void save() throws SQLException {
        // Ensure an option has been chosen in the Combo Box
        if (Objects.equals(employeeIDComboBox.getValue(), null)) {
            messageLabel.setText("Choose an Employee ID from the combo box");
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

            HashMap<String, LocalDate> keyDates = new HashMap<>();
            keyDates.put("Date of Birth", birthDatePicker.getValue());
            keyDates.put("Date of Hire", hireDatePicker.getValue());
            keyDates.put("Date of Last Promotion", lastPromotionDatePicker.getValue());

            // Create the employee and add the profile to the DB
            EmployeeProfile newEmployee = new EmployeeProfile(name, address, null, phoneNumber, maritalStatus,
                    sin, employeeID, null, null, jobName, skillCode, keyDates);

            EmployeeTableAdapter.updateProfile(newEmployee);

            // Close the tab
            cancelButton.fire();
        }
    }

    public void delete() throws Exception {
        EmployeeProfile selected = EmployeeTableAdapter.getProfile("EmployeeID", employeeIDComboBox.getValue());

        // Ensure an option has been chosen in the Combo Box
        if (Objects.equals(employeeIDComboBox.getValue(), null)) {
            messageLabel.setText("Choose an Employee ID from the combo box");
        }
        else {
            // Deleted account is either a null account, or null account fields
            if (selected.getAccount() == null ||
                    (selected.getUsername() == null && selected.getPassword() == null && selected.getEmail() == null)){
                EmployeeTableAdapter.deleteProfile("EmployeeID", employeeIDComboBox.getValue());
                cancelButton.fire();
            }
            // Alert to tell the user they must delete the account first
            else{
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("deleteAlert-view.fxml"));
                Parent parent = (Parent) fxmlLoader.load();
                Scene scene = new Scene(parent);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.getIcons().add(new Image("file:src/main/resources/se2203b/ipayroll/WesternLogo.png"));
                stage.setTitle("");
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.show();
            }
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
