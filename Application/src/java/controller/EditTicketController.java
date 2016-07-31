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
import model.Account;
import model.Project;
import util.TicketCollectionUtils;
import util.LoginUserUtils;
import model.Ticket;
import util.AccountCollectionUtils;
import util.DBUtils;
import util.ProjectCollectionUtils;


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

    public boolean editTicket(String ticket, String postresp, String responsible, String state, String description, String deadline, String project){
        DBCollection ticketCollection = TicketCollectionUtils.getInstance().getCollection();
        BasicDBObject query = new BasicDBObject("ticket",ticket)
                .append("responsible", postresp)
                .append("project", project);
        DBCursor cursor = ticketCollection.find(query);
        if(cursor.size() == 1){
            ticketCollection.remove(query);
            return true;
        }
        return false;
    }

    public List<Account> getAccountList(String pro) {
        // ユーザー一覧を格納するリスト
        List<Account> accountList = new ArrayList<Account>();
        // 現在ログインしているユーザ名
        String user = LoginUserUtils.getInstance().getLoginUser().getUser();

        // アカウントのコレクションを取って来る
        DBCollection accountCollection = AccountCollectionUtils.getInstance().getCollection();

        // 自分が所属しているプロジェクトの一覧を取って来る
        List<Project> projectList = new ArrayList<Project>();
        projectList = getProjectList();

        Account ac = new Account();

        // 一回でも検索にヒットしたかのフラグ
        boolean hit = false;
        for (Project p : projectList) {
            if (pro.equals(p.getProject())) {
                // ユーザ検索用のクエリーを作成
                BasicDBObject query = new BasicDBObject("project", p.getProject());
                // 検索
                DBCursor cursor = accountCollection.find(query);
                if (cursor.size() >= 1) {
                    while (cursor.hasNext()) {
                        ac = gson.fromJson(cursor.next().toString(), Account.class);
                        accountList.add(ac);
                        hit = true;
                    }
                }
            }
        }

        if (hit) {
            return accountList;
        } else {
            return null;
        }
    }

    public List<Project> getProjectList(){
        // プロジェクト一覧を格納するリスト
        List<Project> projectList = new ArrayList<Project>();
        // 現在ログインしているユーザ名
        String user = LoginUserUtils.getInstance().getLoginUser().getUser();
        
        // プロジェクトのコレクションを取って来る
        DBCollection projectCollection = ProjectCollectionUtils.getInstance().getCollection();
        
        // ログインユーザが所属しているプロジェクトを検索
        // プロジェクト登録用のクエリーを作成
        BasicDBObject query = new BasicDBObject("member", user);
        // 検索
        DBCursor cursor = projectCollection.find(query);
        if(cursor.size() >= 1){
            // ユーザが所属しているプロジェクトが１つ以上ある
            Project p = new Project();
            while(cursor.hasNext()){
                p = gson.fromJson(cursor.next().toString(), Project.class);
                projectList.add(p);
            }
            return projectList;
        } else {
            return null;
        }
    }

}
