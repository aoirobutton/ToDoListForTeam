/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.Account;
import model.Project;
import util.AccountCollectionUtils;
import util.ProjectCollectionUtils;

/**
 *
 * @author matsushita
 */
public class EditProjectController {
    public EditProjectController(){
    }
    
    public void addMember(String ProjectName, String newMember) throws Exception{
        Project project = ProjectCollectionUtils.getInstance().getProject(ProjectName);
        Account newMemberAccount = AccountCollectionUtils.getInstance().getAccount(newMember);
        if(newMemberAccount != null && newMemberAccount.getProject().indexOf(ProjectName) == -1){
            ProjectCollectionUtils.getInstance().addMember(ProjectName, newMember);
            AccountCollectionUtils.getInstance().addProject(newMember, ProjectName);
        }else{
            throw new Exception("そのユーザは存在しないか, 既に同名のプロジェクトに参加しています.");
        }
    }
    
}
