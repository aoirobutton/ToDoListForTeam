/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import com.google.gson.Gson;
import model.Account;

/**
 *
 * @author matsushita
 */
public class LoginUserUtils{
    private static LoginUserUtils singlrton = new LoginUserUtils();
    private static Account loginUser = new Account();
    
    private LoginUserUtils(){
    }
    
<<<<<<< HEAD
    public static Account getLoginUser(){
        return singlrton.loginUser;
=======
    public static String getUser(){
        return singleton.getUser();
>>>>>>> master
    }
    
    public static void setLoginUser(Account loginUser){
        singlrton.loginUser = loginUser;
    }
   
}
