package com.example.chat_app.database;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class chat_dao {
    // TODO implement connect method alone
    public Boolean login(String username, String password) {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql:// localhost:3306/chat_db", "root", "");
            Statement stmt = con.createStatement();
            String login_query = "select username,email from users where username ='" + username + "' and password ='" + password + "'";
            ResultSet resultSet = stmt.executeQuery(login_query);
            if (resultSet.next()) {
                String user = resultSet.getString("username");
                String email = resultSet.getString("email");
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            return false;
        }
    }
    public static void Signup(String username, String password,String email) {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql:// localhost:3306/chat_db", "root", "");
            Statement stmt = con.createStatement();
            PreparedStatement insert= con.prepareStatement("insert into users(username,password,email) values(?,?,?)");
            insert.setString(1,username);
            insert.setString(2,password);
            insert.setString(3,email);
            int ligne_inserted = insert.executeUpdate ();
            if (ligne_inserted>0) {
                Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                a.setContentText("account created ");
                // show the dialog
                a.show();
            } else {
                // TODO Empty the textfields
                System.out.println("no user found with credentials");
            }
        } catch (Exception ex) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("something went wrong try again");
            // show the dialog
            a.show();
            System.out.println("connection failed because" + ex);
        }


    }
}
