/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lightframework.data;

import java.util.List;

/**
 *
 * @author Tom Deng
 */
public abstract class CommonBiz<TDAO extends BaseSelect<TEntity>, TEntity> {

    private TDAO _dataAccessor;

    protected CommonBiz(TDAO dataAccessor) {
        this._dataAccessor = dataAccessor;
    }

    public TDAO getDataAccessor() {
        return this._dataAccessor;
    }

    public List<TEntity> getAll(String... columnNames) {
        return this.getDataAccessor().select(columnNames);
    }
}
