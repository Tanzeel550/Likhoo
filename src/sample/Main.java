package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.util.Optional;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Likhoo");
        primaryStage.setScene(new Scene(root, 800, 575));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws Exception {
        super.init();
    }

    @Override
    public void stop() throws Exception {
        if (!Controller.isSaved){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Data not changed");
            alert.setContentText("Are you sure to exit?");
            alert.getButtonTypes().add(ButtonType.YES);
            alert.getButtonTypes().add(ButtonType.NO);
            alert.getButtonTypes().add(ButtonType.CLOSE);

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.equals(ButtonType.YES)){
                // exit by saving the data
                Platform.exit();
            }
            else if (result.isPresent() && result.equals(ButtonType.NO)) {
                // Continue the notoo
            }
            else if (result.isPresent() && result.equals(ButtonType.CLOSE)) {
                // exit without saving
            }

        }else
            super.stop();
    }

}
