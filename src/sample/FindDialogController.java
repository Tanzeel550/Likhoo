package sample;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

public class FindDialogController {

    public CheckBox matchCase;
    @FXML
    private TextField findTextField;

    public String processUserInput() {
        return findTextField.getText();
    }

    public boolean matchCase() {
        return matchCase.isSelected();
    }

}
