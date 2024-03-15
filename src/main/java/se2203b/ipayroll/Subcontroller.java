package se2203b.ipayroll;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import java.net.URL;
import java.util.ResourceBundle;

// Subcontrollers are controllers other than the main controller (iPAYROLLController)
public class Subcontroller implements Initializable {
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

    };
}
