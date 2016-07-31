/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import java.util.ArrayList;
import java.util.List;
import util.TicketCollectionUtils;
import util.LoginUserUtils;
import model.Ticket;
import util.DBUtils;



/**
 *
 * @author aoiro_makoto
 */
public class EditTicketController {

    private DBCollection ticketCollection;// = TicketCollectionUtils.getInstance().getCollection();
    private DB db = DBUtils.getInstance().getDb();
    private DBCollection projectCollection = db.getCollection("project");
    //private DBCollection accountColl = TicketCollectionUtils.getCollection();
    private Gson gson = new Gson();

    public EditTicketController(){
        ticketCollection = TicketCollectionUtils.getInstance().getCollection();        
    }

    public boolean editTicket(Ticket ticket, String responsible, String state, String description, String deadline){
        DBCollection ticketCollection = TicketCollectionUtils.getInstance().getCollection();
        BasicDBObject query = new BasicDBObject("ticket",ticket.getTicket())
                .append("responsible", ticket.getResponsible())
                .append("project", ticket.getProject());
        DBCursor cursor = ticketCollection.find(query);
        if(cursor.size() == 1){
            ticketCollection.remove(query);
            return true;
        }
        return false;
    }


}
