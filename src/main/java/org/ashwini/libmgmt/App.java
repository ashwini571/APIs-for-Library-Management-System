package org.ashwini.libmgmt;

import java.awt.print.Book;
import java.nio.MappedByteBuffer;
import java.util.*;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Index;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.*;
import com.amazonaws.services.dynamodbv2.xspec.L;

public class App {


//    //DynaomDb Local
    private static AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
            .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "us-east-1"))
            .build();

    //DynamoDb Web
//    private static AmazonDynamoDB client = AmazonDynamoDBClient.builder()
//            .withRegion(Regions.US_EAST_1).build();


    private static DynamoDB dynamoDB = new DynamoDB(client);
    private static String tableName = "Books";

    private static DynamoDBMapper mapper = new DynamoDBMapper(client);

    public static void createTable() {

        // Attribute definitions
        ArrayList<AttributeDefinition> attributeDefinitions = new ArrayList<AttributeDefinition>();

        attributeDefinitions.add(new AttributeDefinition().withAttributeName("title").withAttributeType("S"));
        attributeDefinitions.add(new AttributeDefinition().withAttributeName("author").withAttributeType("S"));

        // Key schema for table
        ArrayList<KeySchemaElement> tableKeySchema = new ArrayList<KeySchemaElement>();
        tableKeySchema.add(new KeySchemaElement().withAttributeName("title").withKeyType(KeyType.HASH));//Partition Key
        tableKeySchema.add(new KeySchemaElement().withAttributeName("author").withKeyType(KeyType.RANGE));//Sort Key

        // Initial provisioned throughput settings for the indexes
        ProvisionedThroughput ptIndex = new ProvisionedThroughput().withReadCapacityUnits(1L)
                .withWriteCapacityUnits(1L);


        CreateTableRequest createTableRequest = new CreateTableRequest().withTableName(tableName)
                .withProvisionedThroughput(
                        new ProvisionedThroughput().withReadCapacityUnits((long) 1).withWriteCapacityUnits((long) 1))
                .withKeySchema(tableKeySchema)
                .withAttributeDefinitions(attributeDefinitions);

        System.out.println("Creating table " + tableName + "...");
        dynamoDB.createTable(createTableRequest);

        // Wait for table to become active
        System.out.println("Waiting for " + tableName + " to become ACTIVE...");
        try {
            Table table = dynamoDB.getTable(tableName);
            table.waitForActive();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static void deleteTable(String tableName) {

        System.out.println("Deleting table " + tableName + "...");

        Table table = dynamoDB.getTable(tableName);
        table.delete();

        // Wait for table to be deleted
        System.out.println("Waiting for " + tableName + " to be deleted...");
        try {
            table.waitForDelete();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void putBook(BookItem book) {
        mapper.save(book);
    }
    public static BookItem getBook(BookItem book) {
       return mapper.load(BookItem.class,book.getTitle(),book.getAuthor());
    }
    public static void delBook(BookItem book) {
        mapper.delete(mapper.load(BookItem.class,book.getTitle(),book.getAuthor()));
    }

    public static void main( String[] args )
    {
         createTable();
//        BookItem book = new BookItem();
//        book.setPub("jainPub");
//        book.setAuthor("ashwini");
//        book.setTitle("Maths");
//        mapper.save(book);

    }
}
