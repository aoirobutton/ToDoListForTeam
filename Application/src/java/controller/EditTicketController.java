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
import com.mongodb.DBObject;
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

    public boolean EditTicket(String ticket, String postresp, String responsible, String state, String description, String deadline, String project){
        DBCollection ticketCollection = TicketCollectionUtils.getInstance().getCollection();
        BasicDBObject query = new BasicDBObject("ticket",ticket)
                .append("responsible", postresp)
                .append("project", project);
        DBObject doc = ticketCollection.findOne(query);
        DBCursor cursor = ticketCollection.find(query);
        if(cursor.size() == 1){
            doc.put("responsible", responsible);
            doc.put("state", state);
            doc.put("description", description);
            doc.put("deadline", deadline);
            doc.put("responsible", responsible);
//            ticketCollection.update(query2);
            return true;
        }
        return false;
    }

    public List<String> getAccountList(String pro) {
        // ユーザー一覧を格納するリスト
        List<String> accountList = new ArrayList<String>();
        // 現在ログインしているユーザ名
        String user = LoginUserUtils.getInstance().getLoginUser().getUser();

        // プロジェクトのコレクションを取って来る
        DBCollection accountCollection = AccountCollectionUtils.getInstance().getCollection();

        // 自分が所属しているプロジェクトの一覧を取って来る
        Project project = getProject(pro);

        Account ac = new Account();

        accountList = project.getMember();
        return accountList;
        // 一回でも検索にヒットしたかのフラグ
/*
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
*/
    }

    public Project getProject(String pro){
        // 現在ログインしているユーザ名
        String user = LoginUserUtils.getInstance().getLoginUser().getUser();
        
        // プロジェクトのコレクションを取って来る
        DBCollection projectCollection = ProjectCollectionUtils.getInstance().getCollection();
        
        // ログインユーザが所属しているプロジェクトを検索
        // プロジェクト登録用のクエリーを作成
        BasicDBObject query = new BasicDBObject("member", user)
                .append("project", pro);
        // 検索
        DBCursor cursor = projectCollection.find(query);
        if(cursor.size() >= 1){
            // ユーザが所属しているプロジェクトが１つ以上ある
            Project p = new Project();
            while(cursor.hasNext()){
                p = gson.fromJson(cursor.next().toString(), Project.class);
            }
            return p;
        } else {
            return null;
        }
    }

}
