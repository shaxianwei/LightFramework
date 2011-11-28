package lightframework.data.biz;

import lightframework.data.DataAccess;

/**
 *
 * @param <TDAO> 
 * @param <TEntity> 
 * @author Tom Deng
 */
public abstract class AbstractDataAccessBiz<TDAO extends DataAccess<TEntity>, TEntity>
        extends AbstractBaseBiz<TDAO, TEntity> {

    protected AbstractDataAccessBiz(TDAO dao) {
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
