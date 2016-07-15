/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.lang.reflect.Member;
import java.util.List;

/**
 *
 * @author matsushita
 */
public class Project {
    
    private int id;
    private String project;
    private List member;
    
    
    public Project(){
    }
    
    public int getId(){
        return this.id;
    }
    
    public String getProject(){
        return this.project;
    }
    
    public List getMember(){
        return this.member;
    }
    
    public void setId(int id){
        this.id = id;
    }
    
    public void setProject(String project){
        this.project = project;
    }
    
    public void setMember(List member){
        this.member = member;
    }
}


