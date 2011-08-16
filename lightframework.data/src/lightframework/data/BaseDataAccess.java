/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lightframework.data;

/**
 * 
 * @author Tom Deng
 */
public interface BaseDataAccess<T> extends BaseInsert<T>, BaseDelete<T>, BaseUpdate<T>, BaseSelect<T> {
}
