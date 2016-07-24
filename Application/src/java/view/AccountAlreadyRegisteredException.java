/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

/**
 *
 * 指定されたUserIdを持つアカウントが既に登録されているときに投げられる例外クラス．
 * @author aoiro_makoto
 */
public class AccountAlreadyRegisteredException extends SystemException {

    private static final long serialVersionUID = 7581760299328703281L;

    public AccountAlreadyRegisteredException(String msg) {
        super(msg);
    }

}
