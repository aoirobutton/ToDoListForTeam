/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.todo;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author matsushita
 */
public class addMongoTest {

    private MongoClient client;
    private DB db;
    private DBCollection coll;

    public addMongoTest() {
        try {
            client = new MongoClient("localhost", 27017);
            db = client.getDB("addMongo");
            if(db.collectionExists("testcoll")){
                db.createCollection("testColl", new BasicDBObject());                
            }
            coll = db.getCollection("testcoll");
        } catch (UnknownHostException ex) {
            Logger.getLogger(addMongoTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String func(boolean dropDbFlg) {
        BasicDBObject doc = new BasicDBObject("id", "123")
                .append("name", "zyuzo");
        coll.insert(doc);
        doc = new BasicDBObject("id", "345")
                .append("name", "hihumi");
        coll.insert(doc);
        BasicDBObject query = new BasicDBObject("id", "345");
        DBCursor cursor = coll.find(query);
        
        String ret = cursor.next().toString();
        if(dropDbFlg)
            db.dropDatabase();
        return ret;
    }
}
