/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lightframework.data;

/**
 *
 * @author Tom Deng
 */
public abstract class BaseDataAccessBiz<TDAO extends BaseDataAccess<TEntity>, TEntity> extends CommonBiz<TDAO, TEntity> {

    protected BaseDataAccessBiz(TDAO dataAccessor) {
        super(dataAccessor);
    }

    public int addWithId(TEntity entity) {
        return this.getDataAccessor().insertWithId(entity);
    }

    public int add(TEntity entity) {
        return this.getDataAccessor().insert(entity);
    }

    public void clear() {
        this.getDataAccessor().deleteAll();
    }
}
