package com.example.librarymanagementsystemapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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
import java.util.SortedMap;

public class centerSceneController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;
    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;
    private ObservableList<Document> addDocumentlist;
    private ObservableList<Borrower> borrowerList;

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
    private TableView<Document> add_tableview;

    @FXML
    private Button add_update;

    @FXML
    private TableColumn<Document, String> author_column;

    @FXML
    private Button borrowers_button;

    @FXML
    private Button home_button;

    @FXML
    private TableColumn<Document, Integer> id_column;

    @FXML
    private TableColumn<Document, Integer> quantity_column;

    @FXML
    private TableColumn<Document, String> title_column;

    @FXML
    private AnchorPane borrowers_form;

    @FXML
    private AnchorPane home_form;


    @FXML
    private TextField borrowers_ID;

    @FXML
    private Button borrowers_add;

    @FXML
    private TextField borrowers_name;

    @FXML
    private TextField borrowers_phone;

    @FXML
    private TextField borrowers_citizenid;

    @FXML
    private Button borrowers_delete;

    @FXML
    private Button borrowers_clear;

    @FXML
    private Button borrowers_update;

    @FXML
    private TableColumn<Borrower, Integer> borrowerId_column;

    @FXML
    private TableColumn<Borrower, String> borrowerName_column;

    @FXML
    private TableColumn<Borrower, String> borrowerPhone_column;

    @FXML
    private TableColumn<Borrower, String> citizenid_column;

    @FXML
    private TableView<Borrower> borrowers_tableview;

    @FXML
    private TextField borrower_search;

    @FXML
    private AnchorPane issuedocument_form;

    @FXML
    private Button issueDocument_button;

    @FXML
    private Label nameBorrowerDetails;

    @FXML
    private Label titleDocumentDetails;

    @FXML
    private Label phoneBorrowerDetails;

    @FXML
    private Label idBorrowerDetails;

    @FXML
    private Label idDocumentDetails;

    @FXML
    private Label citizenidBorrowerDetails;

    @FXML
    private Label authorDocumentDetails;

    @FXML
    private Label quantityDocumentDetails;

    @FXML
    private DatePicker issueDateField;

    @FXML
    private DatePicker dueDateField;

    @FXML
    private TextField idBorrowerIssue;

    @FXML
    private TextField idDocumentIssue;

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
            issuedocument_form.setVisible(false);

            home_button.setStyle("-fx-background-color:linear-gradient(to bottom right, #52ae8b, #9a2d3d);");
            addDocument_button.setStyle("-fx-background-color:transparent");
            borrowers_button.setStyle("-fx-background-color:transparent");
            issueDocument_button.setStyle("-fx-background-color:transparent");
        } else if (event.getSource() == addDocument_button) {
            home_form.setVisible(false);
            add_form.setVisible(true);
            borrowers_form.setVisible(false);
            issuedocument_form.setVisible(false);

            home_button.setStyle("-fx-background-color:transparent");
            addDocument_button.setStyle("-fx-background-color:linear-gradient(to bottom right, #52ae8b, #9a2d3d);");
            borrowers_button.setStyle("-fx-background-color:transparent");
            issueDocument_button.setStyle("-fx-background-color:transparent");

            add_search.clear();
            showListDocument();
            search();
        } else if (event.getSource() == borrowers_button) {
            borrowers_form.setVisible(true);
            home_form.setVisible(false);
            add_form.setVisible(false);
            issuedocument_form.setVisible(false);

            home_button.setStyle("-fx-background-color:transparent");
            addDocument_button.setStyle("-fx-background-color:transparent");
            borrowers_button.setStyle("-fx-background-color:linear-gradient(to bottom right, #52ae8b, #9a2d3d);");
            issueDocument_button.setStyle("-fx-background-color:transparent");

            borrower_search.clear();
            showListBorrower();
            borrowers_search();
        } else if (event.getSource() == issueDocument_button) {
            borrowers_form.setVisible(false);
            home_form.setVisible(false);
            add_form.setVisible(false);
            issuedocument_form.setVisible(true);

            home_button.setStyle("-fx-background-color:transparent");
            addDocument_button.setStyle("-fx-background-color:transparent");
            borrowers_button.setStyle("-fx-background-color:transparent");
            issueDocument_button.setStyle("-fx-background-color:linear-gradient(to bottom right, #52ae8b, #9a2d3d);");

            borrower_search.clear();
        }
    }

    public ObservableList<Document> documentList() {
        ObservableList<Document> documents = FXCollections.observableArrayList();

        String sql = "SELECT * FROM document";
        Database connectNow = new Database();
        connect = connectNow.getConnection();

        try {
            statement = connect.createStatement();
            result = statement.executeQuery(sql);
            Document document;
            while (result.next()) {
                document = new Document(result.getInt("document_id"),
                        result.getString("title"),
                        result.getString("author"),
                        result.getInt("quantity"));
                documents.add(document);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return documents;
    }

    public void showListDocument() {
        addDocumentlist = documentList();
        id_column.setCellValueFactory(new PropertyValueFactory<>("id"));
        title_column.setCellValueFactory(new PropertyValueFactory<>("title"));
        author_column.setCellValueFactory(new PropertyValueFactory<>("author"));
        quantity_column.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        add_tableview.setItems(addDocumentlist);

        id_column.setResizable(false);
        title_column.setResizable(false);
        author_column.setResizable(false);
        quantity_column.setResizable(false);
    }

    public void manageDocument_addButton() {
        String sql = "INSERT INTO document (document_id, title, author, quantity) VALUES(?,?,?,?)";

        Database connectNow = new Database();
        connect = connectNow.getConnection();

        try {
            Alert alert;

            if (add_ID.getText().isEmpty() || add_Title.getText().isEmpty()
                || add_Author.getText().isEmpty() || add_Quantity.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Missing Information");
                alert.setContentText("Please enter all required information in each field!");
                alert.showAndWait();
            } else {
                int quantity;
                int id;
                try {
                    id = Integer.parseInt(add_ID.getText());
                } catch (NumberFormatException e) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Invalid ID");
                    alert.setContentText("The ID must be a valid integer");
                    alert.showAndWait();
                    return;
                }
                String checkID = "SELECT document_id FROM document WHERE document_id = '" + add_ID.getText() + "'";
                statement = connect.createStatement();
                result = statement.executeQuery(checkID);
                if (result.next()) {
                    alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Duplicate ID");
                    alert.setContentText("This ID already exists. Please enter a different ID.");
                    alert.showAndWait();
                    return;
                }

                try {
                    quantity = Integer.parseInt(add_Quantity.getText());
                } catch (NumberFormatException e) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Invalid Quantity");
                    alert.setContentText("The quantity must be a valid integer");
                    alert.showAndWait();
                    return;
                }

                prepare = connect.prepareStatement(sql);

                prepare.setInt(1, id);
                prepare.setString(2, add_Title.getText());
                prepare.setString(3, add_Author.getText());
                prepare.setInt(4, quantity);
                prepare.executeUpdate();
                showListDocument();
                manageDocument_clearButton();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void manageDocument_clearButton() {
        add_ID.clear();
        add_Quantity.clear();
        add_Author.clear();
        add_Title.clear();
    }

    public void manageDocument_updateButton() {
        String sql = "UPDATE document SET title = '"
                + add_Title.getText() + "', author = '"
                + add_Author.getText() + "', quantity = '"
                + add_Quantity.getText() + "' WHERE document_id = '"
                + add_ID.getText() + "'";
        Database connectNow = new Database();
        connect = connectNow.getConnection();
        try {
            Alert alert;

            if (add_ID.getText().isEmpty() || add_Title.getText().isEmpty()
                    || add_Author.getText().isEmpty() || add_Quantity.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Missing Information");
                alert.setContentText("Please enter all required information in each field!");
                alert.showAndWait();
            } else {
                int quantity;
                int id;
                try {
                    id = Integer.parseInt(add_ID.getText());
                } catch (NumberFormatException e) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Invalid ID");
                    alert.setContentText("The ID must be a valid integer");
                    alert.showAndWait();
                    return;
                }
                try {
                    quantity = Integer.parseInt(add_Quantity.getText());
                } catch (NumberFormatException e) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Invalid Quantity");
                    alert.setContentText("The quantity must be a valid integer");
                    alert.showAndWait();
                    return;
                }
                String checkID = "SELECT * FROM document WHERE document_id = '" + add_ID.getText() + "'";
                statement = connect.createStatement();
                result = statement.executeQuery(checkID);

                if (!result.next()) {
                    alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Document Not Found");
                    alert.setContentText("The document with ID " + add_ID.getText() + " does not exist.");
                    alert.showAndWait();
                    return;
                }
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to update:\nDocument ID: " + add_ID.getText() + "\n"
                                    + "Title: " + add_Title.getText() + "\n"
                                    + "Author: " + add_Author.getText() + "\n"
                                    + "Quantity: " + add_Quantity.getText());
                Optional<ButtonType> option = alert.showAndWait();
                if (option.get().equals(ButtonType.OK)) {
                    statement = connect.createStatement();
                    statement.executeUpdate(sql);
                    showListDocument();
                    manageDocument_clearButton();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void manageDocument_deleteButton() {
        String sql = "DELETE FROM document WHERE document_id = '" + add_ID.getText() + "'";
        Database connectNow = new Database();
        connect = connectNow.getConnection();
        try {
            Alert alert;

            if (add_ID.getText().isEmpty() || add_Title.getText().isEmpty()
                    || add_Author.getText().isEmpty() || add_Quantity.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Missing Information");
                alert.setContentText("Please enter all required information in each field!");
                alert.showAndWait();
            } else {
                int quantity;
                int id;
                try {
                    id = Integer.parseInt(add_ID.getText());
                } catch (NumberFormatException e) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Invalid ID");
                    alert.setContentText("The ID must be a valid integer");
                    alert.showAndWait();
                    return;
                }
                try {
                    quantity = Integer.parseInt(add_Quantity.getText());
                } catch (NumberFormatException e) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Invalid Quantity");
                    alert.setContentText("The quantity must be a valid integer");
                    alert.showAndWait();
                    return;
                }
                String checkID = "SELECT * FROM document WHERE document_id = '" + add_ID.getText() + "'";
                statement = connect.createStatement();
                result = statement.executeQuery(checkID);

                if (!result.next()) {
                    alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Document Not Found");
                    alert.setContentText("The document with ID " + add_ID.getText() + " does not exist.");
                    alert.showAndWait();
                    return;
                }
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to delete:\nDocument ID: " + add_ID.getText() + "\n"
                        + "Title: " + add_Title.getText() + "\n"
                        + "Author: " + add_Author.getText() + "\n"
                        + "Quantity: " + add_Quantity.getText());
                Optional<ButtonType> option = alert.showAndWait();
                if (option.get().equals(ButtonType.OK)) {
                    statement = connect.createStatement();
                    statement.executeUpdate(sql);
                    showListDocument();
                    manageDocument_clearButton();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addDocumentSelected() {
        add_tableview.setOnMouseClicked(event -> {
            Document selectedDocument = add_tableview.getSelectionModel().getSelectedItem();
            if (selectedDocument != null) {
                add_ID.setText(String.valueOf(selectedDocument.getId()));
                add_Title.setText(selectedDocument.getTitle());
                add_Author.setText(selectedDocument.getAuthor());
                add_Quantity.setText(String.valueOf(selectedDocument.getQuantity()));
            }
        });
    }

    public void search() {
        FilteredList<Document> filteredList = new FilteredList<>(addDocumentlist, b -> true);
        add_search.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(document-> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String searchKey = newValue.toLowerCase();
                if (String.valueOf(document.getId()).toLowerCase().contains(searchKey)) {
                    return true;
                } else if (document.getTitle().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (document.getAuthor().toLowerCase().contains(searchKey)) {
                    return true;
                }
                return false;
            });
        });
        SortedList<Document> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(add_tableview.comparatorProperty());
        add_tableview.setItems(sortedList);
    }

    public ObservableList<Borrower> borrowerList() {
        ObservableList<Borrower> borrowers = FXCollections.observableArrayList();

        String sql = "SELECT * FROM borrower";
        Database connectNow = new Database();
        connect = connectNow.getConnection();

        try {
            statement = connect.createStatement();
            result = statement.executeQuery(sql);
            Borrower borrower;
            while (result.next()) {
                borrower = new Borrower(result.getInt("borrower_id"),
                        result.getString("name"),
                        result.getString("phone"),
                        result.getString("citizen_id"));
                borrowers.add(borrower);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return borrowers;
    }

    public void showListBorrower() {
        borrowerList = borrowerList();
        borrowerId_column.setCellValueFactory(new PropertyValueFactory<>("id"));
        borrowerName_column.setCellValueFactory(new PropertyValueFactory<>("name"));
        borrowerPhone_column.setCellValueFactory(new PropertyValueFactory<>("phone"));
        citizenid_column.setCellValueFactory(new PropertyValueFactory<>("citizenID"));
        borrowers_tableview.setItems(borrowerList);

        borrowerId_column.setResizable(false);
        borrowerName_column.setResizable(false);
        citizenid_column.setResizable(false);
        borrowerPhone_column.setResizable(false);
    }

    public void borrowers_addButton() {
        String sql = "INSERT INTO borrower(borrower_id, name, phone, citizen_id) VALUES(?,?,?,?)";

        Database connectNow = new Database();
        connect = connectNow.getConnection();

        try {
            Alert alert;

            if (borrowers_ID.getText().isEmpty() || borrowers_name.getText().isEmpty()
                    || borrowers_phone.getText().isEmpty() || borrowers_citizenid.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Missing Information");
                alert.setContentText("Please enter all required information in each field!");
                alert.showAndWait();
            } else {
                int id;
                try {
                    id = Integer.parseInt(borrowers_ID.getText());
                } catch (NumberFormatException e) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Invalid ID");
                    alert.setContentText("The ID must be a valid integer");
                    alert.showAndWait();
                    return;
                }
                String checkID = "SELECT document_id FROM document WHERE document_id = '" + add_ID.getText() + "'";
                statement = connect.createStatement();
                result = statement.executeQuery(checkID);
                if (result.next()) {
                    alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Duplicate ID");
                    alert.setContentText("This ID already exists. Please enter a different ID.");
                    alert.showAndWait();
                    return;
                }

                prepare = connect.prepareStatement(sql);

                prepare.setInt(1, id);
                prepare.setString(2, borrowers_name.getText());
                prepare.setString(3, borrowers_phone.getText());
                prepare.setString(4, borrowers_citizenid.getText());
                prepare.executeUpdate();
                showListBorrower();
                borrowers_clearButton();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void borrowers_clearButton() {
        borrowers_ID.clear();
        borrowers_name.clear();
        borrowers_phone.clear();
        borrowers_citizenid.clear();
    }

    public void borrowers_updateButton() {
        String sql = "UPDATE borrower SET name = '"
                + borrowers_name.getText() + "', phone = '"
                + borrowers_phone.getText() + "', citizen_id = '"
                + borrowers_citizenid.getText() + "' WHERE borrower_id = '"
                + borrowers_ID.getText() + "'";
        Database connectNow = new Database();
        connect = connectNow.getConnection();
        try {
            Alert alert;

            if (borrowers_ID.getText().isEmpty() || borrowers_name.getText().isEmpty()
                    || borrowers_phone.getText().isEmpty() || borrowers_citizenid.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Missing Information");
                alert.setContentText("Please enter all required information in each field!");
                alert.showAndWait();
            } else {
                try {
                    int id = Integer.parseInt(borrowers_ID.getText());
                } catch (NumberFormatException e) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Invalid ID");
                    alert.setContentText("The ID must be a valid integer");
                    alert.showAndWait();
                    return;
                }
                String checkID = "SELECT * FROM borrower WHERE borrower_id = '" + borrowers_ID.getText() + "'";
                statement = connect.createStatement();
                result = statement.executeQuery(checkID);

                if (!result.next()) {
                    alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Borrower Not Found");
                    alert.setContentText("Borrower with ID " + borrowers_ID.getText() + " does not exist.");
                    alert.showAndWait();
                    return;
                }
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to update:\nBorrower ID: " + borrowers_ID.getText() + "\n"
                        + "Name: " + borrowers_name.getText() + "\n"
                        + "Phone: " + borrowers_phone.getText() + "\n"
                        + "Citizen ID: " + borrowers_citizenid.getText());
                Optional<ButtonType> option = alert.showAndWait();
                if (option.get().equals(ButtonType.OK)) {
                    statement = connect.createStatement();
                    statement.executeUpdate(sql);
                    showListBorrower();
                    borrowers_clearButton();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void borrowers_deleteButton() {
        String sql = "DELETE FROM borrower WHERE borrower_id = '" + borrowers_ID.getText() + "'";
        Database connectNow = new Database();
        connect = connectNow.getConnection();
        try {
            Alert alert;

            if (borrowers_ID.getText().isEmpty() || borrowers_name.getText().isEmpty()
                    || borrowers_phone.getText().isEmpty() || borrowers_citizenid.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Missing Information");
                alert.setContentText("Please enter all required information in each field!");
                alert.showAndWait();
            } else {
                try {
                    int id = Integer.parseInt(borrowers_ID.getText());
                } catch (NumberFormatException e) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Invalid ID");
                    alert.setContentText("The ID must be a valid integer");
                    alert.showAndWait();
                    return;
                }

                String checkID = "SELECT * FROM borrower WHERE borrower_id = '" + borrowers_ID.getText() + "'";
                statement = connect.createStatement();
                result = statement.executeQuery(checkID);

                if (!result.next()) {
                    alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Borrower Not Found");
                    alert.setContentText("Borrower with ID " + borrowers_ID.getText() + " does not exist.");
                    alert.showAndWait();
                    return;
                }
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to delete:\nBorrower ID: " + borrowers_ID.getText() + "\n"
                        + "Name: " + borrowers_name.getText() + "\n"
                        + "Phone: " + borrowers_phone.getText() + "\n"
                        + "Citizen ID: " + borrowers_citizenid.getText());
                Optional<ButtonType> option = alert.showAndWait();
                if (option.get().equals(ButtonType.OK)) {
                    statement = connect.createStatement();
                    statement.executeUpdate(sql);
                    showListBorrower();
                    borrowers_clearButton();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void borrowersSelected() {
        borrowers_tableview.setOnMouseClicked(event -> {
            Borrower borrowerSelected = borrowers_tableview.getSelectionModel().getSelectedItem();
            if (borrowerSelected != null) {
                borrowers_ID.setText(String.valueOf(borrowerSelected.getId()));
                borrowers_name.setText(borrowerSelected.getName());
                borrowers_phone.setText(borrowerSelected.getPhone());
                borrowers_citizenid.setText(borrowerSelected.getCitizenID());
            }
        });
    }

    public void borrowers_search() {
        FilteredList<Borrower> filteredList = new FilteredList<>(borrowerList, b -> true);
        borrower_search.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(borrower -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String searchKey = newValue.toLowerCase();
                if (String.valueOf(borrower.getId()).toLowerCase().contains(searchKey)) {
                    return true;
                } else if (borrower.getName().toLowerCase().contains(searchKey)) {
                    return true;
                }
                return false;
            });
        });
        SortedList<Borrower> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(borrowers_tableview.comparatorProperty());
        borrowers_tableview.setItems(sortedList);
    }

    @FXML
    public void initialize (URL Location, ResourceBundle resources) {
        showListDocument();
        showListBorrower();
        addDocumentSelected();
        borrowersSelected();
        search();
        borrowers_search();
    }
}
