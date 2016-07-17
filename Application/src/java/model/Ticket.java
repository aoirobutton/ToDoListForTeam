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
    int id;
    String ticket;
    String user;
    int responsible;
    String state;
    String description;
    String deadline;
    int project;
    
    public Ticket(){
    }
    
    public int getID(){
        return this.id;
    }
    public String getTicket(){
        return this.ticket;
    }
    public String getUser(){
        return this.user;
    }
    public int getResponsible(){
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
    public int getProject(){
        return this.project;
    }
    
    public void setID(int id){
        this.id = id;
    }
    public void setTicket(String ticket){
        this.ticket = ticket;
    }
    public void setUser(String user){
        this.user = user;
    }
    public void setResponsible(int responsible){
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
    public void setProject(int project){
        this.project = project;
    }
}
