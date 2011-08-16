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

    protected SinglePKDataAccessBiz(TDAO dataAccessor) {
        super(dataAccessor);
    }

    public boolean isExist(int id) {
        return this.getDataAccessor().isExistKey(id);
    }

    public boolean isExist(String id) {
        return this.getDataAccessor().isExistKey(id);
    }

    public int delete(int id) {
        return this.getDataAccessor().delete(id);
    }

    public int delete(String id) {
        return this.getDataAccessor().delete(id);
    }

    public int modify(TEntity entity, int id, String... columnNames) {
        return this.getDataAccessor().update(entity, id, columnNames);
    }

    public int modify(TEntity entity, String id, String... columnNames) {
        return this.getDataAccessor().update(entity, id, columnNames);
    }

    public int modifyIn(TEntity entity, String ids, String... columnNames) {
        return this.getDataAccessor().updateIn(entity, ids, columnNames);
    }

    public TEntity getById(String id, String... columnNames) {
        return this.getDataAccessor().select(id, columnNames);
    }

    public TEntity getById(int id, String... columnNames) {
        return this.getDataAccessor().select(id, columnNames);
    }
}
