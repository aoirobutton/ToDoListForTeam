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
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.beanutils.PropertyUtils;


/**
 *
 * @author aoiro_makoto
 */
public class DBUtils {
    
    private static Mongo m;
    private static DB db;
    private static final String dbName = "ToDo";

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
            try {
                m = new Mongo("localhost", 27017);
                db = m.getDB(dbName);
            } catch (UnknownHostException ex) {
                Logger.getLogger(DBUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

    public DB getDb() {
        return db;
    }

    
        @SuppressWarnings("unchecked")
    public static void attachProperties(Object dest, DBObject from) {
        //		System.out.println("========================="+dest.getClass() + " | " + from.getClass());
        Field[] fields = dest.getClass().getDeclaredFields();
        for (Field field : fields) {
            // 変換対象のオブジェクトプロパティを取得
            //Object property = PropertyUtils.getProperty(dest, field.getName());
            Object property = from.get(field.getName());

            // ネストされたオブジェクトを探す
            if (property != null) {
                //				System.out.println("  " + property.getClass() + " " + field.getName());

                // 継承先を全て探索
                for (Class<?> c : property.getClass().getInterfaces()) {
                    //					System.out.println("     " + c);
                    // List を発見した場合  TODO:他のコレクションクラス
                    if (c == com.mongodb.DBObject.class || c == java.util.List.class) {
                        try {
                            // genericsのClassオブジェクトを取得
                            ParameterizedType ptype
                                    = (ParameterizedType) dest.getClass().getDeclaredField(field.getName()).getGenericType();
                            Class<?> cls = (Class<?>) ptype.getActualTypeArguments()[0];

                            List<Object> list = new ArrayList<Object>();
                            // 全てのリストを変換
                            for (DBObject dbo : (List<DBObject>) from.get(field.getName())) {
                                Object o = cls.newInstance();
                                attachProperties(o, dbo);
                                list.add(o);
                            }

                            property = list;
                            // 他の継承クラスは無視
                            break;
                        } catch (IllegalAccessException | InstantiationException |
                                NoSuchFieldException | SecurityException e) {
                            continue;
                        }
                    }
                }
            }
            try {
                PropertyUtils.setProperty(dest, field.getName(), property);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                continue; //TODO
            }
        }
    }

    /**
     * <p>
     * HTML特殊文字のサニタイズを行う．
     * ユーザが入力した文字列のうち，セキュリティ上危険な特殊タグ文字を全て置換する．
     * 基本的に String に対する setter の全てで呼び出すこと．<br>
     * ※ユーザが入力した & や <> などの特殊文字をそのまま利用すると，
     * DBへの命令を直接埋め込まれる危険性がある（XSS，SQLインジェクション）．
     * </p><p>
     * 利用例：
     * </p>
     * <pre>
     *   public void setMessage(String message) {
     *     this.message = DBUtils.getInstance().sanitize(message);
     *
     *     // 直接代入しないこと
     *     // this.message = message;
     *   }
     * </pre>
     *
     * @param str サニタイズ対象の文字列
     * @return サニタイズ済み文字列
     */
    public static String sanitize(String str) {
        if (str == null) {
            return "";
        }
        str = str.replaceAll("&" , "&amp;" );
        str = str.replaceAll("<" , "&lt;"  );
        str = str.replaceAll(">" , "&gt;"  );
        str = str.replaceAll("\"", "&quot;");
        str = str.replaceAll("'" , "&#39;" );
        return str;
    }

    
    /**
     * <p>
     * Object → DBObjectの変換を行う．
     * 作成したObjectのプロパティを，MongoDBのDBObjectに移し替える．
     * </p><p>
     * 利用手順は以下の通り．
     * </p>
     * <ol>
     *   <li>変換したいObjectを取得する．</li>
     *   <li>DBObject（空）を生成する．</li>
     *   <li>本メソッドを呼び出す．</li>
     * </ol>
     *
     * <p>利用例：</p>
     * <pre>
     *   Event event = getEvent(query);
     *   DBObject object = new BasicDBObject();
     *   DBUtils.convertToDBObject(object, event);
     * </pre>
     *
     * @param dest 変換先のDBObject
     * @param from 変換元のObject
     */
    @SuppressWarnings("unchecked")
    public static void convertToDBObject(DBObject dest, Object from){
        try {
            // from側の属性から探索する
            Field[] fields = from.getClass().getDeclaredFields();
            for (Field field : fields) {
                //TODO カバレッジツールが"$jaccoData"というフィールドを
                //カバレッジ計測のためにクラスに埋め込むので，回避するために実装を修正した．挙動的に問題がないか要確認
                if(field.getName().equals("$jacocoData")) continue;
                // destの同名オブジェクトプロパティを取得
                Object property = PropertyUtils.getProperty(from, field.getName());

                // ネストされたオブジェクトを探す
                if (property != null) {
                    // 継承先を全て探索
                    for (Class<?> c : property.getClass().getInterfaces()) {
                        // List を発見した場合
                        // TODO Map等の他のコレクションはどうするか
                        if (c == java.util.List.class) {
                            List<DBObject> list = new ArrayList<DBObject>();
                            for (Object p : (List<Object>)property) {
                                // 再帰呼び出しで変換
                                DBObject o = new BasicDBObject();
                                convertToDBObject(o, p);
                                list.add(o);
                            }
                            property = list;

                            // 他の継承クラスは無視
                            break;
                        }
                    }
                }
                dest.put(field.getName(), property);
            }
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            Logger logger = Logger.getLogger(DBUtils.class.getName());
            logger.warning("DBUtils.convertToDBObject: " + e.getMessage());
            //System.out.println("DBUtils.convertToDBObject: " + e.getMessage());

            // RuntimeExceptionをスロー
            throw new IllegalArgumentException();
        }
    }
    
    
    
}
