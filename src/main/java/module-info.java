module com.example.librarymanagementsystemapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.example.librarymanagementsystemapp to javafx.fxml;
    exports com.example.librarymanagementsystemapp;
}