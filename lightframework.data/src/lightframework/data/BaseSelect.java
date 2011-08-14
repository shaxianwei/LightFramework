/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lightframework.data;

import java.util.List;

/**
 * BaseSelect提供对数据库一些基本查询操作的接口。
 * @author Tom Deng
 * @param <T> 通用类型
 */
public interface BaseSelect<T> {

    /**
     * 获取初始化的对象表名
     * @return 初始化的对象表名
     */
    String getTableName();

    /**
     * 设置初始化的对象表名
     * @param name
     */
    void setTableName(String name);

    /**
     * 
     * @return 
     */
    String getConnectionString();

    /**
     * 
     * @param connstr 
     */
    void setConnectionString(String connectionString);

    /**
     * 
     * @param condition
     * @param parameterValues
     * @return 
     */
    Boolean isExistWithCondition(String condition, Object... parameterValues)  ;

    /**
     * 
     * @return 
     */
    List<T> select();

    /**
     * 
     * @param columnNames
     * @return 
     */
    List<T> select(String... columnNames);

    /**
     * 
     * @param orderByColumnName
     * @param sortType
     * @param columnNames
     * @return 
     */
    List<T> select(String orderByColumnName, SortTypeEnum sortType, String... columnNames);

    /**
     * 
     * @param condition
     * @param columnNames
     * @return 
     */
    List<T> selectWithCondition(String condition, String... columnNames)  ;

    /**
     * 
     * @param condition
     * @param parameterValues
     * @param columnNames
     * @return 
     */
    List<T> selectWithCondition(String condition, Object[] parameterValues, String... columnNames)  ;

    /**
     * 
     * @param condition
     * @param orderByColumnName
     * @param sortType
     * @param parameterValues
     * @param columnNames
     * @return 
     */
    List<T> selectWithCondition(String condition, String orderByColumnName, SortTypeEnum sortType, Object[] parameterValues, String... columnNames)   ;

    /**
     * 
     * @param topN
     * @param condition
     * @param parameterValues
     * @param columnNames
     * @return 
     */
    List<T> selectTopN(int topN, String condition, Object[] parameterValues, String... columnNames)  ;

    /**
     * 
     * @param topN
     * @param condition
     * @param orderByColumnName
     * @param sortType
     * @param parameterValues
     * @param columnNames
     * @return 
     */
    List<T> selectTopN(int topN, String condition, String orderByColumnName, SortTypeEnum sortType, Object[] parameterValues, String... columnNames)  ;

    /**
     * 
     * @param condition
     * @param parameterValues
     * @param columnNames
     * @return 
     */
    T selectOne(String condition, Object[] parameterValues, String... columnNames)  ;

    /**
     * 
     * @param pageSize
     * @param pageIndex
     * @param condition
     * @param notinColumnName
     * @param orderby
     * @param sortType
     * @param parameterValues
     * @param columnNames
     * @return 
     */
    PageData<T> selectWithPageSizeByNotIn(int pageSize, int pageIndex, String condition, String notinColumnName, String orderByColumnName, SortTypeEnum sortType, Object[] parameterValues, String... columnNames) ;

    /**
     * 
     * @param pageSize
     * @param pageIndex
     * @param condition
     * @param orderby
     * @param sortType
     * @param parameterValues
     * @param columnNames
     * @return 
     */
    PageData<T> selectWithPageSizeByRowId(int pageSize, int pageIndex, String condition, String orderByColumnName, SortTypeEnum sortType, Object[] parameterValues, String... columnNames)  ;

    /**
     * 
     * @param pageSize
     * @param pageIndex
     * @param condition
     * @param orderby
     * @param sortType
     * @param columnNames
     * @return 
     */
    PageData<T> selectWithPageSizeByStoredProcedure(int pageSize, int pageIndex, String condition, String orderByColumnName, SortTypeEnum sortType, String... columnNames)  ;

    /**
     * 
     * @return 
     */
    int count();

    /**
     * 
     * @param condition
     * @param parameterValues
     * @return 
     */
    int count(String condition, Object... parameterValues);
}
