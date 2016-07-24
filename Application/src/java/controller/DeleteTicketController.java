/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import util.TicketCollectionUtils;

/**
 *
 * @author matsushita
 */
public class DeleteTicketController {
    public DeleteTicketController(){
    }
    
    public boolean deleteTicket(String ticket, String responsible, String project){
        DBCollection ticketCollection = TicketCollectionUtils.getInstance().getCollection();
        BasicDBObject query = new BasicDBObject("ticket",ticket)
                .append("responsible", responsible)
                .append("project", project);
        DBCursor cursor = ticketCollection.find(query);
        if(cursor.size() == 1){
            ticketCollection.remove(query);
            return true;
        }
        return false;
    }
    
}
