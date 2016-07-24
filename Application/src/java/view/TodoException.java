/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

/**
 *
 * @author aoiro_makoto
 */
public class TodoException extends Exception {

    private static final long serialVersionUID = 4929153935825984769L;

    public TodoException() {
        super();
    }

    public TodoException(String message, Throwable cause,
            boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public TodoException(String message, Throwable cause) {
        super(message, cause);
    }

    public TodoException(String message) {
        super(message);
    }

    public TodoException(Throwable cause) {
        super(cause);
    }

}
