/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Cliente;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class LoginApp extends Application {

    private Socket cliente;
    private final int Puerto = 3000;
    public String host = "25.40.239.180";//10.182.2.206
    private Stage loginStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));

        // Crear los componentes de la ventana de inicio de sesión
        Label lblIP = new Label("Dirección IP:");
        TextField txtIP = new TextField();
        Label lblPort = new Label("Puerto:");
        TextField txtPort = new TextField();
        Label lblUsername = new Label("Nombre de usuario:");
        TextField txtUsername = new TextField();
        Button btnLogin = new Button("Iniciar sesión");

        // Crear el diseño y agregar los componentes
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));
        gridPane.add(lblIP, 0, 0);
        gridPane.add(txtIP, 1, 0);
        gridPane.add(lblPort, 0, 1);
        gridPane.add(txtPort, 1, 1);
        gridPane.add(lblUsername, 0, 2);
        gridPane.add(txtUsername, 1, 2);
        gridPane.add(btnLogin, 1, 3);

        // Crear la escena y agregar el diseño
        Scene scene = new Scene(gridPane, 400, 250);

        // Configurar la acción del botón de inicio de sesión
        btnLogin.setOnAction(event -> {
            // Obtener las entradas del usuario
            String ip = txtIP.getText();
            int port = Integer.parseInt(txtPort.getText());
            String username = txtUsername.getText();
            try {
                cliente = new Socket(ip, port);
            } catch (IOException ex) {
                Logger.getLogger(LoginApp.class.getName()).log(Level.SEVERE, null, ex);
            }

            // Validar las entradas del usuario
            if (cliente.isConnected()) {
                // Si las entradas del usuario son válidas, mostrar la aplicación principal
                primaryStage.setScene(new Scene(new Label("¡Inicio de sesión exitoso!")));
                //System.out.println("entraaa");
                ChatGUI chatGUI = new ChatGUI(cliente, username);
                chatGUI.start(new Stage());
                //primaryStage.close();
                //loginStage.hide();

            } else {
                // Si las entradas del usuario no son válidas, mostrar un mensaje de error
                new Alert(Alert.AlertType.ERROR, "Por favor, ingrese una dirección IP válida, un puerto válido y un nombre de usuario.").showAndWait();
            }
        });

        // Configurar la ventana principal
        primaryStage.setTitle("Inicio de sesión");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
