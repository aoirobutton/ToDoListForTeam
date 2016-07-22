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
    private static Account singleton = new Account();
    
    private LoginUserUtils(String form){
        Gson gson = new Gson();
        singleton = gson.fromJson(form, Account.class);
    }
    
    public static String getUser(){
        return singleton.getUser();
    }
    
}
