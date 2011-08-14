/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lightframework.data;

/**
 *
 * @author Tom Deng
 */
public interface BaseDelete<T> {

    boolean deleteWithCondition(String condition, Object... parameterValues);

    void deleteAll();

    void truncate();
}
