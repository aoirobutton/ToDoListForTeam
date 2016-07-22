/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import com.mongodb.DB;
import com.mongodb.DBCollection;

/**
 *
 * @author matsushita
 */
public class ProjectCollectionUtils {
    private static DB db;
    private static final String collectionName = "project";
    private static DBCollection collection;
    
    private static ProjectCollectionUtils singleton = new ProjectCollectionUtils();
    private ProjectCollectionUtils(){
        db = DBUtils.getInstance().getDb();
        collection = db.getCollection(collectionName);
    }
    
    public static ProjectCollectionUtils getInstance(){
        return singleton;
    }
    
    public DBCollection getCollection(){
        return collection;
    }   
   
}
