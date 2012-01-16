package lightframework.data.mysql;

import java.util.ArrayList;
import java.util.Arrays;

import lightframework.data.*;
import lightframework.data.collections.ColumnMap;
import lightframework.data.exceptions.WhereConditionException;
import lightframework.data.configuration.Configurationable;
import lightframework.data.util.StringExtension;

/**
 *
 * @param <T> 
 * @author Tom Deng
 */
public abstract class AbstractDataAccess<T> extends AbstractSelect<T> implements DataAccess<T> {

    protected AbstractDataAccess(String tableName, String dbAlias, Configurationable config) {
        super(tableName, dbAlias, config);
    }

    @Override
    public int insert(T entity) {
        SqlExpression sqlExpr = this.generateInsertSqlExpression(this.getColumnMap(entity), this.tableName);
        return MySqlHelper.executeNonQuery(this.getConnection(), sqlExpr.getCommandText(), sqlExpr.getParameters());
    }

    @Override
    public int insertWithId(T entity) {
        SqlExpression sqlExpr = this.generateInsertSqlExpression(this.getColumnMap(entity), this.tableName);
        String sqlCmd = sqlExpr.getCommandText();
        Object lastId = MySqlHelper.executeScalar(this.getConnection(), sqlCmd, true, sqlExpr.getParameters());
        return Integer.valueOf(lastId.toString()).intValue();
    }

    @Override
    public int deleteAll() {
        String sqlCmd = String.format("DELETE FROM %s", this.tableName);
        return MySqlHelper.executeNonQuery(this.getConnection(), sqlCmd);
    }

    @Override
    public int deleteWithCondition(String condition, Object... parameterValues) {
        if (this.containWhere(condition)) {
            throw new WhereConditionException("condition:指定的条件,不要求带SQL语句Where关键字的条件");
        }

        if (StringExtension.isNullOrEmpty(condition)) {
            return -1;
        }

        StringBuilder strSql = new StringBuilder();
        strSql.append("DELETE FROM %1$s WHERE %2$s");
        String sqlCmd = String.format(strSql.toString(), this.tableName, condition);
        return MySqlHelper.executeNonQuery(this.getConnection(), sqlCmd, parameterValues);
    }

    @Override
    public void truncate() {
        String sqlCmd = String.format("TRUNCATE TABLE %s", this.tableName);
        MySqlHelper.executeNonQuery(this.getConnection(), sqlCmd);
    }

    @Override
    public int updateWithCondition(T entity, String condition, Object[] parameterValues, String... columnNames) {
        if (entity == null) {
            throw new NullPointerException("entity:未将对象引用到实例");
        }

        if (this.containWhere(condition)) {
            throw new WhereConditionException("condition:指定的条件,不要求带SQL语句Where关键字的条件");
        }

        SqlExpression sqlExpr = this.generateUpdateSqlExpression(condition,
                this.getColumnMap(entity, columnNames), this.tableName, parameterValues);
        return MySqlHelper.executeNonQuery(this.getConnection(), sqlExpr.getCommandText(), sqlExpr.getParameters());
    }

    protected SqlExpression generateInsertSqlExpression(ColumnMap columnMap, String targetTableName) {
        if (columnMap == null || columnMap.size() == 0) {
            throw new NullPointerException("columnMap:列映射对象为空或没有值");
        }

        String[] fields = new String[columnMap.size()];
        String[] values = new String[columnMap.size()];
        int i = 0;

        for (String key : columnMap.keySet()) {
            fields[i] = key;
            values[i] = " ? ";
            i++;
        }

        String commandText = String.format("INSERT INTO %1$s (%2$s) VALUES (%3$s)",
                targetTableName, StringExtension.join(",", fields), StringExtension.join(",", values));
        return new SqlExpression(commandText, columnMap.values().toArray());
    }

    protected String generateInsertSql(ColumnMap columnMap, String targetTableName) {
        if (columnMap == null || columnMap.size() == 0) {
            throw new NullPointerException("columnMap:列映射对象为空或没有值");
        }

        String[] fields = new String[columnMap.size()];
        String[] values = new String[columnMap.size()];
        int i = 0;

        for (String key : columnMap.keySet()) {
            fields[i] = key;
            values[i] = columnMap.get(key).toString().replace("'", "''");
            i++;
        }

        String commandText = String.format("INSERT INTO %1$s (%2$s) VALUES (%3$s)",
                targetTableName, StringExtension.join(",", fields), StringExtension.join(",", values));
        return commandText;
    }

    protected SqlExpression generateUpdateSqlExpression(String condition, ColumnMap columnMap, String targetTableName, Object... parameterValues) {
        if (columnMap == null || columnMap.size() == 0) {
            throw new NullPointerException("columnMap:列映射对象为空或没有值");
        }

        int initCapacity = columnMap.size() + (parameterValues == null ? 0 : parameterValues.length);
        ArrayList<Object> parameters = new ArrayList<Object>(initCapacity);
        String[] setValues = new String[columnMap.size()];
        int i = 0;

        for (String key : columnMap.keySet()) {
            setValues[i] = String.format("%s = ?", key);
            parameters.add(columnMap.get(key));
            i++;
        }

        if (parameterValues != null && parameterValues.length > 0) {
            parameters.addAll(Arrays.asList(parameterValues));
        }

        String commandText = String.format("UPDATE %1$s SET %2$s WHERE %3$s ", targetTableName,
                StringExtension.join(",", setValues), condition);
        return new SqlExpression(commandText, parameters.toArray());
    }

    protected String generateUpdateSql(String condition, ColumnMap columnMap, String targetTable, Object... parameterValues) {
        if (columnMap == null || columnMap.size() == 0) {
            throw new NullPointerException("columnMap:列映射对象为空或没有值");
        }

        int initCapacity = columnMap.size() + (parameterValues == null ? 0 : parameterValues.length);
        ArrayList<Object> parameters = new ArrayList<Object>(initCapacity);
        String[] setValues = new String[columnMap.size()];
        int i = 0;

        for (String key : columnMap.keySet()) {
            setValues[i] = String.format("%1$s = '%2$s'", key, columnMap.get(key).toString().replace("'", "''"));
            parameters.add(columnMap.get(key));
            i++;
        }

        if (parameterValues != null && parameterValues.length > 0) {
            parameters.addAll(Arrays.asList(parameterValues));
        }

        String sqlCmd = String.format("UPDATE %1$s SET %2$s WHERE %3$s ", targetTable,
                StringExtension.join(",", setValues), condition);
        return sqlCmd;
    }

    protected abstract ColumnMap getColumnMap(T entity, String... columnNames);
}
