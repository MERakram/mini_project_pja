module com.example.chat_app {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.chat_app to javafx.fxml;
    exports com.example.chat_app;
    exports com.example.chat_app.client;
    opens com.example.chat_app.client to javafx.fxml;
}