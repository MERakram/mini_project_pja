package com.example.chat_app;

import com.example.chat_app.database.chat_dao;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class SignupController {
    @FXML
    private TextField username_input;
    @FXML
    private TextField password_input;
    @FXML
    private TextField email_input;
    @FXML
            private Button back;
    chat_dao dao = new chat_dao();
    public void Signup(){
        String username = username_input.getText();
        String password = password_input.getText();
        String email = email_input.getText();
        dao.Signup(username,password,email);
    }
    public  void back() throws IOException {
        Stage stage = (Stage) back.getScene().getWindow();
        stage.close();
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("login-view.fxml"));
        primaryStage.setTitle("login");
        primaryStage.setScene(new Scene(root, 720, 480));
        primaryStage.show();
    }
}
