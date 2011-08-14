/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lightframework.data.collections;

/**
 *
 * @author Tom Deng
 */
public interface Action<T>{
    void execute(T element);
}