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
public class TicketCollectionUtils {
    private static DB db;
    private static final String collectionName = "ticket";
    private static DBCollection collection;
    
    private static TicketCollectionUtils singleton = new TicketCollectionUtils();
    private TicketCollectionUtils(){
        db = DBUtils.getInstance().getDb();
        collection = db.getCollection(collectionName);
    }
    
    public static TicketCollectionUtils getInstance(){
        return singleton;
    }
    
    public DBCollection getCollection(){
        return collection;
    }   
}
