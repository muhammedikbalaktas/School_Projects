package com.example.librarymanagement.classes;

public class Book {
    private String name;
    private String kind;
    private String author;
    private String pageCount;
    private String publishDate;

    public Book(String name, String kind, String author, String pageCount, String publishDate) {
        this.name = name;
        this.kind = kind;
        this.author = author;
        this.pageCount = pageCount;
        this.publishDate = publishDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPageCount() {
        return pageCount;
    }

    public void setPageCount(String pageCount) {
        this.pageCount = pageCount;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                ", kind='" + kind + '\'' +
                ", author='" + author + '\'' +
                ", pageCount='" + pageCount + '\'' +
                ", publishDate='" + publishDate + '\'' +
                '}';
    }
}
