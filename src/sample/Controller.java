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


// Add Status bar
// Goto Function
// ask dialog before closing
// Help menu

public class Controller {
    @FXML
    public MenuItem saveMenu, saveAsMenu, openMenu, newMenu, quitMenu, undoMenu, redoMenu, cutMenu, pasteMenu, copyMenu, deleteMenu, findMenu, replaceMenu, goToMenu,
            selectAllMenu, wordWrapMenu, fontMenu, statusBarMenu, aboutMenu, whatIsItMenu;

    @FXML
    public BorderPane borderPane;
    @FXML
    public TextArea textarea;
    public Label statusLabel;

    private File file = null;
    private String fileData = null;

    public static boolean isSaved = true;

    private boolean showStatus = false;

    int row=0, col = 0;

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
        goToMenu.setAccelerator(new KeyCodeCombination(KeyCode.G, KeyCombination.CONTROL_DOWN));
        pasteMenu.setAccelerator(new KeyCodeCombination(KeyCode.P, KeyCombination.CONTROL_DOWN));
        deleteMenu.setAccelerator(new KeyCodeCombination(KeyCode.D, KeyCombination.CONTROL_DOWN));





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
        if (showStatus) {

        }
    }

    public void undoAction() {
        textarea.undo();
    }

    public void redoAction() {
        textarea.redo();
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

            for (int i = 0; i < textAreaLength-userFindLength; i++) {
                if (findDialogController.matchCase()) {
                    final String substring = text.substring(i, i + userFindLength);

                    if (substring.equalsIgnoreCase(userFind))
                        textarea.selectRange(i, i + userFindLength);
                    else if (substring.equals(userFind))
                        textarea.selectRange(i, i + userFindLength);
                }
            }
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

            textarea.setText(textarea.getText().replaceAll(userChoice.getFind(), userChoice.getReplace()));

        }

    }

    public void gotoAction() {
    }

    public void selectAllAction() {
        textarea.selectAll();
    }

    public void wordWrapAction() {
        textarea.setWrapText(!textarea.isWrapText());
    }

    public void fontAction() {
    }

    public void statusBarAction() {


        if (statusLabel.isVisible()){
            statusLabel.setVisible(true);
            showStatus = true;
        }else {
            statusLabel.setVisible(false);
        }

    }
}
