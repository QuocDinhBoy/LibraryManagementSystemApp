package com.example.librarymanagementsystemapp;

import java.sql.Date;

public class BorrowTransaction {
    private int transactionId;
    private String documentName;
    private String borrowerName;
    private Date issueDate;
    private Date dueDate;
    private String status;
    private Date returnDate;

    public BorrowTransaction(int transactionId, String documentName, String borrowerName, Date issueDate, Date dueDate, String status) {
        this.transactionId = transactionId;
        this.documentName = documentName;
        this.borrowerName = borrowerName;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
        this.status = status;
        this.returnDate = null;
    }

    public BorrowTransaction(int transactionId, String documentName, String borrowerName, Date issueDate, Date dueDate, String status, Date returnDate) {
        this.transactionId = transactionId;
        this.documentName = documentName;
        this.borrowerName = borrowerName;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
        this.status = status;
        this.returnDate = returnDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public String getBorrowerName() {
        return borrowerName;
    }

    public void setBorrowerName(String borrowerName) {
        this.borrowerName = borrowerName;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
