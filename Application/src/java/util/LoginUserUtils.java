/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import model.Account;

/**
 *
 * @author matsushita
 */
public class LoginUserUtils{

    private static LoginUserUtils singleton = new LoginUserUtils();
    private Account loginUser;
    
    private LoginUserUtils(){
        loginUser = new Account();
    }
    
    public static LoginUserUtils getInstance(){
        singleton.resetLoginUser();
        return singleton;
    }
    
    public Account getLoginUser(){
        singleton.resetLoginUser();
        return singleton.loginUser;
    }
    
    public static void setLoginUser(Account loginUser){
        singleton.loginUser = loginUser;
    }
    
    private void resetLoginUser(){
        singleton.loginUser = AccountCollectionUtils.getInstance().getAccount(this.loginUser.getUser());
    }
   
}
