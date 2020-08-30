package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import sample.ReplaceDialogController.ReplaceUserChoice;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class Controller {
    @FXML
    private MenuItem saveMenu, saveAsMenu, openMenu, newMenu, quitMenu, undoMenu, redoMenu, cutMenu, pasteMenu, copyMenu, deleteMenu, findMenu, replaceMenu,
            selectAllMenu, wordWrapMenu, fontMenu, statusBarMenu, aboutMenu, whatIsItMenu;

    @FXML
    private BorderPane borderPane;
    @FXML
    private TextArea textarea;
    @FXML
    private Label statusLabel;

    private File file = null;
    private String fileData = null;

    public static boolean isSaved = true;

    public void initialize() {
//        Adding the shortucts
        openMenu.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));
        saveMenu.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
        saveAsMenu.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN));
        quitMenu.setAccelerator(new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN));
        newMenu.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN));
        undoMenu.setAccelerator(new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN));
        redoMenu.setAccelerator(new KeyCodeCombination(KeyCode.Y, KeyCombination.CONTROL_DOWN));
        cutMenu.setAccelerator(new KeyCodeCombination(KeyCode.X, KeyCombination.CONTROL_DOWN));
        copyMenu.setAccelerator(new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN));
        selectAllMenu.setAccelerator(new KeyCodeCombination(KeyCode.A, KeyCombination.CONTROL_DOWN));
        findMenu.setAccelerator(new KeyCodeCombination(KeyCode.F, KeyCombination.CONTROL_DOWN));
        replaceMenu.setAccelerator(new KeyCodeCombination(KeyCode.R, KeyCombination.CONTROL_DOWN));
//        goToMenu.setAccelerator(new KeyCodeCombination(KeyCode.G, KeyCombination.CONTROL_DOWN));
        pasteMenu.setAccelerator(new KeyCodeCombination(KeyCode.P, KeyCombination.CONTROL_DOWN));
        deleteMenu.setAccelerator(new KeyCodeCombination(KeyCode.D, KeyCombination.CONTROL_DOWN));
        wordWrapMenu.setAccelerator(new KeyCodeCombination(KeyCode.W, KeyCombination.CONTROL_DOWN));




    }

    public void openMenuAction() {
        FileChooser fileChooser = new FileChooser();

        file = fileChooser.showOpenDialog(borderPane.getScene().getWindow());

        try {
            fileData = Model.getInstance().openData(file);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        textarea.setText(fileData);

        isSaved = true;

        statusLabel.setText("You are editing " + file.getName());
    }

    public void saveMenuAction() {

        if (file == null) {
            FileChooser fileChooser = new FileChooser();
            file = fileChooser.showOpenDialog(borderPane.getScene().getWindow());
        }

        try {
            Model.getInstance().saveData(file, textarea.getText());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        isSaved = true;
    }

    public void saveAsMenuAction() {
        FileChooser fileChooser = new FileChooser();
        File newfile = fileChooser.showSaveDialog(borderPane.getScene().getWindow());

        try {
            Model.getInstance().saveData(newfile, textarea.getText());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        isSaved = true;
    }

    public void exitMenuAction() {
        Platform.exit();
    }

    public void textAreaTextChanged() {
        isSaved = false;
        statusLabel.setText("Changes were not saved.");
    }

    public void undoAction() {
        textarea.undo();
        statusLabel.setText("Un-doed");
    }

    public void redoAction() {
        textarea.redo();
        statusLabel.setText("Re-doed");
    }

    public void cutAction() {
        textarea.cut();
    }

    public void pasteAction() {
        textarea.paste();
    }

    public void copyAction() {
        textarea.copy();
    }

    public void deleteAction() {
        textarea.deleteNextChar();
    }

    public void findAction() throws IOException {
        Dialog<ButtonType> findDialog = new Dialog<>();
        findDialog.setTitle("Find");
        findDialog.setHeaderText("Enter The Text You Want to Find");

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("findDialog.fxml"));

        findDialog.getDialogPane().setContent(fxmlLoader.load());

        findDialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        findDialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = findDialog.showAndWait();

        if (result.isPresent() && result.get() == (ButtonType.OK)){

            FindDialogController findDialogController = fxmlLoader.getController();
            String userFind = findDialogController.processUserInput();

            int userFindLength = userFind.length();
            int textAreaLength = textarea.getLength();

            String text = textarea.getText();

            boolean found = false;

            for (int i = 0; i < textAreaLength-userFindLength; i++) {

                final String substring = text.substring(i, i + userFindLength);

                if (findDialogController.matchCase()) {
                    if (substring.equals(userFind)) {
                        textarea.selectRange(i, i + userFindLength);
                        found = true;
                    }
                }
                else {
                    if (substring.equalsIgnoreCase(userFind)) {
                        textarea.selectRange(i, i + userFindLength);
                        found = true;
                    }
                }
            }

            if (!found) {
                Alert alert = new Alert(Alert.AlertType.NONE);
                alert.setTitle("Not found");
                alert.setHeaderText("The text you searched was not found");
                alert.getButtonTypes().add(ButtonType.OK);
                alert.showAndWait();
                statusLabel.setText(userFind + " not Found");
            }
            else
                statusLabel.setText(userFind + " Found");

        }

    }

    public void replaceAction() throws IOException {
        Dialog<ButtonType> replaceDialog = new Dialog<>();
        replaceDialog.setTitle("Replace");
        replaceDialog.setHeaderText("Enter The Text You Want to Replace");

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("replaceDialog.fxml"));

        replaceDialog.getDialogPane().setContent(fxmlLoader.load());

        replaceDialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        replaceDialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = replaceDialog.showAndWait();

        if (result.isPresent() && result.get() == (ButtonType.OK)){

            ReplaceDialogController replaceDialogController = fxmlLoader.getController();
            ReplaceUserChoice userChoice = replaceDialogController.processUserInput();

            if (!userChoice.getReplace().equals("")) {

                String text = textarea.getText();

                textarea.setText(text.replaceAll(userChoice.getFind(), userChoice.getReplace()));

                if (textarea.toString().equals(text))
                    statusLabel.setText(userChoice.getFind() + " not Found!");
                else
                    statusLabel.setText(userChoice.getFind() + " was replaced with " + userChoice.getReplace());
            }
            else
                statusLabel.setText("Nothing to replace");
        }

    }

    public void selectAllAction() {
        textarea.selectAll();
    }

    public void wordWrapAction() {
        if (textarea.isWrapText()){
            textarea.setWrapText(false);
            statusLabel.setText("Word Wrap was disabled.");
        }else {
            textarea.setWrapText(true);
            statusLabel.setText("Word Wrap was enabled");
        }
    }

    public void aboutAction() throws IOException {
        Dialog<Button> dialog = new Dialog<>();

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("about.fxml"));

        dialog.getDialogPane().setContent(fxmlLoader.load());

        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        dialog.showAndWait();

    }
}
