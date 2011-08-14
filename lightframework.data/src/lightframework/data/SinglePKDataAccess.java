/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lightframework.data;

/**
 *
 * @author Tom Deng
 */
public interface SinglePKDataAccess<T> extends SinglePKInsert<T>, SinglePKDelete<T>, SinglePKUpdate<T>, SinglePKSelect<T> {
}
