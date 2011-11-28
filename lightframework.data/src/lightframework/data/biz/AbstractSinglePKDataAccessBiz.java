package lightframework.data.biz;

import lightframework.data.DataAccess;
import lightframework.data.SinglePKDataAccess;

/**
 *
 * @param <TDAO> 
 * @param <TEntity> 
 * @author Tom Deng
 */
public abstract class AbstractSinglePKDataAccessBiz<TDAO extends DataAccess<TEntity> & SinglePKDataAccess<TEntity>, TEntity>
        extends AbstractDataAccessBiz<TDAO, TEntity> {

    protected AbstractSinglePKDataAccessBiz(TDAO dao) {
        super(dao);
    }

    public boolean isExist(int id) {
        return this.getDAO().isExistKey(id);
    }

    public boolean isExist(String id) {
        return this.getDAO().isExistKey(id);
    }

    public int delete(int id) {
        return this.getDAO().delete(id);
    }

    public int delete(String id) {
        return this.getDAO().delete(id);
    }

    public int modify(TEntity entity, String... columnNames) {
        return this.getDAO().update(entity, columnNames);
    }

    public int modify(TEntity entity, int id, String... columnNames) {
        return this.getDAO().update(entity, id, columnNames);
    }

    public int modify(TEntity entity, String id, String... columnNames) {
        return this.getDAO().update(entity, id, columnNames);
    }

    public int modifyIn(TEntity entity, String ids, String... columnNames) {
        return this.getDAO().updateIn(entity, ids, columnNames);
    }

    public TEntity getById(String id, String... columnNames) {
        return this.getDAO().select(id, columnNames);
    }

    public TEntity getById(int id, String... columnNames) {
        return this.getDAO().select(id, columnNames);
    }
}
