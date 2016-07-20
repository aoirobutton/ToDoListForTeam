/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author aoiro_makoto
 */
public class Account {

    /**
     * ユーザID
     */
    private int id;
    /**
     * セッションID
     */
    private String sessionId = "";
    /**
     * ユーザ名
     */    
    private String user;
    /**
     * パスワード
     */
    private String pass;
    /**
     * プロジェクト
     */
    private List<Integer> project = new ArrayList<Integer>();

    /**
     * コンストラクタ．
     * <ol>
     *   <li>すべてのフィールドを初期化する．</li>
     * </ol>
     */
    public Account(){
    }

    /**
     * <ol>
     *   <li>ユーザIDを取得する</li>
     * </ol>
     * @return ユーザID
     */
    public int getId(){
        return id;
    }

    /**
     * <ol>
     *   <li>セッションIDを取得する</li>
     * </ol>
     * @return セッションID
     */
    public String getSessionId(){
        return sessionId;
    }

    public String getUser(){
        return user;
    }
    
    /**
     * <ol>
     *   <li>パスワードを取得する</li>
     * </ol>
     * @return パスワード
     */
    public String getPass(){
        return pass;
    }

    public List<Integer> getProject(){
        return project;
    }
    
        /**
     * <ol>
     *   <li>sessionIdをフィールドへ登録する．</li>
     * </ol>
     * @param sessionId セッションID
     */
    public void setSessionId(String sessionId){
        this.sessionId = sessionId;
    }

    public void setUser(String user){
        this.user = user;
    }

    /**
     * <ol>
     * <li>userIdをフィールドへ登録する．</li>
     * </ol>
     * @param id ユーザID
     */
    public void setId(int id){
        this.id = id;
    }

    /**
     * <ol>
     * <li>passをフィールドへ登録する．</li>
     * </ol>
     * @param pass パスワード
     */
    public void setPass(String pass){
        this.pass = pass;
    }
    
    public void setProject(List<Integer> project){
        this.project = project;
    }
    /*
    public void addProject(int projectId){
        this.project.add(projectId);
    }
    */
    
}
