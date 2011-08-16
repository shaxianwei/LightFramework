/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lightframework.data.mysql;

import lightframework.data.collections.DataFieldMapTable;
import java.util.ArrayList;
import java.util.Arrays;
import lightframework.data.*;
import lightframework.data.exceptions.WhereConditionException;

/**
 *
 * @author Tom Deng
 */
public abstract class AbstractBaseDataAccess<T> extends AbstractBaseSelect<T> implements BaseDataAccess<T> {

    protected AbstractBaseDataAccess(String tableName, String connectionString) {
        super(tableName, connectionString);
    }

    @Override
    public int insert(T entity) {
        SqlExpression sqlExpr = this.generateInsertSqlExpression(this.getDataFieldMapTable(entity), this.tableName);
        return MySqlHelper.executeNonQuery(this.connectionString, sqlExpr.getCommandText(), sqlExpr.getParameters());
    }

    @Override
    public int insertWithId(T entity) {
        SqlExpression sqlExpr = this.generateInsertSqlExpression(this.getDataFieldMapTable(entity), this.tableName);
        String sqlCmd = sqlExpr.getCommandText();
        Object lastId = MySqlHelper.executeScalar(this.connectionString, sqlCmd, true, sqlExpr.getParameters());
        return Integer.valueOf(lastId.toString());
    }

    @Override
    public int  deleteAll() {
        String sqlCmd = String.format("DELETE FROM %s", this.tableName);
        return MySqlHelper.executeNonQuery(this.connectionString, sqlCmd);
    }

    @Override
    public int deleteWithCondition(String condition, Object... parameterValues) {
        if (this.containWhere(condition)) {
            throw new WhereConditionException("condition:指定的条件,不要求带SQL语句Where关键字的条件");
        }

        if (this.isNullOrEmpty(condition)) {
            return -1;
        }

        StringBuilder strSql = new StringBuilder();
        strSql.append("DELETE FROM %1$s WHERE %2$s");
        String sqlCmd = String.format(strSql.toString(), this.tableName, condition);
        return MySqlHelper.executeNonQuery(this.connectionString, sqlCmd, parameterValues);
    }

    @Override
    public void truncate() {
        String sqlCmd = String.format("TRUNCATE TABLE %s", this.tableName);
        MySqlHelper.executeNonQuery(this.connectionString, sqlCmd);
    }

    @Override
    public int updateWithCondition(T entity, String condition, Object[] parameterValues, String... columnNames) {
        if (entity == null) {
            throw new NullPointerException("dto:未将对象引用到实例");
        }

        if (this.containWhere(condition)) {
            throw new WhereConditionException("condition:指定的条件,不要求带SQL语句Where关键字的条件");
        }

        SqlExpression sqlExpr = this.generateUpdateSqlExpression(condition,
                this.getDataFieldMapTable(entity, columnNames), this.tableName, parameterValues);
        return MySqlHelper.executeNonQuery(this.connectionString, sqlExpr.getCommandText(), sqlExpr.getParameters());
    }

    protected SqlExpression generateInsertSqlExpression(DataFieldMapTable mapTable, String targetTable) {
        if (mapTable == null || mapTable.size() == 0) {
            throw new NullPointerException("mapTable:数据映射对象表为空或没有值");
        }

        String[] fields = new String[mapTable.size()];
        String[] values = new String[mapTable.size()];
        int i = 0;

        for (String key : mapTable.keySet()) {
            fields[i] = key;
            values[i] = " ? ";
            i++;
        }

        String commandText = String.format("INSERT INTO %1$s (%2$s) VALUES (%3$s)",
                targetTable, this.join(",", fields), this.join(",", values));
        return new SqlExpression(commandText, mapTable.values().toArray());
    }

    protected String generateInsertSql(DataFieldMapTable mapTable, String targetTable) {
        if (mapTable == null || mapTable.size() == 0) {
            throw new NullPointerException("mapTable:数据映射对象表为空或没有值");
        }

        String[] fields = new String[mapTable.size()];
        String[] values = new String[mapTable.size()];
        int i = 0;

        for (String key : mapTable.keySet()) {
            fields[i] = key;
            values[i] = mapTable.get(key).toString().replace("'", "''");
            i++;
        }

        String commandText = String.format("INSERT INTO %1$s (%2$s) VALUES (%3$s)",
                targetTable, this.join(",", fields), this.join(",", values));
        return commandText;
    }

    protected SqlExpression generateUpdateSqlExpression(String condition, DataFieldMapTable mapTable, String targetTable, Object... parameterValues) {
        if (mapTable == null || mapTable.size() == 0) {
            throw new NullPointerException("mapTable:数据映射对象表为空或没有值");
        }

        ArrayList<Object> parameters = new ArrayList<Object>(mapTable.size() + parameterValues.length);
        String[] setValues = new String[mapTable.size()];
        int i = 0;

        for (String key : mapTable.keySet()) {
            setValues[i] = String.format("%s = ?", key);
            parameters.add(mapTable.get(key));
            i++;
        }

        if (parameterValues != null && parameterValues.length > 0) {
            parameters.addAll(Arrays.asList(parameterValues));
        }

        String commandText = String.format("UPDATE %1$s SET %2$s WHERE %3$s ", targetTable,
                this.join(",", setValues), condition);
        return new SqlExpression(commandText, parameters.toArray());
    }

    protected String generateUpdateSql(String condition, DataFieldMapTable mapTable, String targetTable, Object... parameterValues) {
        if (mapTable == null || mapTable.size() == 0) {
            throw new NullPointerException("mapTable:数据映射对象表为空或没有值");
        }

        ArrayList<Object> parameters = new ArrayList<Object>(mapTable.size() + parameterValues.length);
        String[] setValues = new String[mapTable.size()];
        int i = 0;

        for (String key : mapTable.keySet()) {
            setValues[i] = String.format("%1$s = '%2$s'", key, mapTable.get(key).toString().replace("'", "''"));
            parameters.add(mapTable.get(key));
            i++;
        }

        if (parameterValues != null && parameterValues.length > 0) {
            parameters.addAll(Arrays.asList(parameterValues));
        }

        String sqlCmd = String.format("UPDATE %1$s SET %2$s WHERE %3$s ", targetTable,
                this.join(",", setValues), condition);
        return sqlCmd;
    }

    protected abstract DataFieldMapTable getDataFieldMapTable(T entity, String... columnNames);
}
