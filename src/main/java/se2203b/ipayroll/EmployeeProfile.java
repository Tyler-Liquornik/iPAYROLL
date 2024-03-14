package se2203b.ipayroll;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import java.time.LocalDate;
import java.util.HashMap;

public class EmployeeProfile extends Profile
{
    // Fields
    private StringProperty employeeID;
    private StringProperty username;
    private StringProperty password;
    private StringProperty jobName;
    private StringProperty skillCode;
    private MapProperty<LocalDate, String> keyDates;

    // Constructor
    public EmployeeProfile(String[] name, String[] address, String email, String phoneNumber, String maritalStatus,
                           String sin, String employeeID, String username, String password, String jobName,
                           String skillCode, HashMap<LocalDate, String> keyDates){
        super(name, address, email, phoneNumber, maritalStatus, sin);
        this.employeeID = new SimpleStringProperty(employeeID);
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
        this.jobName = new SimpleStringProperty(jobName);
        this.skillCode = new SimpleStringProperty(skillCode);

        // FXCollections.observableMap() can't take a null value
        this.keyDates = keyDates != null ? new SimpleMapProperty<>(FXCollections.observableMap(keyDates)) : null;
    }

    // Accessors
    public String getEmployeeID(){
        return employeeID.get();
    }
    public String getUsername(){
        return username.get();
    }
    public String getPassword(){
        return password.get();
    }
    public String getJobName(){
        return jobName.get();
    }
    public String getSkillCode(){
        return skillCode.get();
    }
    public HashMap<LocalDate, String> getKeyDates(){

        // Cannot get from null hashmap
        return keyDates != null ? new HashMap<>(keyDates.get()) : null;
    }

    // Mutators
    public void setEmployeeID(String employeeID){
        this.employeeID = new SimpleStringProperty(employeeID);
    }
    public void setUserName(String username){
        this.username = new SimpleStringProperty(username);
    }
    public void setPassword(String password){
        this.password = new SimpleStringProperty(password);
    }
    public void setJobName(String jobName) {
        this.jobName = new SimpleStringProperty(jobName);
    }
    public void setSkillCode(String skillCode){
        this.skillCode = new SimpleStringProperty(skillCode);
    }
    public void setKeyDates(HashMap<LocalDate, String> keyDates){
        this.keyDates = new SimpleMapProperty<>(FXCollections.observableMap(keyDates));
    }
}
