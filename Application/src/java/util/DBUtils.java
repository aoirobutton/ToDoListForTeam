/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import com.mongodb.DB;
import com.mongodb.Mongo;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author aoiro_makoto
 */
public class DBUtils {
    
    private static Mongo m;
    private static DB db;
    private static final String dbName = "Todo";

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

    
    
}
