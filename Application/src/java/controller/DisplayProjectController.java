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
import util.LoginUserUtils;
import util.ProjectCollectionUtils;

/**
 *
 * @author matsushita
 */
public class DisplayProjectController {
    Gson gson = new Gson();
    DBCollection projectCollection = ProjectCollectionUtils.getInstance().getCollection();
    
    public DisplayProjectController(){
    }
    
    public List<Project> getProjectList(){
        List<Project> projectList = new ArrayList<Project>();
        BasicDBObject query = new BasicDBObject("member",LoginUserUtils.getInstance().getLoginUser().getUser());
        DBCursor cursor = projectCollection.find(query);
        while(cursor.hasNext()){
            Project p = new Project();
            p = gson.fromJson(cursor.next().toString(), Project.class);
            projectList.add(p);
        }
        return projectList;
    }
    
    public Project getProject(String projectName) throws Exception{
        // プロジェクトの中にログインユーザが所属していて, projectNameで指定されたプロジェクトを探す.
        BasicDBObject query = new BasicDBObject("member",LoginUserUtils.getInstance().getLoginUser().getUser())
                .append("project", projectName);
        DBCursor cursor = projectCollection.find(query);
        if(cursor.size() == 1){
            return gson.fromJson(cursor.next().toString(), Project.class);
        }else{
            throw new Exception("プロジェクトないお");
        }
    }
    
}
