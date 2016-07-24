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
    private List<String> project = new ArrayList<String>();

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
     *   <li>セッションIDを取得する</li>
     * </ol>
     * @return セッションID
     */
    public String getSessionId(){
        return sessionId;
    }

    /**
     * <ol>
     *   <li>ユーザ名を取得する</li>
     * </ol>
     * @return ユーザ名
     */
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

    public List<String> getProject(){
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

    /**
     * <ol>
     * <li>userをフィールドへ登録する．</li>
     * </ol>
     * @param user パスワード
     */
    public void setUser(String user){
        this.user = user;
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
    
    public void setProject(List<String> project){
        this.project = project;
    }
    /*
    public void addProject(int projectId){
        this.project.add(projectId);
    }
    */
    
}
