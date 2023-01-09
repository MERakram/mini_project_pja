package com.example.chat_app;

import com.example.chat_app.client.Client;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class ChatController implements Initializable {
    @FXML
    private Button send_btn;
    @FXML
    private TextArea msg_input;
    @FXML
    private ScrollPane chatScrollPane;
    @FXML
    private VBox msg_vbox;

    private Client client;
    private String userName;
    public ChatController(String userName) {
        this.userName = userName;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            client = new Client(new Socket("127.0.0.1",1000),userName);
        }catch (IOException e){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Server is off");
            // show the dialog
            a.show();
        }
        client.listenToMessage(msg_vbox);
        msg_vbox.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                chatScrollPane.setHvalue((Double) t1);
            }
        });
        send_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String messageToSend = msg_input.getText();
                if (!messageToSend.isEmpty()){
                    HBox hBox = new HBox();
                    hBox.setAlignment(Pos.CENTER_RIGHT);
                    hBox.setPadding(new Insets(5,5,5,10));
                    Text text = new Text(messageToSend);
                    TextFlow textFlow = new TextFlow(text);
                    textFlow.setStyle("-fx-background-color:rgba(216,47,47,0.65)" +"; -fx-background-radius:20px");
                    textFlow.setPadding(new Insets(5,10,5,10));
                    text.setFill(Color.color(0.934,0.945,0.996));
                    hBox.getChildren().add(textFlow);
                    msg_vbox.getChildren().add(hBox);
                    client.sendMessage(messageToSend);
                    msg_input.clear();
                }
            }
        });
    }
    public static void showReceivedMessages(String msgReceived,VBox vBox){
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPadding(new Insets(5,5,5,10));
        Text text = new Text(msgReceived);
        TextFlow textFlow = new TextFlow(text);
        textFlow.setStyle("-fx-background-color:rgba(0,0,0,0.51)" +"; -fx-background-radius:20px");
        textFlow.setPadding(new Insets(5,10,5,10));
        hBox.getChildren().add(textFlow);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                vBox.getChildren().add(hBox);
            }
        });
    }

}
