package com.example.librarymanagementsystemapp;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class DocumentController {

    @FXML
    private Label documentAuthor;

    @FXML
    private ImageView documentImage;

    @FXML
    private Label documentName;

    public void setData (Document document) {
        String uri = "file:" + document.getImage();
        Image image = new Image(uri);
        documentImage.setImage(image);
        documentName.setText(document.getTitle());
        documentAuthor.setText(document.getAuthor());

    }
}
