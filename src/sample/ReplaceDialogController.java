package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ReplaceDialogController {

    @FXML
    private TextField findText, replaceText;


    public ReplaceUserChoice processUserInput() {
        return (new ReplaceUserChoice(findText.getText(), replaceText.getText()));
    }

    static class ReplaceUserChoice {
        private final String find;
        private final String replace;

        public ReplaceUserChoice(String find, String replace) {
            this.find = find;
            this.replace = replace;
        }

        public String getFind() {
            return find;
        }

        public String getReplace() {
            return replace;
        }

    }

}
