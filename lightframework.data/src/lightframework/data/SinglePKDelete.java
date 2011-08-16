/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lightframework.data;

/**
 *
 * @author Tom Deng
 */
public interface SinglePKDelete<T> {

    int delete(int keyValue);

    int delete(String keyValue);

    int delete(int... keyValues);

    int delete(String... keyValues);

    int deleteIn(String keyValues);
}
