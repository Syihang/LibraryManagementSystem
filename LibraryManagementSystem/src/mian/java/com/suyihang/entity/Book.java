package com.suyihang.entity;

import com.suyihang.database.DataSelectBook;

import java.sql.Date;

public class Book {
    private String ISBN;           // ISBN编号
    private String title;          // 书名
    private String author;         // 作者
    private String publisher;      // 出版社
    private Date publicationDate;  // 出版日期
    private int total;             // 总量
    private Double price;          // 价格
    private int typeId;            // 类型ID
    private String typeName;       // 书籍类型
    private int repertory;         // 库存
    private String Type;

    // Getters and Setters

    public Book(String ISBN, String title, String author, String publisher, Date publicationDate, int total, Double price, int typeId) {
        this.ISBN = ISBN;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.publicationDate = publicationDate;
        this.total = total;
        this.price = price;
        this.typeId = typeId;
        this.typeName = DataSelectBook.findBookTypeByID(typeId);
        this.repertory = total - DataSelectBook.getNumberofBooksLent(ISBN);
        this.Type = DataSelectBook.findBookTypeByID(typeId);
    }

    public Book() {}

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    @Override
    public String toString() {
        return "Book{" +
                "ISBN='" + ISBN + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", publisher='" + publisher + '\'' +
                ", publicationDate=" + publicationDate +
                ", total=" + total +
                ", price=" + price +
                ", typeId=" + typeId +
                '}';
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getRepertory() {
        return repertory;
    }

    public void setRepertory(int repertory) {
        this.repertory = repertory;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }
}
