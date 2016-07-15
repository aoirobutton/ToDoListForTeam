/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package todo.controller;

/**
 *
 * @author matsushita
 */
public class Ticket {
    int ID;
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
        return this.ID;
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
}
