package lightframework.data.mysql;

import lightframework.data.*;
import lightframework.data.annotations.Column;
import lightframework.data.exceptions.WhereConditionException;
import java.lang.reflect.Field;
import lightframework.data.configuration.Configurationable;

/**
 *
 * @author Tom Deng
 */
public abstract class AbstractSinglePKDataAccess<T> extends AbstractDataAccess<T> implements SinglePKDataAccess<T> {

    protected String primaryKey;

    protected AbstractSinglePKDataAccess(String tableName, String primaryKey, String dbAlias, Configurationable config) {
        super(tableName, dbAlias, config);
        this.primaryKey = primaryKey;
    }

    public String getPrimaryKey() {
        return this.primaryKey;
    }

    @Override
    public int delete(int keyValue) {
        String condition = String.format("%1$s = %2$s", this.primaryKey, keyValue);
        return this.deleteWithCondition(condition);
    }

    @Override
    public int delete(String keyValue) {
        String condition = String.format("%s = ?", this.primaryKey);
        return this.deleteWithCondition(condition, keyValue);
    }

    @Override
    public int delete(int... keyValues) {
        String[] keys = new String[keyValues.length];
        for (int i = 0; i < keyValues.length; i++) {
            keys[i] = Integer.valueOf(keyValues[0]).toString();
        }

        return this.delete(keys);
    }

    @Override
    public int delete(String... keyValues) {
        return this.deleteIn(this.join(",", keyValues));
    }

    @Override
    public int deleteIn(String keyValues) {
        String condition = String.format("%1$s IN(%2$s)", this.primaryKey, keyValues);
        return this.deleteWithCondition(condition);
    }

    @Override
    public int update(T entity, String... columnNames) {
        String condition = String.format("%s = ?", this.primaryKey);
        Object id = this.getPrimaryKeyValue(entity.getClass(), entity);
        Object[] parameterValues = new Object[]{id};

        return this.updateWithCondition(entity, condition, parameterValues, columnNames);
    }

    @Override
    public int update(T entity, int id, String... columnNames) {
        String condition = String.format("%1$s = %2$s", this.primaryKey, id);
        return this.updateWithCondition(entity, condition, null, columnNames);
    }

    @Override
    public int update(T entity, String id, String... columnNames) {
        String condition = String.format("%s = ?", this.primaryKey);
        Object[] parameterValues = new Object[]{id};

        return this.updateWithCondition(entity, condition, parameterValues, columnNames);
    }

    @Override
    public int updateIn(T entity, String keyValues, String... columnNames) {
        String condition = String.format("%1$s IN(%1$s)", this.primaryKey, keyValues);
        return this.updateWithCondition(entity, condition, null, columnNames);
    }

    @Override
    public int getMaxID() {
        return this.getMaxValue(this.primaryKey, 10, "");
    }

    @Override
    public int getMaxValue(String fieldName, int fromBase, String condition, Object... parameterValues) {
        if (!this.containWhere(condition)) {
            throw new WhereConditionException("condition:指定的条件,要求带SQL语句Where关键字的条件");
        }

        StringBuilder strSql = new StringBuilder();
        strSql.append("SELECT MAX(%1$s) AS MaxValue FROM %2$s %3$s");

        String sqlCmd = String.format(strSql.toString(), fieldName, this.tableName, condition);
        Object obj = MySqlHelper.executeScalar(this.getConnection(), sqlCmd, parameterValues);
        return Integer.valueOf(obj.toString()).intValue();
    }

    @Override
    public boolean isExistKey(int keyValue) {
        String condition = String.format("%1$s = %2$s", this.primaryKey, keyValue);
        return this.isExistWithCondition(condition);
    }

    @Override
    public boolean isExistKey(int... keyValues) {
        String[] keys = new String[keyValues.length];
        for (int i = 0; i < keyValues.length; i++) {
            keys[i] = Integer.valueOf(keyValues[i]).toString();
        }

        return this.isExistKey(keys);
    }

    @Override
    public boolean isExistKey(String keyValue) {
        String condition = String.format("%s = ?", this.primaryKey);
        Object[] parameterValues = new Object[]{keyValue};

        return this.isExistWithCondition(condition, parameterValues);
    }

    @Override
    public boolean isExistKey(String... keyValues) {
        return this.isExistKey(this.join(",", keyValues));
    }

    @Override
    public boolean isExistKeyIn(String keyValues) {
        String condition = String.format("%1$s IN(%2$s)", this.primaryKey, keyValues);
        return this.isExistWithCondition(condition);
    }

    @Override
    public T select(String keyValue, String... columnNames) {
        String condition = String.format("WHERE %1$s = %2$s", this.primaryKey, keyValue);
        return this.selectOne(condition, null, columnNames);
    }

    @Override
    public T select(int keyValue, String... columnNames) {
        String condition = String.format("WHERE %1$s = ?", this.primaryKey);
        return this.selectOne(condition, new Object[]{keyValue}, columnNames);
    }

    @Override
    public PageData<T> selectWithPageSizeByIdentity(int pageSize, int pageIndex, String condition, String orderByColumnName, SortTypeEnum sortType, Object[] parameterValues, String... columnNames) {
        return this.selectWithPageSizeByRowId(pageSize, pageIndex, condition, orderByColumnName, sortType, parameterValues, columnNames);
    }

    private Object getPrimaryKeyValue(Class<?> entityType, T entity) {
        Object value = null;
        Field[] fields = entityType.getDeclaredFields();
        if (fields == null || fields.length == 0) {
            return value;
        }

        try {
            for (Field field : fields) {
                Column column = field.getAnnotation(Column.class);
                if (column == null || !column.isPrimaryKey()) {
                    continue;
                }
                field.setAccessible(true);
                value = field.get(entity);
                break;
            }
        } catch (IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }

        return value;
    }
}
