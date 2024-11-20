package com.example.librarymanagementsystemapp;

public class Document {
        private int id;
        private String title;
        private String author;
        private int quantity;

        public Document(int id, String title, String author, int quantity) {
            this.id = id;
            this.title = title;
            this.author = author;
            this.quantity = quantity;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String name) {
            this.title = name;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
}
