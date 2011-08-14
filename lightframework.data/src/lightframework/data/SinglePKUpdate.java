/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lightframework.data;

/**
 *
 * @author Tom Deng
 */
public interface SinglePKUpdate<T> {
    
        int update(T entity, int id, String... columnNames);

        int update(T entity, String id, String... columnNames);

        int updateIn(T entity, String keyValues, String... columnNames);
}
