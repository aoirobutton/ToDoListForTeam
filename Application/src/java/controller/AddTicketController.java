/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import java.util.ArrayList;
import java.util.List;
import model.Project;
import util.TicketCollectionUtils;
import model.Ticket;
import util.AccountCollectionUtils;
import util.ProjectCollectionUtils;
import util.LoginUserUtils;

import com.mongodb.DB;
import java.util.HashSet;
import java.util.Set;
import java.util.Collection;
import java.util.LinkedHashSet;
import model.Account;
import util.DBUtils;
/**
 *
 * @author matsushita
 */
public class AddTicketController {
    
    private Gson gson = new Gson();
    
    public AddTicketController(){
    }
    
    public boolean AddTicket(Ticket ticket){
        // チケットのコレクションを取って来る
        DBCollection ticketCollection = TicketCollectionUtils.getInstance().getCollection();
        
        // 被り防止用の検索クエリー作成
        // プロジェクト内に同じチケット名がないか確認
        BasicDBObject doc = new BasicDBObject("ticket",ticket.getTicket())
                .append("project", ticket.getProject());
        // 検索
        DBCursor coll = ticketCollection.find(doc);
        if(coll.size() == 1){
            // 既に同じ名前のチケットがある
            // 何もせずに終了
            return false;
        }
        // 現在のログインユーザ名を取って来る
        String user = LoginUserUtils.getInstance().getLoginUser().getUser();
        // プロジェクトのコレクションを取得
        DBCollection projectCollection = ProjectCollectionUtils.getInstance().getCollection();
        // 選択したプロジェクトに担当者が所属してるか確認
        // ＆検索するためにクエリーのリストを作る
        List<BasicDBObject> queryList = new ArrayList<BasicDBObject>();
        
        BasicDBObject doc2_1 = new BasicDBObject("project", ticket.getProject())
                .append("member", user);
        BasicDBObject doc2_2 = new BasicDBObject("member", ticket.getResponsible());
        
        queryList.add(doc2_1);
        queryList.add(doc2_2);
        BasicDBObject doc3 = new BasicDBObject("$and", queryList);
        
        // 所属確認のための検索
        DBCursor coll2 = projectCollection.find(doc3);
        if(coll2.size() == 0){
            // 既に同じ名前のチケットがある
            // 何もせずに終了
            return false;
        }        
        // ここまで来たら被ってないので登録
        // チケット登録用のクエリーを作成
        BasicDBObject query = new BasicDBObject("ticket",ticket.getTicket())
                .append("responsible", ticket.getResponsible())
                .append("state", ticket.getState())
                .append("description", ticket.getDescription())
                .append("deadline", ticket.getDeadline())
                .append("project", ticket.getProject());
        // チケット登録して終了
        ticketCollection.insert(query);
        
        return true;
    }
    
    //////////////////////////////////////////// まだ途中
    public List<Account> getAccountList(){
        // ユーザー一覧を格納するリスト
        List<Account> accountList = new ArrayList<Account>();
        // 現在ログインしているユーザ名
        String user = LoginUserUtils.getInstance().getLoginUser().getUser();
        
        // アカウントのコレクションを取って来る
        DBCollection accountCollection = AccountCollectionUtils.getInstance().getCollection();
        
        // 自分が所属しているプロジェクトの一覧を取って来る
        List<Project> projectList = new ArrayList<Project>();
        projectList = getProjectList();
        if(projectList==null){
            return null;
        }

        Account ac = new Account();
        
        // 重複防止用のユーザ名リスト
        List<String> overlapList = new ArrayList<String>();
        // 一回でも検索にヒットしたかのフラグ
        boolean hit = false;
        for(Project p : projectList) {
            // それぞれのプロジェクト用にユーザ検索用のクエリーを作成
            BasicDBObject query = new BasicDBObject("project", p.getProject());
            // 検索
            DBCursor cursor = accountCollection.find(query);
            if(cursor.size() >= 1){
                while(cursor.hasNext()){
                    ac = gson.fromJson(cursor.next().toString(), Account.class);
                    if(!overlapList.contains(ac.getUser())) {
                        accountList.add(ac);
                        overlapList.add(ac.getUser());
                        hit = true;
                    }
                }
            }
        }
        
        if(hit) {
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
