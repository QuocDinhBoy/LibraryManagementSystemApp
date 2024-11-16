package com.example.librarymanagementsystemapp;

public class Document {
        private String id;
        private String name;
        private String author;
        private int quantity;

        public Document(String id, String name, String author, int quantity) {
            this.id = id;
            this.name = name;
            this.author = author;
            this.quantity = quantity;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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
