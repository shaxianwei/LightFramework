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

    boolean delete(int keyValue);

    boolean delete(String keyValue);

    boolean delete(int... keyValues);

    boolean delete(String... keyValues);

    boolean deleteIn(String keyValues);
}
