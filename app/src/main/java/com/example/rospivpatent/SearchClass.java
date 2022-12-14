package com.example.rospivpatent;

public class SearchClass {
    //название(title) +
    private String title;
    //МПК + fullname +
    private String fullname;
    //date = kind + publication_date +
    private String kind;
    private String publication_date;
    //Документ = publishing_office + document_number
    private String publishing_office;
    private String document_number;
    //описание +
    private String description;
    //id
    private String id;
    private String inventor;

    private String referat;

    public SearchClass(){
        title = "";
        fullname = "";
        kind = "";
        publication_date = "";
        publishing_office = "";
        document_number = "";
        description = "";
        id = "";
        inventor = "";
        referat = "";
    }

    public SearchClass(String title, String fullname, String kind, String publication_date, String publishing_office, String document_number, String description, String id, String inventor, String referat) {
        this.title = title;
        this.fullname = fullname;
        this.kind = kind;
        this.publication_date = publication_date;
        this.publishing_office = publishing_office;
        this.document_number = document_number;
        this.description = description;
        this.id = id;
        this.inventor = inventor;
        this.referat = referat;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getPublication_date() {
        return publication_date;
    }

    public void setPublication_date(String publication_date) {
        this.publication_date = publication_date;
    }

    public String getPublishing_office() {
        return publishing_office;
    }

    public void setPublishing_office(String publishing_office) {
        this.publishing_office = publishing_office;
    }

    public String getDocument_number() {
        return document_number;
    }

    public void setDocument_number(String document_number) {
        this.document_number = document_number;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInventor() {
        return inventor;
    }

    public void setInventor(String inventor) {
        this.inventor = inventor;
    }

    public String getReferat() {
        return referat;
    }

    public void setReferat(String referat) {
        this.referat = referat;
    }
}

