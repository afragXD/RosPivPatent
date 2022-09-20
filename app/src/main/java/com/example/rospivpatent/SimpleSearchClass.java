package com.example.rospivpatent;

public class SimpleSearchClass {
    private String SearchText;
    private String DocNumber;
    private String PublicationDateFrom;
    private String PublicationDateBefore;
    private String CopyrightHolder;
    private String author;
    private String applicant;
    private String SearchArrays;

    public SimpleSearchClass(){
        SearchText = "";
        DocNumber = "";
        PublicationDateFrom = "";
        PublicationDateBefore = "";
        CopyrightHolder = "";
        author = "";
        applicant = "";
        SearchArrays = "";
    }

    public SimpleSearchClass (String SearchText,String DocNumber,String PublicationDateFrom,String PublicationDateBefore,String CopyrightHolder,String author,String applicant,String SearchArrays)
    {
        this.SearchText=SearchText;
        this.DocNumber=DocNumber;
        this.PublicationDateFrom=PublicationDateFrom;
        this.PublicationDateBefore=PublicationDateBefore;
        this.CopyrightHolder=CopyrightHolder;
        this.author=author;
        this.applicant=applicant;
        this.SearchArrays=SearchArrays;

    }

    public String getDocNumber() {
        return DocNumber;
    }

    public void setDocNumber(String docNumber) {
        DocNumber = docNumber;
    }

    public String getSearchText() {
        return SearchText;
    }

    public void setSearchText(String searchText) {
        SearchText = searchText;
    }

    public String getPublicationDateFrom() {
        return PublicationDateFrom;
    }

    public void setPublicationDateFrom(String publicationDateFrom) {
        PublicationDateFrom = publicationDateFrom;
    }

    public String getPublicationDateBefore() {
        return PublicationDateBefore;
    }

    public void setPublicationDateBefore(String publicationDateBefore) {
        PublicationDateBefore = publicationDateBefore;
    }

    public String getCopyrightHolder() {
        return CopyrightHolder;
    }

    public void setCopyrightHolder(String copyrightHolder) {
        CopyrightHolder = copyrightHolder;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getApplicant() {
        return applicant;
    }

    public void setApplicant(String applicant) {
        this.applicant = applicant;
    }

    public String getSearchArrays() {
        return SearchArrays;
    }

    public void setSearchArrays(String searchArrays) {
        SearchArrays = searchArrays;
    }
}

