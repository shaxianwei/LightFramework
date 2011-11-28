package lightframework.data.mysql;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.sql.Connection;

import lightframework.data.*;
import lightframework.data.exceptions.*;
import lightframework.data.mapping.MetaDataTable;
import lightframework.data.configuration.Configurationable;
import lightframework.data.mysql.connection.ConnectionFactory;

/**
 * 
 * @author Tom Deng
 * @param <T> 
 */
public abstract class AbstractSelect<T> implements Select<T>, ResultSetHandler<T> {

    protected String tableName = "";
    protected String dbAlias = "";
    protected Configurationable configuration;

    protected AbstractSelect(String tableName, String dbAlias, Configurationable config) {
        this.tableName = tableName;
        this.dbAlias = dbAlias;
        this.configuration = config;
    }

    @Override
    public String getDBAlias() {
        return this.dbAlias;
    }

    @Override
    public void setDBAlias(String dbAlias) {
        this.dbAlias = dbAlias;
    }

    @Override
    public String getTableName() {
        return this.tableName;
    }

    @Override
    public void setTableName(String name) {
        this.tableName = name;
    }

    @Override
    public Configurationable getConfiguration() {
        return this.configuration;
    }

    @Override
    public void setConfiguration(Configurationable config) {
        this.configuration = config;
    }

    @Override
    public abstract T toEntity(ResultSet rs, MetaDataTable mdt, String... columnNames);

    @Override
    public int count() {
        return this.count("");
    }

    @Override
    public int count(String condition, Object... parameterValues) {
        if (!this.containWhere(condition)) {
            throw new WhereConditionException("condition：指定的条件,要求带SQL语句Where关键字的条件");
        }

        StringBuilder strSql = new StringBuilder();
        strSql.append("SELECT COUNT(0) AS TotalCount FROM %1$s %2$s");
        String sqlCmd = String.format(strSql.toString(), this.getTableName(), condition);
        Object result = MySqlHelper.executeScalar(this.getConnection(), sqlCmd, parameterValues);
        return Integer.valueOf(result.toString()).intValue();
    }

    @Override
    public Boolean isExistWithCondition(String condition, Object... parameterValues) {
        if (this.containWhere(condition)) {
            throw new WhereConditionException("condition：指定的条件,不要求带SQL语句Where关键字的条件");
        }

        StringBuilder strSql = new StringBuilder();
        strSql.append("SELECT COUNT(0) FROM %1$s ");
        strSql.append("WHERE %2$s ");
        String sqlCmd = String.format(strSql.toString(), this.tableName, condition);
        Object result = MySqlHelper.executeScalar(this.getConnection(), sqlCmd, parameterValues);
        return Integer.valueOf(result.toString()) > 0;
    }

    @Override
    public List<T> select() {
        return this.selectWithCondition("");
    }

    @Override
    public List<T> select(String... columnNames) {
        return this.selectWithCondition("", null, columnNames);
    }

    @Override
    public List<T> select(String orderByColumnName, SortTypeEnum sortType, String... columnNames) {
        return this.selectWithCondition("", orderByColumnName, sortType, null, columnNames);
    }

    @Override
    public T selectOne(String condition, Object[] parameterValues, String... columnNames) {
        List<T> list = this.selectWithCondition(condition, parameterValues, columnNames);
        return (list == null || list.isEmpty()) ? null : list.get(0);
    }

    @Override
    public List<T> selectTopN(int topN, String condition, Object[] parameterValues, String... columnNames) {
        return this.selectTopN(topN, condition, "", SortTypeEnum.ASC, parameterValues, columnNames);
    }

    @Override
    public List<T> selectTopN(int topN, String condition, String orderByColumnName, SortTypeEnum sortType, Object[] parameterValues, String... columnNames) {
        //如果topN的值不合法.
        if (topN < 1) {
            return this.selectWithCondition(condition, columnNames);
        }

        if (!this.containWhere(condition)) {
            throw new WhereConditionException("condition：指定的条件,要求带SQL语句Where关键字的条件");
        }

        //获取筛选列
        String columns = this.getColumns(columnNames);
        String sqlCmd = String.format("SELECT %1$s FROM %2$s %3$s %4$s LIMIT %5$s", columns, this.tableName, condition,
                this.isNullOrEmpty(orderByColumnName)
                ? ""
                : String.format("ORDER BY %1$s %2$s ", orderByColumnName, sortType.toString()),
                topN);

        return this.getEntities(sqlCmd, parameterValues, columnNames);
    }

    @Override
    public List<T> selectWithCondition(String condition, String... columnNames) {
        return this.selectWithCondition(condition, null, columnNames);
    }

    @Override
    public List<T> selectWithCondition(String condition, Object[] parameterValues, String... columnNames) {
        return this.selectWithCondition(condition, "", SortTypeEnum.ASC, parameterValues, columnNames);
    }

    @Override
    public List<T> selectWithCondition(String condition, String orderByColumnName, SortTypeEnum sortType, Object[] parameterValues, String... columnNames) {
        if (!this.containWhere(condition)) {
            throw new WhereConditionException("condition：指定的条件,要求带SQL语句Where关键字的条件");
        }

        //获取筛选列
        String columns = this.getColumns(columnNames);
        String sqlCmd = String.format("SELECT %1$s FROM %2$s %3$s %4$s", columns, this.tableName, condition,
                this.isNullOrEmpty(orderByColumnName)
                ? ""
                : String.format("ORDER BY %1$s %2$s", orderByColumnName, sortType.toString()));

        return this.getEntities(sqlCmd, parameterValues, columnNames);
    }

    @Override
    public PageData<T> selectWithPageSizeByNotIn(int pageSize, int pageIndex, String condition, String notinColumnName, String orderByColumnName, SortTypeEnum sortType, Object[] parameterValues, String... columnNames) {
        if (this.isNullOrEmpty(notinColumnName)) {
            throw new NullPointerException("notinColumnName:请指定的筛选列名称,该参数为必须指定，且为当前表中合法的列名称");
        }

        if (!this.containWhere(condition)) {
            throw new WhereConditionException("condition：指定的条件,要求带SQL语句Where关键字的条件");
        }

        //设置默认分页参数
        if (pageSize < 1) {
            pageSize = 10;
        }
        if (pageIndex < 0) {
            pageIndex = 0;
        }

        //临时表大小
        int tempTableSize = pageIndex * pageSize;
        //获取筛选列
        String columns = this.getColumns(columnNames);

        //设置第五个格式化参数为去掉Where字符串
        String trimWhere = condition.isEmpty()
                ? ""
                : String.format("AND %s", condition.trim().substring(5));

        //设置OrderBy参数
        String orderBy = this.isNullOrEmpty(orderByColumnName)
                ? String.format("%1$s %2$s", notinColumnName, sortType.toString())
                : String.format("%1$s %2$s", orderByColumnName, sortType.toString());

        //分页获取数据库记录集的SQL语句
        StringBuilder sqlFormat = new StringBuilder();
        sqlFormat.append("SELECT %1$s ");
        sqlFormat.append("FROM %2$s ");
        sqlFormat.append("WHERE %3$s NOT IN ");
        sqlFormat.append("(SELECT %3$s FROM %2$s %5$s ORDER BY %7$s LIMIT %4$s) ");
        sqlFormat.append("%6$s ORDER BY %7$s LIMIT %8$s");

        String sqlCmd = String.format(sqlFormat.toString(), columns, this.tableName,
                notinColumnName, tempTableSize, condition, trimWhere, orderBy, pageSize);
        return this.getPageData(condition, parameterValues, sqlCmd, columnNames);
    }

    @Override
    public PageData<T> selectWithPageSizeByRowId(int pageSize, int pageIndex, String condition, String orderByColumnName, SortTypeEnum sortType, Object[] parameterValues, String... columnNames) {
        if (this.isNullOrEmpty(orderByColumnName)) {
            throw new NullPointerException("orderByColumnName:排序字段名称，不要求带ORDER BY关键字,只要指定排序字段名称即可");
        }

        if (!this.containWhere(condition)) {
            throw new WhereConditionException("condition：指定的条件,不要求带SQL语句Where关键字的条件");
        }

        //设置默认分页参数
        if (pageSize < 1) {
            pageSize = 10;
        }
        if (pageIndex < 0) {
            pageIndex = 0;
        }

        //设置分页起点与终点
        int startRowId = (pageIndex - 1) * pageSize;
        //获取筛选列
        String columns = this.getColumns(columnNames);

        //分页获取数据库记录集的SQL语句
        StringBuilder sqlFormat = new StringBuilder();
        sqlFormat.append("SELECT %1$s ");
        sqlFormat.append("FROM %2$s ");
        sqlFormat.append("%3$s ORDER BY %4$s %5$s LIMIT %6$s,%7$s ");

        String sqlCmd = String.format(sqlFormat.toString(), columns, this.tableName, condition, orderByColumnName, sortType.toString(), startRowId, pageSize);
        return this.getPageData(condition, parameterValues, sqlCmd, columnNames);
    }

    @Override
    public PageData<T> selectWithPageSizeByStoredProcedure(int pageSize, int pageIndex, String condition, String orderByColumnName, SortTypeEnum sortType, String... columnNames) {
        if (this.containWhere(condition)) {
            throw new WhereConditionException("condition:指定的条件,要求不带SQL语句Where关键字的条件");
        }

        return null;
    }

    protected PageData<T> getPageData(String condition, Object[] parameterValues, String sqlCmd, String[] columnNames) {
        PageData<T> pageData = new PageData<T>();
        pageData.setRecordSet(this.getEntities(sqlCmd, parameterValues, columnNames));
        pageData.setCount(this.count(condition, parameterValues));
        return pageData;
    }

    protected List<T> getEntities(String sqlCmd, String... columnNames) {
        return this.getEntities(sqlCmd, null, columnNames);
    }

    protected List<T> getEntities(String sqlCmd, Object[] parameterValues, String... columnNames) {
        return this.<T>getEntities(sqlCmd, parameterValues, this, columnNames);
    }

    protected <TEntity> List<TEntity> getEntities(String sqlCmd, Object[] parameterValues, ResultSetHandler<TEntity> rsh, String... columnNames) {
        ArrayList<TEntity> entities = new ArrayList<TEntity>();
        ResultSet rs = null;
        Connection connection = null;

        try {
            MetaDataTable mdt = new MetaDataTable(this.getParameterizedType(rsh.getClass()), this.tableName);
            connection = getConnection();
            rs = MySqlHelper.executeReader(connection, sqlCmd, parameterValues);
            while (rs.next()) {
                entities.add(rsh.toEntity(rs, mdt, columnNames));
            }
        } catch (Exception ex) {
            String message = String.format("[SQL]:{%1$s},[Exception]:{%2$s}", sqlCmd, ex.toString());
            throw new RuntimeException(message);
        } finally {
            MySqlHelper.close(rs);
            MySqlHelper.close(connection);
        }

        return entities;
    }

    protected Connection getConnection() {
        return ConnectionFactory.creator(this.dbAlias, this.configuration);
    }

    protected boolean containWhere(String condition) {
        return (this.isNullOrEmpty(condition)
                || (!this.isNullOrEmpty(condition)
                && condition.trim().toUpperCase().startsWith("WHERE")));
    }

    protected String getColumns(String... columnNames) {
        if (columnNames == null
                || columnNames.length == 0
                || columnNames[0] == null) {
            return "*";
        }

        return this.join(",", columnNames);
    }

    protected boolean isNullOrEmpty(String s) {
        return (s == null || s.equals("") || s.trim().isEmpty());
    }

    protected <T> String join(String separator, T... values) {
        if (values == null) {
            throw new NullPointerException("values");
        }
        if ((values.length == 0) || (values[0] == null)) {
            return "";
        }
        if (separator == null) {
            separator = "";
        }

        StringBuilder builder = new StringBuilder();
        builder.append(values[0]);
        for (int i = 1; i < values.length; i++) {
            builder.append(",");
            if (values[i] != null) {
                builder.append(values[i]);
            }
        }

        return builder.toString();
    }

    private Class<?> getParameterizedType(Class<?> handlerType) {
        Class<?> parameterizedType = null;
        if (handlerType.isAnonymousClass()) {
            parameterizedType = (Class<?>) ((ParameterizedType) handlerType.getGenericInterfaces()[0]).getActualTypeArguments()[0];
        } else {
            parameterizedType = (Class<?>) ((ParameterizedType) handlerType.getGenericSuperclass()).getActualTypeArguments()[0];
        }
        return parameterizedType;
    }
}
