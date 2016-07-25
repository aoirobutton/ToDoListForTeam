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
    
    public List<Ticket> getTicketList(List<String> keys){
        List<Ticket> ticketList = new ArrayList<Ticket>();
        //String[] keys ={"responsible","project"};
        List<BasicDBObject> li = new ArrayList<BasicDBObject>();
        li.add(new BasicDBObject("project", new BasicDBObject("$in", LoginUserUtils.getInstance().getLoginUser().getProject())));
        li.add(new BasicDBObject("responsible",LoginUserUtils.getInstance().getLoginUser().getUser()));
        BasicDBObject queryDef = new BasicDBObject("$or",li);
        List<BasicDBObject> queryList = new ArrayList<BasicDBObject>();
        queryList.add(queryDef);
        for(String key:keys){
            queryList.add(getSearchValue(key));
        }
        BasicDBObject query = new BasicDBObject("$and", queryList);
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
    
    private BasicDBObject getSearchValue(String key){
        BasicDBObject search;
        switch(key){
            case "--responsible":
                return new BasicDBObject("responsible",LoginUserUtils.getInstance().getLoginUser().getUser()); 
            case "--project":
                search = new BasicDBObject("$in", LoginUserUtils.getInstance().getLoginUser().getProject()); 
                return new BasicDBObject("project", search);
            default:
                List<BasicDBObject> li = new ArrayList<BasicDBObject>();
                //BasicDBObject li = new BasicDBObject();
                li.add(new BasicDBObject("ticket",new BasicDBObject("$regex",".*"+key+".*")));
                li.add(new BasicDBObject("responsible", new BasicDBObject("$regex",".*"+key+".*")));
                li.add(new BasicDBObject("state", new BasicDBObject("$regex",".*"+key+".*")));
                //li.add(new BasicDBObject("description", new BasicDBObject("$regex",".*"+key+".*")));
                li.add(new BasicDBObject("deadline", new BasicDBObject("$regex",".*"+key+".*")));
                li.add(new BasicDBObject("project", new BasicDBObject("$regex",".*"+key+".*")));
                search = new BasicDBObject("$or",li);
                return search;
        }
    }
}
