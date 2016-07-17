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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author matsushita
 */
public class DisplayTicketListController {
    private DBCollection ticketCollection;// = TicketCollectionUtils.getInstance().getCollection();
    private DB db = DBUtils.getInstance().getDb();
    private DBCollection projectCollection = db.getCollection("project");
    //private DBCollection accountColl = TicketCollectionUtils.getCollection();
    private Gson gson = new Gson();

    public DisplayTicketListController(){
        ticketCollection = TicketCollectionUtils.getInstance().getCollection();
    }
    
    public List<Ticket> getTicketList(String... keys){
        List<Ticket> ticketList = new ArrayList<Ticket>();
        //String[] keys ={"responsible","project"};
        BasicDBObject query = new BasicDBObject();
        for(String key:keys){
            query.append(key,getSearchValue(key));
        }
        DBCursor cursor = ticketCollection.find(query);
        
        //どのチケットの責任者でもなければ, nullを返す.
        if(cursor.size() == 0){
            return null;
        }
        while(cursor.hasNext()){
                Ticket t = new Ticket();
                t = gson.fromJson(cursor.next().toString(), Ticket.class);
                ticketList.add(t);
        }
        return ticketList;
    }
    
    private Object getSearchValue(String key){
        switch(key){
            case "responsible":
                return Integer.toString(1);//LoginUserUtils.getUserId()); // LoginUtilUtils作るクラスのgetIDをstaticに
            case "project":
                //BasicDBObject search = new BasicDBObject("$in", LoginUserUtils.getProject()); 
                //return search;
            default:
                return null;
        }
    }
}
