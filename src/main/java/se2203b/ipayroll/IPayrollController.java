package se2203b.ipayroll;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class IPayrollController implements Initializable {
    @FXML
    private Menu manageUserAccountsMenu;
    @FXML
    private MenuBar mainMenu;
    private EmployeeProfile loggedIn; // Reference to employee logged outside database for convenience
    public void setLoggedIn(EmployeeProfile profile){
        this.loggedIn = profile;
    }

    public EmployeeProfile getLoggedIn(){
        return loggedIn;
    }

    public void setPrivileges(boolean isAdmin){
        if (isAdmin){
            mainMenu.getMenus().get(1).setDisable(false);
            mainMenu.getMenus().get(2).setDisable(false);
            mainMenu.getMenus().get(3).setDisable(false);
            mainMenu.getMenus().get(6).setDisable(false);
        }
        else{
            mainMenu.getMenus().get(4).setDisable(false);
            mainMenu.getMenus().get(5).setDisable(false);
        }
    }

    public Subcontroller showWindow(String fxml, String title) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxml));
        Parent parent = (Parent) fxmlLoader.load();

        Subcontroller s = (Subcontroller) fxmlLoader.getController();
        s.setMainController(this);

        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.getIcons().add(new Image("file:src/main/resources/se2203b/ipayroll/WesternLogo.png"));
        stage.setTitle(title);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
        return s;
    }

    public void showAbout() throws Exception{
        showWindow("about-view.fxml", "About Us");
    }

    public void showCreateAccount() throws Exception{
        showWindow("createAccount-view.fxml", "Create User Account");
    }

    public void showDeleteAccount() throws Exception{
        showWindow("deleteAccount-view.fxml", "Delete User Account");
    }

    public void showLogin() throws Exception{
        showWindow("login-view.fxml", "Login to iPAYROLL");
    }

    public void showAddEmployee() throws Exception{
        showWindow("addEmployee-view.fxml", "Add New Employee Profile");
    }

    public void showModifyEmployee() throws Exception{
        showWindow("modifyEmployee-view.fxml", "Modify User Profile");
    }

    public void showChangePassword() throws Exception{
        ChangePasswordController controller = (ChangePasswordController) showWindow("changePassword-view.fxml", "Change Password");

        // Initialize the label with the username
        // Cannot be in initialize() in ChangePasswordController as this requires access to loggedIn
        // Through the main controller (this) which is set after initialization
        controller.getTitleLabel().textProperty().bind(new SimpleStringProperty
                (String.format("Change password for %s", loggedIn.getUsername())));
    }

    // Add the menu for logged-in user
    public void addUserMenuItem(String username){
        // Creating the Menu with MenuItems, attaching methods to open respective windows
        Menu menu = new Menu(username);

        MenuItem changePassword = new MenuItem("Change Password");
        changePassword.setOnAction(e -> {
            try {
                showChangePassword();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        menu.getItems().add(changePassword);

        menu.getItems().add(1, new SeparatorMenuItem());

        MenuItem logout = new MenuItem("Logout");
        logout.setOnAction(e -> logout());
        menu.getItems().add(logout);

        // Adding the avatar icon
        Image avatar = new Image("file:src/main/resources/se2203b/ipayroll/Avatar.png");
        ImageView avatarView = new ImageView(avatar);
        avatarView.setFitHeight(15);
        avatarView.setFitWidth(15);
        menu.setGraphic(avatarView);

        // Adding the menu to the menu bar
        mainMenu.getMenus().add(menu);

        // Disable the login option as we have just logged in
        mainMenu.getMenus().get(0).getItems().get(0).setDisable(true);
    }

    public void logout(){
        // Reset the main menu
        for (int i = 1; i <= 6; i++) {
            mainMenu.getMenus().get(i).setDisable(true);
        }
        mainMenu.getMenus().remove(8);
        mainMenu.getMenus().get(0).getItems().get(0).setDisable(false);

        // Nobody is logged in anymore
        loggedIn = null;
    }

    // Pass in any node on the window to be closed
    public void closeWindow(Node n) {
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();
    }

    // Extra closeWindow method able to be called by setOnAction as this method has no parameters
    public void exit(){
        closeWindow(mainMenu);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Reset the main menu
        for (int i = 1; i <= 6; i++) {
            mainMenu.getMenus().get(i).setDisable(true);
        }
    }
}
