/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lightframework.data;

/**
 *
 * @author Tom Deng
 */
public abstract class ViewDataAccessBiz<TDAO extends BaseSelect<TEntity> & ViewDataAccess<TEntity>, TEntity> extends CommonBiz<TDAO, TEntity> {

    protected ViewDataAccessBiz(TDAO dataAccessor) {
        super(dataAccessor);
    }
}
