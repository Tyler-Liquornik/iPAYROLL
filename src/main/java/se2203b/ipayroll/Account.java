package se2203b.ipayroll;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Account {
    // Fields
    private StringProperty username;
    private StringProperty password;
    private StringProperty email;
    private EmployeeProfile employeeProfile;

    // Constructor
    public Account(String username, String password, String email, EmployeeProfile employeeProfile) {
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
        this.email = new SimpleStringProperty(email);
        this.employeeProfile = employeeProfile;
    }

    // Accessors
    public String getUsername(){
        return username.get();
    }
    public String getPassword(){
        return password.get();
    }
    public String getEmail(){
        return email.get();
    }
    public EmployeeProfile getEmployeeProfile() {
        return employeeProfile;
    }

    // Mutators
    public void setUsername(String username){
        this.username = new SimpleStringProperty(username);
    }
    public void setPassword(String password){
        this.password = new SimpleStringProperty(password);
    }
    public void setEmail(String email){
        this.email = new SimpleStringProperty(email);
    }
    public void setEmployeeProfile(EmployeeProfile employeeProfile) {
        this.employeeProfile = employeeProfile;
    }
}
