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
    
    
    @Override
    public boolean equals(Object obj){

        if (obj==null) return false;

        if (! (obj instanceof Account) ) return false;
        if (this==obj) return true;

        Account other = (Account)obj;
        if(user != other.user) return false;
        if(pass == null && other.pass != null || pass != null && other.pass == null) return false;
        if(project == null && other.project != null || project != null && other.project == null ) return false;

        if(user == other.user && pass.equals(other.pass) && project.equals(project)) return true;

        return false;
    }
    
    @Override
    public int hashCode() {
        final int oddPrime = 31;
        int result = oddPrime;
        result += (user == null) ? 0 : user.hashCode();
        result *= oddPrime;
  //      result += (pass == null) ? 0 : pass.hashCode();
  //      result *= oddPrime;
  //      result += (project == null) ? 0 : project.hashCode();
        return result;
    }
}
