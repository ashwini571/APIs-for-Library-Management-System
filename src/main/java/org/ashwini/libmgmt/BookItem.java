package org.ashwini.libmgmt;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIgnore;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "Books")
public class BookItem {
    private String name;
    private String pub;
    private String author;


    @DynamoDBHashKey(attributeName="name")
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @DynamoDBIndexHashKey(globalSecondaryIndexName = "authorIndex",attributeName="author")
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }

    @DynamoDBIndexHashKey(globalSecondaryIndexName="pubIndex" ,attributeName="pub")
    public String getPub() {
        return pub;
    }
    public void setPub(String pub) {
        this.pub = pub;
    }
}
