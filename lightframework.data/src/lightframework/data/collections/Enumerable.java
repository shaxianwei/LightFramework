/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lightframework.data.collections;

/**
 *
 * @author Tom Deng
 */
public interface Enumerable<T> {
    Enumerable<T> where(Predicate<T> predicate);
    void each(Action<T> action);
}
