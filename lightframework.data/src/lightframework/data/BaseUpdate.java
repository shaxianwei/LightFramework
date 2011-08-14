/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lightframework.data;

/**
 *
 * @author Tom Deng
 */
public interface BaseUpdate<T> {

    int updateWithCondition(T entity, String condition, Object[] parameterValues, String... columnNames);
}
