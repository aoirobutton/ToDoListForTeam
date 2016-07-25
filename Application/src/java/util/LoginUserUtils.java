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

    private static LoginUserUtils singleton = new LoginUserUtils();
    private static Account loginUser;
    
    private LoginUserUtils(){
        loginUser = new Account();
    }
    
    public static LoginUserUtils getInstance(){
        return singleton;
    }
    
    public Account getLoginUser(){
        return singleton.loginUser;
    }
    
    public static void setLoginUser(Account loginUser){
        singleton.loginUser = loginUser;
    }
   
}
