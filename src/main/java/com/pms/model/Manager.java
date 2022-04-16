package com.pms.model;

import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.InsertOneResult;
import org.bson.Document;

public class Manager {
    private long ManagerID;
    private String FirstName;
    private String LastName;
    private int DepartmentID;

    public void addStudent(long StudentID, String FirstName, String LastName,long ManagerID,int DepartmentID){
        String URI = "mongodb://localhost:27017";
        try(MongoClient mongoclient = MongoClients.create(URI)){
            MongoDatabase database = mongoclient.getDatabase("PayrollManagementSystem");
            MongoCollection<Document> collection = database.getCollection("Employee");
            InsertOneResult result = collection.insertOne(new Document()
                    .append("_id",StudentID)
                    .append("ManagerID",ManagerID)
                    .append("FirstName",FirstName)
                    .append("LastName",LastName)
                    .append("DepartmentID",DepartmentID)
            );
        }
        catch(MongoException me){
            System.err.println("Error: " + me);
        }
    }
}
