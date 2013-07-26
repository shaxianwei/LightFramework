package lightframework.data.biz;

import lightframework.data.Select;
import lightframework.data.ViewDataAccess;

/**
 *
 * @param <TDAO> 
 * @param <TEntity> 
 * @author Tom Deng
 */
public abstract class AbstractViewDataAccessBiz<TDAO extends Select<TEntity> & ViewDataAccess<TEntity>, TEntity>
        extends AbstractBaseBiz<TDAO, TEntity> {

    protected AbstractViewDataAccessBiz(TDAO dataAccessor) {
        super(dataAccessor);
    }
}
