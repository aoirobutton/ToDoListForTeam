/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;


/**
 *
 * @author aoiro_makoto
 */
public class DBUtils {
    
        private static Mongo m;
    private static DB db;
    private static final String dbName = "tem";

    private static DBUtils singleton = new DBUtils();

    /**
     * シングルトンオブジェクトの取得．
     * @return シングルトンオブジェクト
     */
    public static DBUtils getInstance() {
        return singleton;
    }

    // invisible singleton constructor
    private DBUtils() {
            m = new Mongo("localhost", 27017);
            db = m.getDB(dbName);
    }

    
}
