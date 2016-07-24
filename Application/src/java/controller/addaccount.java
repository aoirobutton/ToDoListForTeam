package controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author aoiro_makoto
 */

import model.accountmodel;
import view.IdentifyingAccountForm;
import view.ViewException;
import view.AccountAlreadyRegisteredException;
import view.FatalException;

public class addaccount {
    public void execute(IdentifyingAccountForm form) throws ViewException {
//        form.validate();

        try {
            accountmodel accountModel = new accountmodel();
            accountModel.registerAccount(form.getUser(), form.getPass());

        } catch (AccountAlreadyRegisteredException e) {
            throw new ViewException("指定されたユーザIDは既に存在します", e);
        } catch (FatalException e) {
            throw new ViewException("システムエラーが発生しました．管理者に連絡してください", e);
        }
    }
}
