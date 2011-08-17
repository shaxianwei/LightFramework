/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lightframework.data;

/**
 *
 * @author Tom Deng
 */
public abstract class SinglePKDataAccessBiz<TDAO extends BaseDataAccess<TEntity> & SinglePKDataAccess<TEntity>, TEntity> extends BaseDataAccessBiz<TDAO, TEntity> {

    protected SinglePKDataAccessBiz(TDAO dao) {
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
