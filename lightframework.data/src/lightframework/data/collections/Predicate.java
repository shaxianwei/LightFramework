/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lightframework.data.collections;

/**
 *
 * @author Tom Deng
 */
public interface Predicate<T> {
    boolean isMatch(T element);
}
