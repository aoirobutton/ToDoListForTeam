/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import util.LoginUserUtils;

/**
 *
 * @author matsushita
 */
public class LogoutController {
    public LogoutController(){
    }
    
    public boolean logout() throws Exception{
        try{
            LoginUserUtils.getInstance().delete();
            return true;
        }catch(Exception e){
            return false;
        }
    }
}
