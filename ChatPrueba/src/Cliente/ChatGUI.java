/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Cliente;

import java.net.Socket;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ChatGUI extends Application {



    private TextField inputField;
    private TextArea chatArea;
    private ListView<String> usersList;
    private Button sendButton;
    private boolean darkMode = false;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 800, 600);

        // Input field
        inputField = new TextField();
        inputField.setPrefHeight(40);
        inputField.setPrefWidth(400);
        inputField.setPromptText("Type your message here");

        inputField.setOnAction(e -> {
            sendMessage();
        });

        // Send button
        sendButton = new Button("Send");
        sendButton.setPrefHeight(40);
        sendButton.setPrefWidth(60);
        sendButton.setOnAction(e -> {
            sendMessage();
        });

        // Input box
        HBox inputBox = new HBox(inputField, sendButton);
        inputBox.setSpacing(10);
        inputBox.setPadding(new Insets(10));

        // Chat area
        chatArea = new TextArea();
        chatArea.setEditable(false);
        chatArea.setStyle("-fx-control-inner-background: #222222; -fx-text-fill: white;");

        // Users list
        usersList = new ListView<>();
        usersList.setStyle("-fx-control-inner-background: #333333; -fx-text-fill: white;");
        usersList.getItems().addAll("User 1", "User 2", "User 3");

        // Add components to root
        root.setCenter(chatArea);
        root.setRight(usersList);
        root.setBottom(inputBox);

        // Set scene
        primaryStage.setScene(scene);
        primaryStage.setTitle("Chat");

        // Toggle dark mode
        scene.setOnKeyPressed(e -> {
            if (e.isControlDown() && e.getCode().equals(KeyCode.D)) {
                toggleDarkMode();
            }
        });

        primaryStage.show();
    }

    private void sendMessage() {
        String message = inputField.getText();
        if (!message.isEmpty()) {
            chatArea.appendText(message + "\n");
            inputField.clear();
        }
    }

    private void toggleDarkMode() {

        chatArea.setStyle("-fx-control-inner-background: #222222; -fx-text-fill: white;");
        usersList.setStyle("-fx-control-inner-background: #333333; -fx-text-fill: white;");
        inputField.setStyle("-fx-control-inner-background: #f4f4f4; -fx-text-fill: black;");
        sendButton.setStyle("-fx-background-color: #f4f4f4; -fx-text-fill: black;");
        darkMode = false;

    }
}
