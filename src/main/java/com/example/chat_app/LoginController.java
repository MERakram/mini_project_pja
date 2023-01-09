package com.example.chat_app;

import com.example.chat_app.database.chat_dao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField username_input;
    @FXML
    private TextField password_input;
    @FXML
    private Button signup_btn;
    @FXML
    private Button login_btn;
    chat_dao dao = new chat_dao();

    public void login(ActionEvent actionEvent) throws IOException {
        String username = username_input.getText();
        String password = password_input.getText();
        Boolean authenticated = dao.login(username, password);
        if (authenticated) {
            Stage stage = (Stage) login_btn.getScene().getWindow();
            stage.close();
            Stage primaryStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("chat-view.fxml"));
            ChatController controller = new ChatController(username);
            loader.setController(controller);
            Parent root = loader.load();
            primaryStage.setTitle("Let's chat");
            primaryStage.setScene(new Scene(root, 600, 420));
            primaryStage.show();
        } else {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("something went wrong try again");
            // show the dialog
            a.show();
            username_input.setText("");
            password_input.setText("");
        }
    }

    public void Signup() throws IOException {
        Stage stage = (Stage) signup_btn.getScene().getWindow();
        stage.close();
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("signup-view.fxml"));
        primaryStage.setTitle("Signup");
        primaryStage.setScene(new Scene(root, 720, 480));
        primaryStage.show();
    }
}
