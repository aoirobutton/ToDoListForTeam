/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import model.Project;
import util.AccountCollectionUtils;
import util.LoginUserUtils;
import util.ProjectCollectionUtils;

/**
 *
 * @author matsushita
 */
public class RegisterProjectController {
    DBCollection projectCollection = ProjectCollectionUtils.getInstance().getCollection();
    public RegisterProjectController(){        
    }
    
    public boolean Register(Project newProject) throws Exception{
        newProject.getMember().add(LoginUserUtils.getInstance().getLoginUser().getUser());
        try {
            if(FormCheck(newProject)){
                this.insertProject2Account(newProject);
                BasicDBObject doc = new BasicDBObject("project",newProject.getProject())
                        .append("member", newProject.getMember())
                        .append("description", newProject.getDescription()); // ここうまいこと作れないかなぁ
                projectCollection.insert(doc);
                return true;
            }            
        } catch (Exception e) {
            throw new Exception("プロジェクト名が重複しています.", e);
        }
        return false;
    }
    
    private void insertProject2Account(Project project){
        DBCollection accountCollection = AccountCollectionUtils.getInstance().getCollection();
        for(int i=0; i<project.getMember().size(); i++){
            BasicDBObject search = new BasicDBObject("user",project.getMember().get(i));
            BasicDBObject update = new BasicDBObject("$push",new BasicDBObject("project",project.getProject()));
            accountCollection.update(search, update);
        }
    }
    
    // projectに登録できる形になっているか確認.
    private boolean FormCheck(Project project) throws Exception{
        //projectの名前があるかかくにん.
        BasicDBObject query = new BasicDBObject("project",project.getProject())
                .append("member", LoginUserUtils.getInstance().getLoginUser().getUser());
        DBCursor cursor = projectCollection.find(query);
        if(cursor.size() > 0){
            throw new Exception();
        }
        
        //要素の重複除去 //要確認
        List<String> newList = new ArrayList<String>(new HashSet<>(project.getMember()));
        newList.remove("");
        project.setMember(newList);
            //代案
            // Java8から追加された Stream を使う方法
            //list.stream().distinct().collect(Collectors.toList());
        
        return true;
    }
    
    public boolean existUsers(Project project) throws Exception{
        //要素の重複除去 //要確認
        List<String> newList = new ArrayList<String>(new HashSet<>(project.getMember()));
        newList.remove("");
        project.setMember(newList);
        return AccountCollectionUtils.getInstance().existUsers(project.getMember());
    }
}
