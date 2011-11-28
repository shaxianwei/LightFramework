package lightframework.data.mysql;

import lightframework.data.ViewDataAccess;
import lightframework.data.configuration.Configurationable;

/**
 *
 * @author Tom Deng
 */
public abstract class AbstractViewDataAccess<T> extends AbstractSelect<T> implements ViewDataAccess<T> {

    protected AbstractViewDataAccess(String tableName, String dbAlias, Configurationable config) {
        super(tableName, dbAlias,config);
    }
}
