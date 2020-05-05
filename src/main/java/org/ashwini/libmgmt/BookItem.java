package org.ashwini.libmgmt;

import com.amazonaws.services.dynamodbv2.datamodeling.*;

@DynamoDBTable(tableName = "Books")
public class BookItem {
    private String title;
    private String pub;
    private String author;


    @DynamoDBHashKey(attributeName="title")
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    @DynamoDBRangeKey(attributeName = "author")
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }


    public String getPub() {
        return pub;
    }
    public void setPub(String pub) {
        this.pub = pub;
    }
}
