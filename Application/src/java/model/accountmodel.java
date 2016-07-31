/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoException;
import java.util.ArrayList;
import java.util.List;

import util.DBUtils;
import view.FatalException;
import view.AccountAlreadyRegisteredException;
/**
 *
 * @author aoiro_makoto
 */
public class accountmodel {

    private static final String DB_ACCOUNT_COLLECTION = "account";
    private DB db;
    private DBCollection coll;

    public accountmodel() {
        db = DBUtils.getInstance().getDb();
        coll = db.getCollection(DB_ACCOUNT_COLLECTION);
    }

    public Account authenticate(String userId, String password) {
        DBObject query = new BasicDBObject();
        query.put("userId", userId);
        query.put("pass", password);

        DBObject result = coll.findOne(query);

        if (result == null) {
            String message = String.format(
                    "User { userId: %s, pass: %s } not exist.",
                    userId, password
            );

        }
        Account account = new Account();
        DBUtils.attachProperties(account, result);
        return account;

    }

    /**
     * 新規アカウント情報をデータベースに登録する.
     * <ol>
     * <li>userId, pass, roleからアカウント情報（{@link Account
     * <code>Account</code>}オブジェクト）を構築する．</li>
     * <li>与えられた userId を持つアカウントがあるかを{@link #isUniqueName
     * <code>isUniqueName</code>} メソッドを利用して調査する．
     * <ol>
     * <li>userIdの重複がある場合、{@link AccountAlreadyRegisteredException
     *         <code>AccountAlreadyRegisteredException</code>}を投げる</li>
     * </ol>
     * </li>
     * <li>アカウント情報をデータベースに登録する．</li>
     *     {@code db.account.insert({ "userId" : userId, "pass" : pass, "role" : role })}
     * <li>アカウント情報を返す．</li>
     * <li><code>MongoException</code>が発生した場合：
     * <ol>
     * <li>発生した例外を{@link TEMFatalException
     * <code>TEMFatalException</code>}にラップして投げる．</li>
     * </ol>
     * </li>
     * </ol>
     *
     * @param user	ユーザ名
     * @param pass	パスワード
     * @param project	プロジェクト
     * @return 登録されたアカウント情報．
     * @throws TEMFatalException MongoExceptionが発生した場合．
     * @throws AccountAlreadyRegisteredException すでにアカウントが登録されている場合．
     */
    public Account registerAccount(String user, String pass)
            throws FatalException, AccountAlreadyRegisteredException {
        Account account = new Account();
        account.setUser(user);
        account.setPass(pass);
        List<String> project = new ArrayList<String>();
        account.setProject(project);

        // 重複をチェックする．
        if (!isUniqueName(user)) {
            String message = String.format("Account: %s is already registered", user);
            throw new AccountAlreadyRegisteredException(message);
        }
        // 指定パラメータから MongoDB 格納用オブジェクトを生成する．
        DBObject object = new BasicDBObject();
        DBUtils.convertToDBObject(object, account);

        try {
            coll.insert(object);
            return account;
        } catch (MongoException e) {
            throw new FatalException(e);
        }
    }
    
    

    /**
     * ユーザ名の重複チェック
     * <code>db.account.findOne({ "userId" : userId })</code>の結果を利用して，
     * 存在するかしないかをチェックする
     * @param user ユーザ名
     * @return 与えられた userId が既に登録されていれば false，存在していなければ true．
     */
    private boolean isUniqueName(String user) throws FatalException{
        DBObject query = new BasicDBObject();
        query.put("user", user);

        try{
            if(coll.findOne(query) != null){
                return false;
            }
        } catch(MongoException e){
            throw new FatalException(e);
        }
        return true;
    }
    

}
