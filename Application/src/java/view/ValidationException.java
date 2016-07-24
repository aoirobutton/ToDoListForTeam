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
public class ValidationException extends ViewException{
    
    private static final long serialVersionUID = 4214304664949192996L;

    public ValidationException(String message) {
        super(message);
    }

}
