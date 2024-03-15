package se2203b.ipayroll;

import javafx.beans.property.*;
import javafx.beans.value.ObservableObjectValue;
import javafx.collections.FXCollections;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;

public class EmployeeProfile
{
    // Fields
    private StringProperty[] name; // [firstName, lastName]
    private StringProperty[] address; // [provinceCode, city, postalCode]

    private StringProperty phoneNumber;
    private StringProperty maritalStatus;
    private StringProperty sin;

    private StringProperty employeeID;
    private StringProperty jobName;
    private StringProperty skillCode;
    private MapProperty<String, LocalDate> keyDates;
    private Account account; // contents of account objects are properties available to get and set but not fields

    // Constructor
    public EmployeeProfile(String[] name, String[] address, String email, String phoneNumber, String maritalStatus,
                           String sin, String employeeID, String username, String password, String jobName,
                           String skillCode, HashMap<String, LocalDate> keyDates){
        this.account = new Account(username, password, email, this);
        this.employeeID = new SimpleStringProperty(employeeID);
        this.jobName = new SimpleStringProperty(jobName);
        this.skillCode = new SimpleStringProperty(skillCode);
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
        this.maritalStatus = new SimpleStringProperty(maritalStatus);
        this.sin = new SimpleStringProperty(sin);

        // FXCollections.observableMap() can't take a null value
        this.keyDates = keyDates != null ? new SimpleMapProperty<>(FXCollections.observableMap(keyDates)) : null;

        // More complicated setters outsourced for
        setName(name);
        setAddress(address);
    }

    // Accessors
    public String getEmployeeID(){
        return employeeID.get();
    }
    public String getUsername(){
        return account.getUsername();
    }
    public String getPassword(){
        return account.getPassword();
    }
    public String getJobName(){
        return jobName.get();
    }
    public String getSkillCode(){
        return skillCode.get();
    }
    public HashMap<String, LocalDate> getKeyDates(){

        // Cannot get from null hashmap
        return keyDates != null ? new HashMap<>(keyDates.get()) : null;
    }
    public String[] getName() {
        return name != null ? Arrays.stream(name).map(ObservableObjectValue::get).toArray(String[]::new) : null;
    }
    public String[] getAddress() {
        return address != null ? Arrays.stream(address).map(ObservableObjectValue::get).toArray(String[]::new) : null;
    }
    public String getEmail(){
        return account.getEmail();}
    public String getPhoneNumber(){
        return phoneNumber.get();
    }
    public String getMaritalStatus(){
        return maritalStatus.get();
    }
    public String getSin(){
        return sin.get();
    }

    // Get username, password, email packaged into an Account object
    public Account getAccount(){
        return this.account;
    }

    // Mutators
    public void setEmployeeID(String employeeID){
        this.employeeID = new SimpleStringProperty(employeeID);
    }
    public void setUsername(String username){
        account.setUsername(username);
    }
    public void setPassword(String password){
       account.setPassword(password);
    }
    public void setName(String[] name){
        if (name != null && name.length != 2) {
            throw new IllegalArgumentException("Name array must contain exactly two elements: first name and last name.");
        }
        else if (name != null)
            this.name = Arrays.stream(name).map(SimpleStringProperty::new).toArray(SimpleStringProperty[]::new);
        else
            this.name = null;
    }
    public void setAddress(String[] address){
        if (address != null && address.length != 3) {
            throw new IllegalArgumentException("Address array must contain exactly two elements: province code, city, and postal code.");
        }
        else if (address != null)
            this.address = Arrays.stream(address).map(SimpleStringProperty::new).toArray(SimpleStringProperty[]::new);
        else
            this.address = null;
    }
    public void setEmail(String email){
       account.setEmail(email);}
    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
    }
    public void setMaritalStatus(String isMarried){
        this.maritalStatus = new SimpleStringProperty(isMarried);
    }
    public void setSin(String sin) {
        this.sin = new SimpleStringProperty(sin);
    }
    public void setJobName(String jobName) {
        this.jobName = new SimpleStringProperty(jobName);
    }
    public void setSkillCode(String skillCode){
        this.skillCode = new SimpleStringProperty(skillCode);
    }
    public void setKeyDates(HashMap<String, LocalDate> keyDates){
        this.keyDates = new SimpleMapProperty<>(FXCollections.observableMap(keyDates));
    }
    public void setAccount(Account account){
        this.account = account;
    }
}
