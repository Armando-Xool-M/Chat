/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Cliente;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
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
import javax.sound.sampled.LineUnavailableException;
import javax.swing.JOptionPane;

public class ChatGUI extends Application {

    private TextField inputField = new TextField();;
    private TextArea chatArea = new TextArea();
    private ListView<String> usersList = new ListView<>();
    private Button sendButton = new Button("Send");

    private Socket cliente;
    public String nombre;
    private final int Puerto = 3000;
    public String host = "localhost";//10.182.2.206
    public DataOutputStream salida;
    private String texto;
    HiloCliente hilocliente;
    private String mensaje;
    private Socket audio;
    boolean micro = false;
    ChatClient client1;

    public ChatGUI(Socket cliente, String Username) {
        this.nombre = Username;
        try {
            this.cliente = cliente;
            hilocliente = new HiloCliente(this.cliente, this);
            hilocliente.start();
            audio = new Socket(host, 8000);
            salida = new DataOutputStream(this.cliente.getOutputStream());
            salida.writeUTF(nombre);
            salida.writeUTF("join:" + nombre + ":se ha conectado");
            client1 = new ChatClient(audio);
        } catch (IOException ex) {
            ex.printStackTrace();

        } catch (LineUnavailableException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 800, 600);

        // Input field
        
        inputField.setPrefHeight(40);
        inputField.setPrefWidth(400);
        inputField.setPromptText("Type your message here");
        inputField.setOnAction(e -> {
            sendMessage();
        });

        // Send button
        
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
        
        chatArea.setEditable(false);
        chatArea.setStyle("-fx-control-inner-background: #222222; -fx-text-fill: white;");

        // Users list
        
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
            try {
                salida.writeUTF("MSJ:" + nombre + ":" + message);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }
    }

    void mensaje(String mensaje) {
        System.out.println(mensaje);
        chatArea.appendText(mensaje + "\n");
        inputField.clear();
    }

    private void toggleDarkMode() {

        chatArea.setStyle("-fx-control-inner-background: #222222; -fx-text-fill: white;");
        usersList.setStyle("-fx-control-inner-background: #333333; -fx-text-fill: white;");
        inputField.setStyle("-fx-control-inner-background: #f4f4f4; -fx-text-fill: black;");
        sendButton.setStyle("-fx-background-color: #f4f4f4; -fx-text-fill: black;");

    }

    public void start() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("chat.fxml"));
        Parent rootNode = loader.load();

        Stage chatStage = new Stage();
        chatStage.setScene(new Scene(rootNode));
        chatStage.setTitle("Chat");
        chatStage.show();
    }
}
