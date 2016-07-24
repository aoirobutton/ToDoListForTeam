/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

/**
 * アプリケーション例外を表すクラス．
 *
 * @author aoiro_makoto
 */
public class SystemException extends TodoException {

    private static final long serialVersionUID = 6388376710847740838L;

    public SystemException(String msg) {
        super(msg);
    }
}
