/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.google.gson.Gson;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import model.Project;
import util.ProjectCollectionUtils;

/**
 *
 * @author matsushita
 */
public class RegisterProjectController {
    DBCollection projectCollection = ProjectCollectionUtils.getInstance().getCollection();
    //Gson gson = new Gson();
    public RegisterProjectController(){        
    }
    
    public boolean Register(Project newProject){
        //Project newProject = gson.fromJson(form, Project.class);
        if(FormCheck(newProject)){
            BasicDBObject doc = new BasicDBObject("id",newProject.getId())
                    .append("project",newProject.getProject())
                    .append("member", newProject.getMember())
                    .append("description", newProject.getDescription()); // ここうまいこと作れないかなぁ
            projectCollection.insert(doc);
            return true;
        }
        return false;
    }
    
    // projectに登録できる形になっているか確認.
    private boolean FormCheck(Project project){
        //idの確認, 重複しないようにするにはどうすれば??idいる？
        //projectの名前があるかかくにん.
        BasicDBObject query = new BasicDBObject("project",project.getProject());
        DBCursor cursor = projectCollection.find(query);
        if(cursor.size() > 0){
            return false;
        }
        
        //要素の重複除去 //要確認
        List<Integer> newList = new ArrayList<Integer>(new HashSet<>(project.getMember()));        
        project.setMember(newList);
            //代案
            // Java8から追加された Stream を使う方法
            //list.stream().distinct().collect(Collectors.toList());
        
        //member が一人以上入りることを書くにん.(そのメンバーが登録されているかとか)
        if(project.getMember().isEmpty()){
            return false;
        }
        return true;
    }
}
