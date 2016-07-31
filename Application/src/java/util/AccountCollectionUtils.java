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
import java.util.List;
import model.Account;

/**
 *
 * @author matsushita
 */
public class AccountCollectionUtils {
    private static DB db;
    private static final String collectionName = "account";
    private static DBCollection collection;
    Gson gson = new Gson();
    
    private static AccountCollectionUtils singleton = new AccountCollectionUtils();
    private AccountCollectionUtils(){
        db = DBUtils.getInstance().getDb();
        collection = db.getCollection(collectionName);
    }
    
    public static AccountCollectionUtils getInstance(){
        return singleton;
    }
    
    public DBCollection getCollection(){
        return this.collection;
    }
    
    public boolean existUser(String user){
        BasicDBObject query = new BasicDBObject("user",user);
        DBCursor cursor = collection.find(query);
        return cursor.size() == 1;
    }
    
    public boolean existUsers(List<String> users) throws Exception{
        for(int i = 0; i<users.size(); i++){
            if( ! this.existUser(users.get(i))){                
                throw new Exception(String.format("ユーザ\"%s\"は登録されていません.", users.get(i)));
            }
        }
        return true;
    }
    
    public Account getAccount(String userName){
       DBCursor cursor = collection.find(new BasicDBObject("user",userName));
       if(cursor.size()==1){
           return gson.fromJson(cursor.next().toString(), Account.class);
       }else{
           //throw new Exception("そのアカウントはないよ.");
           return null;
       }
    }
    
    public void addProject(String userName, String projectName){
        BasicDBObject search = new BasicDBObject("user",userName);
        BasicDBObject query  = new BasicDBObject("$push",new BasicDBObject("project", projectName));
        collection.update(search, query);
    }
}
