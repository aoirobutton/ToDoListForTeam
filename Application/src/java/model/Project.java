/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author matsushita
 */
public class Project {
    
    private String project;
    private List<String> member = new ArrayList<>();
    private String description;
    
    
    public Project(){
    }
    
    public String getProject(){
        return this.project;
    }
    
    public List<String> getMember(){
        return this.member;
    }
    
    public String getDescription(){
        return this.description;
    }
    
    public void setProject(String project){
        this.project = project;
    }
    
    public void setMember(List<String> member){
        this.member = member;
    }
    
    public void setDescription(String description){
        this.description = description;
    }
}


