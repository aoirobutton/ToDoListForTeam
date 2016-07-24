/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

/*
アプリケーションでは復帰不可能な例外を表すクラス．
*/

/**
 *
 * @author aoiro_makoto
 */
public class FatalException extends TodoException{
    private static final long serialVersionUID = -4332934532867614822L;

    public FatalException(Throwable cause) {
        super(cause);
    }

}
