/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import com.google.gson.Gson;
import com.mongodb.Mongo;
import java.util.ArrayList;
import java.util.List;
import model.Account;
import sun.security.jca.GetInstance;

/**
 *
 * @author matsushita
 */
public class LoginUserUtils{

//<<<<<<< HEAD
    private static LoginUserUtils singleton = new LoginUserUtils();
    private static Account loginUser;
    
    private LoginUserUtils(){
        loginUser = new Account();
        loginUser.setUser("user1");
        List<String> li = new ArrayList<String>();
        li.add("project3");
        loginUser.setProject(li);
    }
    
    public static LoginUserUtils getInstance(){
        return singleton;
    }
    
    public Account getLoginUser(){
        return singleton.loginUser;
/*        
=======
    private static LoginUserUtils singlrton = new LoginUserUtils();
    private static Account loginUser = new Account();
    
    private LoginUserUtils(){
    }
    
    public static Account getLoginUser(){
        return singlrton.loginUser;

//    public static String getUser(){
//        return singleton.getUser();

>>>>>>> master
*/        
    }
    
    public static void setLoginUser(Account loginUser){
        singleton.loginUser = loginUser;
    }
   
}
