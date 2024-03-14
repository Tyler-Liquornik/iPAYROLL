package se2203b.ipayroll;

import javafx.beans.property.*;
import javafx.beans.value.ObservableObjectValue;
import java.util.Arrays;

public class Profile {
    // Fields
    private StringProperty[] name; // [firstName, lastName]
    private StringProperty[] address; // [provinceCode, city, postalCode]
    private StringProperty email;
    private StringProperty phoneNumber;
    private StringProperty maritalStatus;
    private StringProperty sin;

    // Constructor
    public Profile(String[] name, String[] address, String email, String phoneNumber, String maritalStatus, String sin) {
        setName(name);
        setAddress(address);
        setEmail(email);
        setPhoneNumber(phoneNumber);
        setMaritalStatus(maritalStatus);
        setSin(sin);
    }

    // Accessors
    public String[] getName() {
        return name != null ? Arrays.stream(name).map(ObservableObjectValue::get).toArray(String[]::new) : null;
    }
    public String[] getAddress() {
        return address != null ? Arrays.stream(address).map(ObservableObjectValue::get).toArray(String[]::new) : null;
    }
    public String getEmail(){
        return email.get();}
    public String getPhoneNumber(){
        return phoneNumber.get();
    }
    public String getMaritalStatus(){
        return maritalStatus.get();
    }
    public String getSin(){
        return sin.get();
    }

    // Mutators
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
        this.email = new SimpleStringProperty(email);}
    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
    }
    public void setMaritalStatus(String isMarried){
        this.maritalStatus = new SimpleStringProperty(isMarried);
    }
    public void setSin(String sin) {
        this.sin = new SimpleStringProperty(sin);
    }
}
