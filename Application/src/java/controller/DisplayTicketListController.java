package controller;


import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import util.TicketCollectionUtils;
import util.LoginUserUtils;
import model.Ticket;
import util.DBUtils;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import util.ProjectCollectionUtils;

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
        List<String> projectNames = LoginUserUtils.getInstance().getLoginUser().getProject();
        for(int i =0;i<projectNames.size();i++){
            li.add(new BasicDBObject("project",projectNames.get(i)).append("responsible", new BasicDBObject("$in", ProjectCollectionUtils.getInstance().getProject(projectNames.get(i)).getMember())));
        }
        //li.add(new BasicDBObject("project", new BasicDBObject("$in", LoginUserUtils.getInstance().getLoginUser().getProject())));
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
    
    public BasicDBObject getSearchValue(String key){
        BasicDBObject search;
        List<String> keys = Arrays.asList("ticket","responsible","state","deadline","project");
        String regex = "^--(.+):(.+)"; //--key:value で指定したkeyの中で検索
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(key);
        if(m.find()){
            keys = Arrays.asList(m.group(1));
            key = m.group(2);
        }
        switch(key){
            case "--responsible":
                return new BasicDBObject("responsible",LoginUserUtils.getInstance().getLoginUser().getUser()); 
            case "--project":
                search = new BasicDBObject("$in", LoginUserUtils.getInstance().getLoginUser().getProject()); 
                return new BasicDBObject("project", search);
            //projectの中だけでキーワード検索できるようにする.
            //case "?project":
                //keys = Arrays.asList("project");
            default:
                List<BasicDBObject> li = new ArrayList<BasicDBObject>();
                for(int i=0; i<keys.size(); i++){
                    li.add(new BasicDBObject(keys.get(i),new BasicDBObject("$regex",".*"+key+".*")));
                }
                /*
                //BasicDBObject li = new BasicDBObject();
                li.add(new BasicDBObject("ticket",new BasicDBObject("$regex",".*"+key+".*")));
                li.add(new BasicDBObject("responsible", new BasicDBObject("$regex",".*"+key+".*")));
                li.add(new BasicDBObject("state", new BasicDBObject("$regex",".*"+key+".*")));
                //li.add(new BasicDBObject("description", new BasicDBObject("$regex",".*"+key+".*")));
                li.add(new BasicDBObject("deadline", new BasicDBObject("$regex",".*"+key+".*")));
                li.add(new BasicDBObject("project", new BasicDBObject("$regex",".*"+key+".*")));
                */
                search = new BasicDBObject("$or",li);
                return search;
        }
    }
}
