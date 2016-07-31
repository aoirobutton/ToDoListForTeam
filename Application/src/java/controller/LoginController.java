/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import view.IdentifyingAccountForm;
import view.TEMViewException;
import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import model.Account;
import util.DBUtils;
import util.LoginUserUtils;

/**
 *
 * @author tomo
 */
public class LoginController extends IdentifyingAccountForm{
    
    private  Account account;
    
    public LoginController(){        
    }
    public boolean execute(String form) throws TEMViewException {
    //    form.validate();
    //    account = form;
        // ログインしようとしているユーザの情報を保持
         Gson gson = new Gson();
         account = gson.fromJson(form, Account.class);
  //      account.setUser(form.getUserId());
  //      account.setPass(form.getPass());
  
  //    Session session = new Session();
        // 現在のデータベースを取得
        DB db = DBUtils.getInstance().getDb();
        // collention の指定
        DBCollection coll = db.getCollection("account");
        // 検索用クエリーの生成
        BasicDBObject query = new BasicDBObject("user", account.getUser())
                                       .append("pass", account.getPass());
        // 検索
        DBCursor cursor = coll.find(query);
        if(cursor.size() == 1){
            account = gson.fromJson(cursor.next().toString(),Account.class);
            // 登録されているユーザ名とパスが入力されたとき
            // ログインユーザの情報を格納
            LoginUserUtils.setLoginUser(account);
            return true;
        } else {
            return false;
        }
    }
    
     public static String getSessionId() {
        WebContext ctx = WebContextFactory.get();

        // ローカルテスト時
        if (ctx == null) {
            return "THIS_IS_A_TEST_SESSION_ID";
        }

        return ctx.getScriptSession().getId().split("/")[0];
    }
     
     public Account getLoginUser(){
         return LoginUserUtils.getInstance().getLoginUser();
     }
    
}
