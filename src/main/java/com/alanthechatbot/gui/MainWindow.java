package com.alanthechatbot.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;

import com.alanthechatbot.app.AlanTheChatBot;
import com.alanthechatbot.exceptions.InputParsingException;
import com.alanthechatbot.parse.ParsedInput;
import com.alanthechatbot.parse.Parser;
import com.alanthechatbot.storage.Storage;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private AlanTheChatBot alan;
    private final Image userImage = new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("/images/user.png")));
    private final Image alanImage = new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("/images/alan.png")));
    private final Image spaceBackgroundImage = new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("/images/bg.jpg")));


    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        scrollPane.viewportBoundsProperty().addListener((obs, oldBounds, newBounds) -> {
            dialogContainer.setMinWidth(newBounds.getWidth());
        });
    }

    public void setAlan(AlanTheChatBot a) {
        alan = a;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response;
        try {
            HashSet<String> actionsToNotSave = new HashSet<>();
            Collections.addAll(actionsToNotSave, "list", "find", "bye", "invalid input");
            ParsedInput parsedInput = new Parser(input).parse();
            response = alan.runParsedInput(parsedInput);
            if (!actionsToNotSave.contains(parsedInput.actionType())) {
                System.out.println("saving");
                Storage.writeToFile(input + "\n");
            }
        } catch (InputParsingException e) {
            response = e.getMessage();
        }
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getAlanDialog(response, alanImage)
        );
        userInput.clear();
    }
}
