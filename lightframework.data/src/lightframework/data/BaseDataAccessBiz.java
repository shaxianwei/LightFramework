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

    protected BaseDataAccessBiz(TDAO dao) {
        super(dao);
    }

    public int addWithId(TEntity entity) {
        return this.getDAO().insertWithId(entity);
    }

    public int add(TEntity entity) {
        return this.getDAO().insert(entity);
    }

    public void clear() {
        this.getDAO().deleteAll();
    }
}
