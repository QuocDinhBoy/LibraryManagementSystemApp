package com.example.librarymanagementsystemapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class loginSceneController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private PasswordField passwordfield;

    @FXML
    private TextField usernamefield;

    @FXML
    private Label loginlabel;

    @FXML
    public void switchToCenterScene(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("centerScene.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    public void switchToRegistrationScene(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("registrationScene.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    public void setLoginbutton(ActionEvent event) {
        loginlabel.setAlignment(Pos.CENTER);
        if (usernamefield.getText().isBlank() == false && passwordfield.getText().isBlank() == false) {

            Database connectNow = new Database();
            Connection connectDB = connectNow.getConnection();

            String verifyLogin = "SELECT count(1) FROM user_account WHERE username = '" + usernamefield.getText() + "' AND password = '" + passwordfield.getText() + "'";
            try {
                Statement statement = connectDB.createStatement();
                ResultSet queryResult = statement.executeQuery(verifyLogin);

                while(queryResult.next()) {
                    if (queryResult.getInt(1) == 1) {
                        switchToCenterScene(event);
                    } else {
                        loginlabel.setText("Invalid login. Please try again!");
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                e.getCause();
            }
        } else {
            loginlabel.setText("Please enter username and password.");
        }
    }


}
