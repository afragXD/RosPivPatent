package com.example.rospivpatent;

public class SearchClass {
    //название(title) +
    private String title;
    //МПК + fullname +
    private String fullname;
    //kind + publication_date +
    private String date;
    //Документ publishing_office + document_number
    private String document;
    //описание +
    private String description;

    public SearchClass(){
        title = "";
        fullname = "";
        date = "";
        document = "";
        description = "";
    }

    public SearchClass(String title, String fullname, String date, String document, String description) {
        this.title = title;
        this.fullname = fullname;
        this.date = date;
        this.document = document;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

