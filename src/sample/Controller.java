package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;

import java.io.File;

public class Controller {
    @FXML
    public MenuItem saveMenu, saveAsMenu, openMenu, newMenu, quitMenu, undoMenu, redoMenu, cutMenu, pasteMenu, copyMenu, deleteMenu, findMenu, replaceMenu, goToMenu,
            selectAllMenu, wordWrapMenu, fontMenu, statusBarMenu, aboutMenu, whatIsItMenu;

    @FXML
    public BorderPane borderPane;
    @FXML
    public TextArea textarea;

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
        goToMenu.setAccelerator(new KeyCodeCombination(KeyCode.G, KeyCombination.CONTROL_DOWN));





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

    public void findAction() {
        // Open up the dialog for finding.

    }

    public void replaceAction() {
        // open up the dialog for both finding and replacing
    }

    public void gotoAction() {
    }

    public void selectAllAction() {
        textarea.selectAll();
    }
}
