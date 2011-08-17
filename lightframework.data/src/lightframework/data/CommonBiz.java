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

    private TDAO dao;

    protected CommonBiz(TDAO dao) {
        this.dao = dao;
    }

    public TDAO getDAO() {
        return this.dao;
    }

    public List<TEntity> getAll(String... columnNames) {
        return this.dao.select(columnNames);
    }
}
