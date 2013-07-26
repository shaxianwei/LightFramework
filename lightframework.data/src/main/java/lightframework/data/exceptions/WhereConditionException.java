/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lightframework.data.exceptions;

/**
 *
 * @author Tom Deng
 */
public class WhereConditionException extends RuntimeException {
    public WhereConditionException(String message){
        super(message);
    }
}
