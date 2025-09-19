package com.alanthechatbot.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

import com.alanthechatbot.exceptions.StorageException;
import com.alanthechatbot.gui.MainWindow;
import com.alanthechatbot.storage.Storage;

/**
 * A GUI for Duke using FXML.
 */
public class Main extends Application {

    private final AlanTheChatBot alan = new AlanTheChatBot();

    /**
     * Initializes the application
     *
     * @param stage the stage for the scene to be set
     */
    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setAlan(alan);
            Storage.init();
            alan.replayStoredActions();
            stage.setTitle("Alan");
            stage.show();
        } catch (IOException | StorageException e) {
            System.out.println(e.getMessage());
        }
    }
}
