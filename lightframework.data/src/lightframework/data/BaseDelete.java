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

    int deleteWithCondition(String condition, Object... parameterValues);

    int deleteAll();

    void truncate();
}
