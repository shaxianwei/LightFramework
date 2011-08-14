/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lightframework.data.mysql;

import lightframework.data.ViewDataAccess;

/**
 *
 * @author Tom Deng
 */
public abstract class AbstractViewDataAccess<T> extends AbstractBaseSelect<T> implements ViewDataAccess<T> {

    protected AbstractViewDataAccess(String tableName, String connectionString) {
        super(tableName, connectionString);
    }
}
