package com.example.librarymanagementsystemapp;


import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.locks.StampedLock;

public class DocumentDetails2Controller {

    @FXML
    private Label documentAuthor;

    @FXML
    private Label documentGenre;

    @FXML
    private ImageView documentImage;

    @FXML
    private Label documentTitle;

    @FXML
    private TextField borrowDate;

    @FXML
    private DatePicker dueDate;

    @FXML
    private Button borrowButton;

    public void setDocument(Document document, Stage stage) {
        documentTitle.setText(document.getTitle());
        documentAuthor.setText("Author: " + document.getAuthor());
        documentGenre.setText("Genre: " + document.getGenre());
        if (document.getImage() != null) {
            documentImage.setImage(new Image(document.getImage()));
        }

        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = today.format(formatter);
        borrowDate.setText(formattedDate);
        borrowButton.setOnAction(event-> setBorrowButton(document, stage));
    }
    public void addTransactionToDatabase(Document document, Stage stage) {
        int userId = UserSceneController.getInstance().getUserId();

        String insertTransaction = "INSERT INTO borrowtransaction (document_id, account_id, borrow_date, due_date) VALUES (?, ?, ?, ?)";
        String checkingTransaction = "SELECT * FROM borrowtransaction WHERE document_id = '" + document.getId() + "' AND account_id = '" + userId + "' AND status = 'borrowing'";
        String checkingTransaction2 = "SELECT * FROM borrowtransaction WHERE document_id = '" + document.getId() + "' AND account_id = '" + userId + "' AND status = 'pending'";

        Database connectNow = new Database();
        Connection connect = connectNow.getConnection();

        try {
            LocalDate dueDate1 = dueDate.getValue();
            if (dueDate1 == null) {
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Missing Due Date");
                    alert.setHeaderText("Due Date is Missing");
                    alert.setContentText("Please enter a due date to continue.");
                    alert.showAndWait();
                });
                return;
            } else {
                LocalDate currentDate = LocalDate.now();
                if (dueDate1.isAfter(currentDate.plusDays(7))) {
                    Platform.runLater(() -> {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Invalid Due Date");
                        alert.setHeaderText("Due Date is too far ahead");
                        alert.setContentText("The due date cannot be more than 7 days from today.");
                        alert.showAndWait();
                    });
                    return;
                }
                if (document.getQuantity() == 0) {
                    Platform.runLater(() -> {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Out of stock");
                        alert.setHeaderText(null);
                        alert.setContentText("The document is out of stock and cannot be borrowed!");
                        alert.showAndWait();
                    });
                    return;
                }

                Statement statement = connect.createStatement();
                ResultSet resultSet = statement.executeQuery(checkingTransaction);

                if (resultSet.next()) {
                    Platform.runLater(() -> {
                        Alert alert = new Alert(Alert.AlertType.WARNING,
                                "You have already borrowed this book and have not returned it. You cannot borrow it again."
                        );
                        alert.setTitle("Cannot Borrow Book");
                        alert.setHeaderText("Borrowing Not Allowed");
                        alert.showAndWait();
                    });
                    return;
                }
                Statement statement2 = connect.createStatement();
                ResultSet resultSet2 = statement2.executeQuery(checkingTransaction2);
                if(resultSet2.next()) {
                    Platform.runLater(() -> {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Approval Pending");
                        alert.setHeaderText("Book Request is Pending Approval");
                        alert.setContentText("Your request to borrow the book is currently pending manager approval.");
                        alert.showAndWait();
                        });
                    return;
                } else {
                    PreparedStatement prepare = connect.prepareStatement(insertTransaction);

                    prepare.setInt(1, document.getId());
                    prepare.setInt(2, UserSceneController.getInstance().getUserId());
                    prepare.setDate(3, Date.valueOf(LocalDate.now()));
                    prepare.setDate(4, Date.valueOf(dueDate.getValue()));
                    prepare.executeUpdate();

                    String updateQuantity = "UPDATE document SET quantity = quantity - 1 WHERE document_id = '" + document.getId() + "'";
                    statement = connect.createStatement();
                    statement.executeUpdate(updateQuantity);

                    Platform.runLater(() -> {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION); // Loại thông báo: Thông tin
                        alert.setTitle("Approval Pending");
                        alert.setHeaderText("Request Submitted");
                        alert.setContentText("Your request for borrowing the book has been submitted. It will be reviewed by the manager for approval.");
                        alert.showAndWait();
                    });
                }
            }
        } catch (Exception e) {
             e.printStackTrace();
            }
    }

    private void setBorrowButton(Document document, Stage stage) {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                addTransactionToDatabase(document, stage);
                return null;
            }
        };
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

}