/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author matsushita
 */
public class Ticket {
    String ticket;
    String user;
    String responsible;
    String state;
    String description;
    String deadline;
    String project;
    
    public Ticket(){
    }
    
    public String getUser(){
        return this.user;
    }
    public String getResponsible(){
        return this.responsible;
    }
    public String getState(){
        return this.state;
    }
    public String getDescription(){
        return this.description;
    }
    public String getDeadline(){
        return this.deadline;
    }
    public String getProject(){
        return this.project;
    }
    
    public void setUser(String user){
        this.user = user;
    }
    public void setResponsible(String responsible){
        this.responsible = responsible;
    }
    public void setState(String state){
        this.state = state;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public void setDeadline(String deadline){
        this.deadline = deadline;
    }
    public void setProject(String project){
        this.project = project;
    }
}
