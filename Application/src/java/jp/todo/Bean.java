/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.todo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author matsushita
 */
public class Bean {
    int id;
    String name;
    List<String> strLi = new ArrayList<String>();
    int intLi[] = new int[10];
    
    public int getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public List<String> getStrLi(){
        return strLi;
    }
    public int[] getIntLi(){
        return intLi;
    }
}
