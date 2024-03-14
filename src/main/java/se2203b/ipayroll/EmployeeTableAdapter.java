package se2203b.ipayroll;

import java.sql.*;
import java.time.LocalDate;
import java.util.*;

public class EmployeeTableAdapter {

    // Setup database connection
    private static final Connection conn;
    static {
        try {
            String db_url = "jdbc:derby:iPAYROLLDatabase";
            conn = DriverManager.getConnection(db_url);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Add a new employee profile
    public static void addNewProfile(EmployeeProfile employeeProfile) throws SQLException {
        Statement st = conn.createStatement();

        String employeeID = employeeProfile.getEmployeeID();
        String username = employeeProfile.getUsername();
        String password = employeeProfile.getPassword();
        String jobName = employeeProfile.getJobName();

        // Name stored as fName,lName
        String fName = employeeProfile.getName()[0];
        String lName = employeeProfile.getName()[1];
        String name = fName + "," + lName;

        // Address stored as province,city,postalCode
        String province = employeeProfile.getAddress()[0];
        String city = employeeProfile.getAddress()[1];
        String postalCode = employeeProfile.getAddress()[2];
        String address = province + "," + city + "," + postalCode;

        String email = employeeProfile.getEmail();
        String phoneNumber = employeeProfile.getPhoneNumber();
        String maritalStatus = employeeProfile.getMaritalStatus();
        String skillCode = employeeProfile.getSkillCode();
        String sin = employeeProfile.getSin();


        // Key Dates stored as yyyy-mm-dd_description1,yyyy-mm-dd_description2,yyyy-mm-dd_description3, ...
        String keyDates = "";

        // Ensuring checks for null values
        if (employeeProfile.getKeyDates() != null){
            Set<Map.Entry<LocalDate, String>> entrySet = employeeProfile.getKeyDates().entrySet();
            for (Map.Entry<LocalDate, String> entry : entrySet){
                keyDates += entry == null ? "" : entry.getKey().toString() + "_" + entry.getValue() + ",";
            }
        }

        // Must format all VARCHAR types in DB to properly deal with null values
        String query = String.format("INSERT INTO EmployeeProfiles VALUES(%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s)",
                formatSQL(employeeID), formatSQL(username), formatSQL(password), formatSQL(jobName),
                formatSQL(name), formatSQL(address), formatSQL(email), formatSQL(phoneNumber),
                formatSQL(maritalStatus), formatSQL(skillCode), formatSQL(keyDates), formatSQL(sin));

        st.execute(query);
    }

    // Get a specific employee profile by searching through a specific parameter (column name)
    public static EmployeeProfile getProfile(String searchParameter, String searchValue){
        // Return null if invalid searchParameter is used
        try{
            Statement st = conn.createStatement();
            String query = String.format("SELECT * FROM EmployeeProfiles WHERE %s = '%s'", searchParameter, searchValue);
            ResultSet rs = st.executeQuery(query);

            // Return null if a profile is not found matching the search request
            if (!rs.next())
                return null;

            // Must check for null values before called .split()
            String employeeID = rs.getString("EmployeeID");
            String username = rs.getString("Username");
            String password = rs.getString("Password");
            String jobName = rs.getString("JobName");
            String[] name = rs.getString("Name") != null ? rs.getString("Name").split(",") : null;
            String[] address = rs.getString("Address") != null ? rs.getString("Address").split(",") : null;
            String email = rs.getString("Email");
            String phoneNumber = rs.getString("PhoneNumber");
            String maritalStatus = rs.getString("MaritalStatus");
            String skillCode = rs.getString("SkillCode");
            String sin = rs.getString("Sin");

            // Parsing from string of form yyyy-mm-dd_description
            HashMap<LocalDate, String> keyDates = new HashMap<>();
            if (rs.getString("KeyDates") != null)
            {
                String[] stringEntries = rs.getString("KeyDates").split(",");
                for (String stringEntry : stringEntries)
                {
                    LocalDate date = LocalDate.of(Integer.parseInt(stringEntry.substring(0, 4)),
                            Integer.parseInt(stringEntry.substring(5, 7)),
                            Integer.parseInt(stringEntry.substring(8, 10)));
                    String description = stringEntry.substring(12);
                    keyDates.put(date, description);
                }
            }
            else keyDates = null;

            return new EmployeeProfile(name, address, phoneNumber, email, maritalStatus, sin, employeeID, username, password, jobName, skillCode, keyDates);
        }
        catch (SQLException ex) {
            return null;
        }
    }

    // Update an existing employee profile. Selection based on employeeID in profile passed in.
    public static void updateProfile(EmployeeProfile updatedProfile) throws SQLException {
        Statement st = conn.createStatement();

        String employeeID = updatedProfile.getEmployeeID();
        String username = updatedProfile.getUsername();
        String password = updatedProfile.getPassword();
        String jobName = updatedProfile.getJobName();

        // Name stored as fName,lName
        String name;
        if (updatedProfile.getName() != null) {
            String fName = updatedProfile.getName()[0];
            String lName = updatedProfile.getName()[1];
            name = fName + "," + lName;
        } else name = null;


        // Address stored as province,city,postalCode
        String address;
        if (updatedProfile.getAddress() != null) {
            String province = updatedProfile.getAddress()[0];
            String city = updatedProfile.getAddress()[1];
            String postalCode = updatedProfile.getAddress()[2];
            address = province + "," + city + "," + postalCode;
        } else address = null;

        String email = updatedProfile.getEmail();
        String phoneNumber = updatedProfile.getPhoneNumber();
        String maritalStatus = updatedProfile.getMaritalStatus();
        String skillCode = updatedProfile.getSkillCode();
        String sin = updatedProfile.getSin();


        // Key Dates stored as yyyy-mm-dd_description1,yyyy-mm-dd_description2,yyyy-mm-dd_description3, ...
        StringBuilder keyDates = new StringBuilder();
        if (updatedProfile.getKeyDates() != null) {
            Set<Map.Entry<LocalDate, String>> entrySet = updatedProfile.getKeyDates().entrySet();
            for (Map.Entry<LocalDate, String> entry : entrySet) {
                keyDates.append(entry.getKey().toString()).append("_").append(entry.getValue()).append(",");
            }
        }
        else
            keyDates = null;

        // Must format all VARCHAR types in DB to properly deal with null values
        String query = String.format("UPDATE EmployeeProfiles SET" +
                        " Username = %s, Password = %s, JobName = %s, Name = %s, Address = %s," +
                        " Email = %s, PhoneNumber = %s, MaritalStatus = %s, SkillCode = %s, KeyDates = %s, " +
                        " Sin = %s WHERE EmployeeID = %s",
                formatSQL(username), formatSQL(password), formatSQL(jobName), formatSQL(name),
                formatSQL(address), formatSQL(email), formatSQL(phoneNumber), formatSQL(maritalStatus),
                formatSQL(skillCode), formatSQL(keyDates.toString()), formatSQL(sin), formatSQL(employeeID));

        st.execute(query);
    }

    public static List<EmployeeProfile> getAllEmployeeProfiles() throws SQLException {

        // Get all profiles for employees, IDs start with 'E' (not admin that start with 'A')
        Statement st = conn.createStatement();
        String query = "Select EmployeeID FROM EmployeeProfiles WHERE EmployeeID LIKE 'E%'";
        ResultSet rs = st.executeQuery(query);

        // Build the List
        List<EmployeeProfile> employeeProfiles = new ArrayList<>();
        while (rs.next()){
            employeeProfiles.add(getProfile("EmployeeID", rs.getString("EmployeeID")));
        }

        return employeeProfiles;
    }

    // Delete one specific employee profile by searching through a specific parameter (column name)
    // Need to be careful to pass in a column name that exists
    public static void deleteProfile (String searchParameter, String searchValue) throws SQLException {
        Statement st = conn.createStatement();
        String query = String.format("DELETE FROM EmployeeProfiles WHERE %s = '%s'", searchParameter, searchValue);
        st.execute(query);
    }

    // Prevents null values from being entered as string literals, which can cause issues
    public static String formatSQL(String value){
        return value == null ? "NULL" : "'" + value + "'";
    }
}
