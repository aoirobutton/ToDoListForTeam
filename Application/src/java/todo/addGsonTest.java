/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package todo;

import com.google.gson.Gson;
/**
 *
 * @author matsushita
 */
public class addGsonTest {
    public addGsonTest(){
    }
    
    public Bean func(String form){
        Gson gson = new Gson();
        Bean ret = gson.fromJson(form, Bean.class);
        return ret;//gson.toJson(ret, Bean.class);
    }
}
