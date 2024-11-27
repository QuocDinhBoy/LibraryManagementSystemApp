package com.example.librarymanagementsystemapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.print.Doc;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.sql.Date;
import java.util.*;
import java.util.List;

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
    private ObservableList<BorrowTransaction> borrowerTransactionList;
    private ObservableList<BorrowTransaction> borrowTransactionList;
    private Image image;
    private String path;
    private int checkViewRecords = 0;
    private static final int AllRecords = 0;
    private static final int IssuedList = 1;
    private static final int DefaulterList = 2;

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
    private Button returnDocument_button;

    @FXML
    private AnchorPane returnDocument_form;

    @FXML
    private TableView<BorrowTransaction> tableView_returnDocument;

    @FXML
    private TableColumn<BorrowTransaction, Integer> transactionIdColumn_returnDocument;

    @FXML
    private TableColumn<BorrowTransaction, String> borrowerNameColumn_returnDocument;

    @FXML
    private TableColumn<BorrowTransaction, String> documentName_returnDocument;

    @FXML
    private TableColumn<BorrowTransaction, Date> issueDate_returnDocument;

    @FXML
    private TableColumn<BorrowTransaction, Date> dueDate_returnDocument;

    @FXML
    private TableColumn<BorrowTransaction, String> status_returnDocument;

    @FXML
    private TextField idBorrowerField_returnDocument;

    @FXML
    private Button viewRecords_button;

    @FXML
    private AnchorPane viewRecords_form;

    @FXML
    private Button allRecords_button;

    @FXML
    private Button issuedList_button;

    @FXML
    private Button defaulterList_button;

    @FXML
    private TableView<BorrowTransaction> tableView_viewRecords;

    @FXML
    private TableColumn<BorrowTransaction, Integer> idColumn_viewRecords;

    @FXML
    private TableColumn<BorrowTransaction, String> borrowerName_viewRecords;

    @FXML
    private TableColumn<BorrowTransaction, String> documentName_viewRecords;

    @FXML
    private TableColumn<BorrowTransaction, Date> issueDate_ViewRecords;

    @FXML
    private TableColumn<BorrowTransaction, Date> dueDate_viewRecords;

    @FXML
    private TableColumn<BorrowTransaction, String> status_viewRecords;

    @FXML
    private TableColumn<BorrowTransaction, Date> returnDate_viewRecords;

    @FXML
    private ImageView imageView_manageDocument;

    @FXML
    private Label availableDocuments_home;

    @FXML
    private Label issuedDocument_home;

    @FXML
    private Label totalBorrowers_home;

    @FXML
    private GridPane documentContainer;

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
            returnDocument_form.setVisible(false);
            viewRecords_form.setVisible(false);


            home_button.setStyle("-fx-background-color:linear-gradient(to bottom right, #52ae8b, #9a2d3d);");
            addDocument_button.setStyle("-fx-background-color:transparent");
            borrowers_button.setStyle("-fx-background-color:transparent");
            issueDocument_button.setStyle("-fx-background-color:transparent");
            returnDocument_button.setStyle("-fx-background-color:transparent");
            viewRecords_button.setStyle("-fx-background-color:transparent");

            availableDocuments_home();
            issuedDocuments_home();
            totalBorrowers_home();
            hottest_home();

        } else if (event.getSource() == addDocument_button) {
            home_form.setVisible(false);
            add_form.setVisible(true);
            borrowers_form.setVisible(false);
            issuedocument_form.setVisible(false);
            returnDocument_form.setVisible(false);
            viewRecords_form.setVisible(false);

            home_button.setStyle("-fx-background-color:transparent");
            addDocument_button.setStyle("-fx-background-color:linear-gradient(to bottom right, #52ae8b, #9a2d3d);");
            borrowers_button.setStyle("-fx-background-color:transparent");
            issueDocument_button.setStyle("-fx-background-color:transparent");
            returnDocument_button.setStyle("-fx-background-color:transparent");
            viewRecords_button.setStyle("-fx-background-color:transparent");

            add_search.clear();
            showListDocument();
            search();
        } else if (event.getSource() == borrowers_button) {
            borrowers_form.setVisible(true);
            home_form.setVisible(false);
            add_form.setVisible(false);
            issuedocument_form.setVisible(false);
            returnDocument_form.setVisible(false);
            viewRecords_form.setVisible(false);

            home_button.setStyle("-fx-background-color:transparent");
            addDocument_button.setStyle("-fx-background-color:transparent");
            borrowers_button.setStyle("-fx-background-color:linear-gradient(to bottom right, #52ae8b, #9a2d3d);");
            issueDocument_button.setStyle("-fx-background-color:transparent");
            returnDocument_button.setStyle("-fx-background-color:transparent");
            viewRecords_button.setStyle("-fx-background-color:transparent");

            borrower_search.clear();
            showListBorrower();
            borrowers_search();
        } else if (event.getSource() == issueDocument_button) {
            borrowers_form.setVisible(false);
            home_form.setVisible(false);
            add_form.setVisible(false);
            issuedocument_form.setVisible(true);
            returnDocument_form.setVisible(false);
            viewRecords_form.setVisible(false);

            home_button.setStyle("-fx-background-color:transparent");
            addDocument_button.setStyle("-fx-background-color:transparent");
            borrowers_button.setStyle("-fx-background-color:transparent");
            issueDocument_button.setStyle("-fx-background-color:linear-gradient(to bottom right, #52ae8b, #9a2d3d);");
            returnDocument_button.setStyle("-fx-background-color:transparent");
            viewRecords_button.setStyle("-fx-background-color:transparent");

        } else if (event.getSource() == returnDocument_button) {
            borrowers_form.setVisible(false);
            home_form.setVisible(false);
            add_form.setVisible(false);
            issuedocument_form.setVisible(false);
            returnDocument_form.setVisible(true);
            viewRecords_form.setVisible(false);

            home_button.setStyle("-fx-background-color:transparent");
            addDocument_button.setStyle("-fx-background-color:transparent");
            borrowers_button.setStyle("-fx-background-color:transparent");
            issueDocument_button.setStyle("-fx-background-color:transparent");
            returnDocument_button.setStyle("-fx-background-color:linear-gradient(to bottom right, #52ae8b, #9a2d3d);");
            viewRecords_button.setStyle("-fx-background-color:transparent");
            idBorrowerField_returnDocument.clear();
        } else if (event.getSource() == viewRecords_button) {
            borrowers_form.setVisible(false);
            home_form.setVisible(false);
            add_form.setVisible(false);
            issuedocument_form.setVisible(false);
            returnDocument_form.setVisible(false);
            viewRecords_form.setVisible(true);

            home_button.setStyle("-fx-background-color:transparent");
            addDocument_button.setStyle("-fx-background-color:transparent");
            borrowers_button.setStyle("-fx-background-color:transparent");
            issueDocument_button.setStyle("-fx-background-color:transparent");
            returnDocument_button.setStyle("-fx-background-color:transparent");
            viewRecords_button.setStyle("-fx-background-color:linear-gradient(to bottom right, #52ae8b, #9a2d3d);");

            showBorrowTransactionList();
            borrowTransactionList = getViewRecords();
            tableView_viewRecords.setItems(borrowTransactionList);
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
                        result.getInt("quantity"),
                         result.getString("image"));
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
        String sql = "INSERT INTO document (document_id, title, author, quantity, image) VALUES(?,?,?,?,?)";

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
                if (path != null) {
                    path = path.replace("\\", "/");
                }
                prepare = connect.prepareStatement(sql);

                prepare.setInt(1, id);
                prepare.setString(2, add_Title.getText());
                prepare.setString(3, add_Author.getText());
                prepare.setInt(4, quantity);
                prepare.setString(5, path);
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
        if (path != null) {
            path = path.replace("\\", "/");
        }
        String sql = "UPDATE document SET title = '"
                + add_Title.getText() + "', author = '"
                + add_Author.getText() + "', quantity = '"
                + add_Quantity.getText() + "', image = '"
                + path + "' WHERE document_id = '"
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

    public void manageDocument_importButton() {
        FileChooser open = new FileChooser();
        open.setTitle("Open Image File");
        open.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image File", "*png", "*jpg"));

        Stage stage = (Stage)add_form.getScene().getWindow();
        File file = open.showOpenDialog(stage);

        if (file != null) {
            image = new Image(file.toURI().toString());
            imageView_manageDocument.setImage(image);

            path = file.getAbsolutePath();
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

                String uri = "file:" + selectedDocument.getImage();
                image = new Image(uri);
                imageView_manageDocument.setImage(image);
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

    public Document findDocumentId() {
        Document document = null;
        Database connectNow = new Database();
        connect = connectNow.getConnection();
        try {
            String findID = "SELECT * FROM document WHERE document_id = '" + idDocumentIssue.getText() + "'";
            statement = connect.createStatement();
            result = statement.executeQuery(findID);
            if (result.next()) {
                int id = result.getInt("document_id");
                String title = result.getString("title");
                String author = result.getString("author");
                int quantity = result.getInt("quantity");
                String image = result.getString("image");

                document = new Document(id, title, author, quantity, image);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return document;
    }

    public Borrower findBorrowerId() {
        Borrower borrower = null;
        Database connectNow = new Database();
        connect = connectNow.getConnection();
        try {
            String findID = "SELECT * FROM borrower WHERE borrower_id = '" + idBorrowerIssue.getText() + "'";
            statement = connect.createStatement();
            result = statement.executeQuery(findID);
            if (result.next()) {
                int id = result.getInt("borrower_id");
                String name = result.getString("name");
                String phone = result.getString("phone");
                String citizenId = result.getString("citizen_id");

                borrower = new Borrower(id, name, phone, citizenId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return borrower;
    }

    public void printDetails() {
        Database connectNow = new Database();
        connect = connectNow.getConnection();

        idDocumentIssue.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.isEmpty()) {
                try {
                    Document foundDocument = findDocumentId();
                    if (foundDocument != null) {
                        idDocumentDetails.setText(String.valueOf(foundDocument.getId()));
                        titleDocumentDetails.setText(foundDocument.getTitle());
                        authorDocumentDetails.setText(foundDocument.getAuthor());
                        quantityDocumentDetails.setText(String.valueOf(foundDocument.getQuantity()));
                    } else {
                        idDocumentDetails.setText("");
                        titleDocumentDetails.setText("");
                        authorDocumentDetails.setText("");
                        quantityDocumentDetails.setText("");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                idDocumentDetails.setText("");
                titleDocumentDetails.setText("");
                authorDocumentDetails.setText("");
                quantityDocumentDetails.setText("");
            }
        });

        idBorrowerIssue.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.isEmpty()) {
                try {
                    Borrower foundBorrower = findBorrowerId();
                    if (foundBorrower != null) {
                        idBorrowerDetails.setText(String.valueOf(foundBorrower.getId()));
                        nameBorrowerDetails.setText(foundBorrower.getName());
                        phoneBorrowerDetails.setText(foundBorrower.getPhone());
                        citizenidBorrowerDetails.setText(foundBorrower.getCitizenID());
                    } else {
                        idBorrowerDetails.setText("");
                        nameBorrowerDetails.setText("");
                        phoneBorrowerDetails.setText("");
                        citizenidBorrowerDetails.setText("");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                idBorrowerDetails.setText("");
                nameBorrowerDetails.setText("");
                phoneBorrowerDetails.setText("");
                citizenidBorrowerDetails.setText("");
            }
        });
    }

    public void issueDocument() {
        String insertTransaction = "INSERT INTO borrowtransaction (document_id, borrower_id, borrow_date, due_date) VALUES (?, ?, ?, ?)";
        String checkingTransaction = "SELECT * FROM borrowtransaction WHERE document_id = '" + idDocumentIssue.getText() + "' AND borrower_id = '" + idBorrowerIssue.getText() + "' AND status = 'pending'";
        Database connectNow = new Database();
        connect = connectNow.getConnection();
        try {
            Alert alert;
            if (idDocumentIssue.getText().isEmpty() || idDocumentIssue.getText().isEmpty()
                    || issueDateField.getValue() == null || dueDateField.getValue() == null) {
                alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Missing Information");
                alert.setHeaderText("Incomplete Fields");
                alert.setContentText("Please fill in all required fields!");
                alert.showAndWait();
            } else {
                Document document = findDocumentId();
                Borrower borrower = findBorrowerId();

                if (document == null) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Invalid Document");
                    alert.setHeaderText(null);
                    alert.setContentText("The document ID is invalid. Please check again!");
                    alert.showAndWait();
                    return;
                }

                if (borrower == null) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Invalid Borrower");
                    alert.setHeaderText(null);
                    alert.setContentText("The borrower ID is invalid. Please check again!");
                    alert.showAndWait();
                    return;
                }

                if (document.getQuantity() == 0) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Out of stock");
                    alert.setHeaderText(null);
                    alert.setContentText("The document is out of stock and cannot be issued!");
                    alert.showAndWait();
                    return;
                }

                statement = connect.createStatement();
                result = statement.executeQuery(checkingTransaction);

                if (result.next()) {
                    alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Pending Borrow");
                    alert.setHeaderText(null);
                    alert.setContentText("The borrower has already borrowed this document and has not returned it yet!");
                    alert.showAndWait();
                    return;
                } else {
                    prepare = connect.prepareStatement(insertTransaction);

                    prepare.setInt(1, Integer.parseInt(idDocumentIssue.getText()));
                    prepare.setInt(2, Integer.parseInt(idBorrowerDetails.getText()));
                    prepare.setDate(3, Date.valueOf(issueDateField.getValue()));
                    prepare.setDate(4, Date.valueOf(dueDateField.getValue()));
                    prepare.executeUpdate();

                    String updateQuantity = "UPDATE document SET quantity = quantity - 1 WHERE document_id = '" + idDocumentIssue.getText() + "'";
                    statement = connect.createStatement();
                    statement.executeUpdate(updateQuantity);

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText(null);
                    alert.setContentText("The document has been successfully issued!");
                    alert.showAndWait();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<BorrowTransaction> getBorrowerTransactionList() {
        ObservableList<BorrowTransaction> transactions = FXCollections.observableArrayList();

        if (idBorrowerField_returnDocument.getText().isEmpty()) {
            return transactions;
        }

        String sql = "SELECT bt.borrowtransaction_id, d.title AS document_name, " +
                "b.name AS borrower_name, bt.borrow_date, bt.due_date, bt.status " +
                "FROM borrowtransaction bt " +
                "JOIN document d ON bt.document_id = d.document_id " +
                "JOIN borrower b ON bt.borrower_id = b.borrower_id " +
                "WHERE bt.borrower_id = '" + idBorrowerField_returnDocument.getText() + "'";
        Database connectNow = new Database();
        connect = connectNow.getConnection();

        try {
            statement = connect.createStatement();
            result = statement.executeQuery(sql);

            BorrowTransaction borrowTransaction;
            while (result.next()) {
                borrowTransaction = new BorrowTransaction(
                        result.getInt("borrowtransaction_id"),
                        result.getString("document_name"),
                        result.getString("borrower_name"),
                        result.getDate("borrow_date"),
                        result.getDate("due_date"),
                        result.getString("status")
                );
                transactions.add(borrowTransaction);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return transactions;
    }

    public void showBorrowerTransactionList() {
        borrowerTransactionList = getBorrowerTransactionList();
        transactionIdColumn_returnDocument.setCellValueFactory(new PropertyValueFactory<>("transactionId"));
        borrowerNameColumn_returnDocument.setCellValueFactory(new PropertyValueFactory<>("borrowerName"));
        documentName_returnDocument.setCellValueFactory(new PropertyValueFactory<>("documentName"));
        issueDate_returnDocument.setCellValueFactory(new PropertyValueFactory<>("issueDate"));
        dueDate_returnDocument.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        status_returnDocument.setCellValueFactory(new PropertyValueFactory<>("status"));

        idBorrowerField_returnDocument.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.isEmpty()) {
                borrowerTransactionList = getBorrowerTransactionList();
                tableView_returnDocument.setItems(borrowerTransactionList);
            }
        });

        transactionIdColumn_returnDocument.setResizable(false);
        borrowerNameColumn_returnDocument.setResizable(false);
        documentName_returnDocument.setResizable(false);
        issueDate_returnDocument.setResizable(false);
        dueDate_viewRecords.setResizable(false);
        status_returnDocument.setResizable(false);
    }

    public void returnDocumentButton () {
            BorrowTransaction transaction = tableView_returnDocument.getSelectionModel().getSelectedItem();
            if (transaction == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("No Selection");
                alert.setHeaderText("No Transaction Selected");
                alert.setContentText("Please select a transaction to return the document.");
                alert.showAndWait();
                return;
            }
            Database connectNow = new Database();
            Connection connect = connectNow.getConnection();

            if (transaction.getStatus().equalsIgnoreCase("returned")) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Invalid Operation");
                alert.setHeaderText("Transaction Already Returned");
                alert.setContentText("This transaction has already been marked as returned.");
                alert.showAndWait();
                return;
            }

             if (transaction.getDueDate().before(new java.util.Date())) {
                Alert overdueAlert = new Alert(Alert.AlertType.INFORMATION);
                overdueAlert.setTitle("Overdue Return");
                overdueAlert.setHeaderText("Document Returned Late");
                overdueAlert.setContentText("This document was returned after the due date. Please ensure late fees are handled appropriately.");
                overdueAlert.showAndWait();
             }

            try {
                String updateStatus = "UPDATE borrowtransaction SET status = 'returned', return_date = CURRENT_DATE WHERE borrowtransaction_id = '" + transaction.getTransactionId() + "'";
                String updateQuantity = "UPDATE document SET quantity = quantity + 1 WHERE document_id = " +
                                        "(SELECT document_id FROM borrowtransaction WHERE borrowtransaction_id = '" + transaction.getTransactionId() + "')";
                Statement updateStatusStmt = connect.createStatement();
                updateStatusStmt.executeUpdate(updateStatus);
                Statement updateQuantityStmt = connect.createStatement();
                updateQuantityStmt.executeUpdate(updateQuantity);
                borrowerTransactionList = getBorrowerTransactionList();
                tableView_returnDocument.setItems(borrowerTransactionList);
                tableView_returnDocument.refresh();

            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    public void deleteButton_returnDocument() {
        BorrowTransaction transaction = tableView_returnDocument.getSelectionModel().getSelectedItem();
        if (transaction == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Transaction Selected");
            alert.setContentText("Please select a transaction to delete.");
            alert.showAndWait();
            return;
        }
        Database connectNow = new Database();
        Connection connect = connectNow.getConnection();
        try {
            String deleteTransaction = "DELETE FROM borrowtransaction WHERE borrowtransaction_id = '" + transaction.getTransactionId() + "'";
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Comfirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to delete this transaction ?");
            Optional<ButtonType> option = alert.showAndWait();
            if (option.get().equals(ButtonType.OK)) {
                statement = connect.createStatement();
                statement.executeUpdate(deleteTransaction);
                borrowerTransactionList = getBorrowerTransactionList();
                tableView_returnDocument.setItems(borrowerTransactionList);
                tableView_returnDocument.refresh();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void viewRecordsSwitchForm(ActionEvent event) {
        if(event.getSource() == allRecords_button) {
            checkViewRecords = AllRecords;
            borrowTransactionList = getViewRecords();
            allRecords_button.setStyle("-fx-background-color:linear-gradient(to bottom right, #52ae8b, #9a2d3d);");
            issuedList_button.setStyle("-fx-background-color:transparent");
            defaulterList_button.setStyle("-fx-background-color:transparent");
        } else if(event.getSource() == issuedList_button) {
            checkViewRecords = IssuedList;
            borrowTransactionList = getIssuedList();
            allRecords_button.setStyle("-fx-background-color:transparent");
            issuedList_button.setStyle("-fx-background-color:linear-gradient(to bottom right, #52ae8b, #9a2d3d);");
            defaulterList_button.setStyle("-fx-background-color:transparent");
        } else if(event.getSource() == defaulterList_button) {
            checkViewRecords = DefaulterList;
            borrowTransactionList = getDefaulterList();
            allRecords_button.setStyle("-fx-background-color:transparent");
            issuedList_button.setStyle("-fx-background-color:transparent");
            defaulterList_button.setStyle("-fx-background-color:linear-gradient(to bottom right, #52ae8b, #9a2d3d);");
        }
        tableView_viewRecords.setItems(borrowTransactionList);
    }

    public ObservableList<BorrowTransaction> getViewRecords() {
        ObservableList<BorrowTransaction> transactions = FXCollections.observableArrayList();


        Database connectNow = new Database();
        connect = connectNow.getConnection();
        String sql = "SELECT bt.borrowtransaction_id, d.title AS document_name, " +
                "b.name AS borrower_name, bt.borrow_date, bt.due_date, bt.status, bt.return_date " +
                "FROM borrowtransaction bt " +
                "JOIN document d ON bt.document_id = d.document_id " +
                "JOIN borrower b ON bt.borrower_id = b.borrower_id ";
        try {
            statement = connect.createStatement();
            result = statement.executeQuery(sql);
            BorrowTransaction borrowTransaction;
            while (result.next()) {
                borrowTransaction = new BorrowTransaction(
                        result.getInt("borrowtransaction_id"),
                        result.getString("document_name"),
                        result.getString("borrower_name"),
                        result.getDate("borrow_date"),
                        result.getDate("due_date"),
                        result.getString("status"),
                        result.getDate("return_date")
                );
                transactions.add(borrowTransaction);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return transactions;
    }

    public ObservableList<BorrowTransaction> getDefaulterList() {
        ObservableList<BorrowTransaction> transactions = FXCollections.observableArrayList();


        Database connectNow = new Database();
        connect = connectNow.getConnection();
        String sql = "SELECT bt.borrowtransaction_id, d.title AS document_name, " +
                "b.name AS borrower_name, bt.borrow_date, bt.due_date, bt.status, bt.return_date " +
                "FROM borrowtransaction bt " +
                "JOIN document d ON bt.document_id = d.document_id " +
                "JOIN borrower b ON bt.borrower_id = b.borrower_id " +
                "WHERE (bt.due_date < CURRENT_DATE AND bt.status = 'pending') OR bt.return_date > bt.due_date ";
        try {
            statement = connect.createStatement();
            result = statement.executeQuery(sql);
            BorrowTransaction borrowTransaction;
            while (result.next()) {
                borrowTransaction = new BorrowTransaction(
                        result.getInt("borrowtransaction_id"),
                        result.getString("document_name"),
                        result.getString("borrower_name"),
                        result.getDate("borrow_date"),
                        result.getDate("due_date"),
                        result.getString("status"),
                        result.getDate("return_date")
                );
                transactions.add(borrowTransaction);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return transactions;
    }

    public void showBorrowTransactionList() {
        idColumn_viewRecords.setCellValueFactory(new PropertyValueFactory<>("transactionId"));
        borrowerName_viewRecords.setCellValueFactory(new PropertyValueFactory<>("borrowerName"));
        documentName_viewRecords.setCellValueFactory(new PropertyValueFactory<>("documentName"));
        issueDate_ViewRecords.setCellValueFactory(new PropertyValueFactory<>("issueDate"));
        dueDate_viewRecords.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        status_viewRecords.setCellValueFactory(new PropertyValueFactory<>("status"));
        returnDate_viewRecords.setCellValueFactory(new PropertyValueFactory<>("returnDate"));

        idColumn_viewRecords.setResizable(false);
        borrowerName_viewRecords.setResizable(false);
        documentName_viewRecords.setResizable(false);
        issueDate_ViewRecords.setResizable(false);
        dueDate_viewRecords.setResizable(false);
        status_viewRecords.setResizable(false);
        returnDate_viewRecords.setResizable(false);
    }

    public ObservableList<BorrowTransaction> getIssuedList() {
        ObservableList<BorrowTransaction> transactions = FXCollections.observableArrayList();


        Database connectNow = new Database();
        connect = connectNow.getConnection();
        String sql = "SELECT bt.borrowtransaction_id, d.title AS document_name, " +
                "b.name AS borrower_name, bt.borrow_date, bt.due_date, bt.status, bt.return_date " +
                "FROM borrowtransaction bt " +
                "JOIN document d ON bt.document_id = d.document_id " +
                "JOIN borrower b ON bt.borrower_id = b.borrower_id " +
                "WHERE status = 'pending'";
        try {
            statement = connect.createStatement();
            result = statement.executeQuery(sql);
            BorrowTransaction borrowTransaction;
            while (result.next()) {
                borrowTransaction = new BorrowTransaction(
                        result.getInt("borrowtransaction_id"),
                        result.getString("document_name"),
                        result.getString("borrower_name"),
                        result.getDate("borrow_date"),
                        result.getDate("due_date"),
                        result.getString("status"),
                        result.getDate("return_date")
                );
                transactions.add(borrowTransaction);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return transactions;
    }

    public void deleteButton_viewRecords() {
        BorrowTransaction transaction = tableView_viewRecords.getSelectionModel().getSelectedItem();
        if (transaction == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Transaction Selected");
            alert.setContentText("Please select a transaction to delete.");
            alert.showAndWait();
            return;
        }
        Database connectNow = new Database();
        Connection connect = connectNow.getConnection();

        try {
            String deleteTransaction = "DELETE FROM borrowtransaction WHERE borrowtransaction_id = '" + transaction.getTransactionId() + "'";
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Comfirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to delete this transaction ?");
            Optional<ButtonType> option = alert.showAndWait();
            if (option.get().equals(ButtonType.OK)) {
                statement = connect.createStatement();
                statement.executeUpdate(deleteTransaction);
                if(checkViewRecords == AllRecords) {
                    borrowTransactionList = getViewRecords();
                } else if(checkViewRecords == IssuedList) {
                    borrowTransactionList = getIssuedList();
                } else if(checkViewRecords == DefaulterList) {
                    borrowTransactionList = getDefaulterList();
                }
                tableView_viewRecords.setItems(borrowTransactionList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void availableDocuments_home() {
        String sql = "SELECT COUNT(document_id) FROM document";

        Database connectNow = new Database();
        connect = connectNow.getConnection();

        try {
            statement = connect.createStatement();
            result = statement.executeQuery(sql);
            int count = 0;
            while (result.next()) {
                count = result.getInt("COUNT(document_id)");
            }
            availableDocuments_home.setText(String.valueOf(count));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void issuedDocuments_home() {
        String sql = "SELECT COUNT(borrowtransaction_id) FROM borrowtransaction WHERE status != 'returned'";

        Database connectNow = new Database();
        connect = connectNow.getConnection();

        try {
            statement = connect.createStatement();
            result = statement.executeQuery(sql);
            int count = 0;
            while (result.next()) {
                count = result.getInt("COUNT(borrowtransaction_id)");
            }
            issuedDocument_home.setText(String.valueOf(count));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void totalBorrowers_home() {
        String sql = "SELECT COUNT(borrower_id) FROM borrower";

        Database connectNow = new Database();
        connect = connectNow.getConnection();

        try {
            statement = connect.createStatement();
            result = statement.executeQuery(sql);
            int count = 0;
            while (result.next()) {
                count = result.getInt("COUNT(borrower_id)");
            }
            totalBorrowers_home.setText(String.valueOf(count));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<Document> hotest;

    private List<Document> documents() {
        List<Document> documentList = new ArrayList<>();
        String sql = "SELECT d.document_id, d.title, d.author, d.quantity, d.image, COUNT(bt.document_id) AS borrow_count " +
                "FROM document d " +
                "JOIN borrowtransaction bt ON d.document_id = bt.document_id " +
                "GROUP BY d.document_id, d.title, d.author, d.quantity, d.image " +
                "ORDER BY borrow_count DESC " +
                "LIMIT 12";
        Database connectNow = new Database();
        Connection connect = connectNow.getConnection();

        try {
            Statement statement = connect.createStatement();
            ResultSet result = statement.executeQuery(sql);

            while (result.next()) {
                Document document = new Document(
                        result.getInt("document_id"),
                        result.getString("title"),
                        result.getString("author"),
                        result.getInt("quantity"),
                        result.getString("image")
                );
                documentList.add(document);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return documentList;
    }

    public void hottest_home() {
        hotest = documents();
        int column = 0;
        int row = 1;

        try {
            for (Document document : hotest) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("document.fxml"));
                VBox documentBox = fxmlLoader.load();
                DocumentController documentController = fxmlLoader.getController();
                documentController.setData(document);
                if(column == 4) {
                    column = 0;
                    ++row;
                }

                documentContainer.add(documentBox, column++, row);
                GridPane.setMargin(documentBox, new Insets(7,15,20,25));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize (URL Location, ResourceBundle resources) {
        availableDocuments_home();
        issuedDocuments_home();
        totalBorrowers_home();
        showListDocument();
        showListBorrower();
        showBorrowerTransactionList();
        showBorrowTransactionList();
        addDocumentSelected();
        borrowersSelected();
        search();
        borrowers_search();
        printDetails();
        hottest_home();
    }

}
