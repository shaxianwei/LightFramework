package lightframework.data.biz;

import java.util.List;

import lightframework.data.Select;

/**
 *
 * @param <TDAO> 
 * @param <TEntity> 
 * @author Tom Deng
 */
public abstract class AbstractBaseBiz<TDAO extends Select<TEntity>, TEntity> {

    private TDAO dao;

    protected AbstractBaseBiz(TDAO dao) {
        this.dao = dao;
    }

    public TDAO getDAO() {
        return this.dao;
    }

    public List<TEntity> getAll(String... columnNames) {
        return this.dao.select(columnNames);
    }
}
