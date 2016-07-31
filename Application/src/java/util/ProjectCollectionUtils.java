/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import model.Account;
import model.Project;

/**
 *
 * @author matsushita
 */
public class ProjectCollectionUtils {
    private static DB db;
    private static final String collectionName = "project";
    private static DBCollection collection;
    Gson gson = new Gson();
    
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
        
    //LoginUserの持つプロジェクトを取得
    public Project getProject(String projectName) {
        BasicDBObject query = new BasicDBObject("project",projectName)
                .append("member", LoginUserUtils.getInstance().getLoginUser().getUser());
        DBCursor cursor = collection.find(query);
        if(cursor.size() == 1){
            return gson.fromJson(cursor.next().toString(), Project.class);
        }
        return null;
    }
    
    public void addMember(String projectName, String newMember){
        BasicDBObject search = new BasicDBObject("project",projectName).append("member", LoginUserUtils.getInstance().getLoginUser().getUser());
        BasicDBObject query  = new BasicDBObject("$push",new BasicDBObject("member", newMember));
        collection.update(search, query);
    }
   
}
