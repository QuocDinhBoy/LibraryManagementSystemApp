package com.example.librarymanagementsystemapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;

public class centerSceneController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;
    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;

    @FXML
    private AnchorPane add_form;

    @FXML
    private AnchorPane main_form;

    @FXML
    private Button addDocument_button;

    @FXML
    private TextField add_Author;

    @FXML
    private TextField add_ID;

    @FXML
    private TextField add_Quantity;

    @FXML
    private TextField add_Title;

    @FXML
    private Button add_add;

    @FXML
    private Button add_clear;

    @FXML
    private Button add_delete;

    @FXML
    private TextField add_search;

    @FXML
    private TableView<?> add_tableview;

    @FXML
    private Button add_update;

    @FXML
    private TableColumn<?, ?> author_column;

    @FXML
    private Button borrowers_button;

    @FXML
    private Button home_button;

    @FXML
    private TableColumn<?, ?> id_column;

    @FXML
    private TableColumn<?, ?> quantity_column;

    @FXML
    private TableColumn<?, ?> title_column;

    @FXML
    private AnchorPane borrowers_form;

    @FXML
    private AnchorPane home_form;

    @FXML
    public void switchLoginScene(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("loginScene.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    public void close(){
        System.exit(0);
    }

    public void minimize() {
        Stage stage = (Stage) main_form.getScene().getWindow();
        stage.setIconified(true);
    }

    public void logout(ActionEvent event) throws Exception {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Logout");
            alert.setHeaderText("Are you sure you want to logout?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {
                switchLoginScene(event);
            }
    }

    @FXML
    public void switchForm(ActionEvent event) {
        if (event.getSource() == home_button) {
            home_form.setVisible(true);
            add_form.setVisible(false);
            borrowers_form.setVisible(false);

            home_button.setStyle("-fx-background-color:linear-gradient(to bottom right, #52ae8b, #9a2d3d);");
            addDocument_button.setStyle("-fx-background-color:transparent");
            borrowers_button.setStyle("-fx-background-color:transparent");
        } else if (event.getSource() == addDocument_button) {
            home_form.setVisible(false);
            add_form.setVisible(true);
            borrowers_form.setVisible(false);

            home_button.setStyle("-fx-background-color:transparent");
            addDocument_button.setStyle("-fx-background-color:linear-gradient(to bottom right, #52ae8b, #9a2d3d);");
            borrowers_button.setStyle("-fx-background-color:transparent");
        } else if (event.getSource() == borrowers_button) {
            borrowers_form.setVisible(true);
            home_form.setVisible(false);
            add_form.setVisible(false);

            home_button.setStyle("-fx-background-color:transparent");
            addDocument_button.setStyle("-fx-background-color:transparent");
            borrowers_button.setStyle("-fx-background-color:linear-gradient(to bottom right, #52ae8b, #9a2d3d);");
        }
    }

//    public ObservableList<Document> documentList() {
//        ObservableList<Document> documents = FXCollections.observableArrayList();
//
//        String sql = "SELECT * FORM document";
//        Database connectNow = new Database();
//        connect = connectNow.getConnection();
//
//        try {
//            prepare = connect.prepareStatement(sql);
//            result = prepare.executeQuery();
//            Document document;
//            while (result.next()) {
//
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    public void initialize (URL Location, ResourceBundle resources){
    }
}
