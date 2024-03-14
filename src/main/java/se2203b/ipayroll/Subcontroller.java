package se2203b.ipayroll;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

// Subcontrollers are controllers other than the main controller (iPAYROLLController)
public abstract class Subcontroller implements Initializable {
    private IPayrollController mainController;

    // Store a reference to main window
    public void setMainController(IPayrollController mainController) {
        this.mainController = mainController;
    }

    public IPayrollController getMainController() {
        return mainController;
    }

    // Cancel closes the window
    public void cancel(ActionEvent e){
        getMainController().closeWindow((Node) e.getSource());
    };

    // Save deals with database queries
    public abstract void save() throws SQLException;

    @Override
    public abstract void initialize(URL url, ResourceBundle resourceBundle);
}
