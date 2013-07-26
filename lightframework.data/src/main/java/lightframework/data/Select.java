package lightframework.data;

import java.util.List;
import lightframework.data.configuration.Configurationable;

/**
 * 提供对数据库一些基本查询操作的接口。
 * @author Tom Deng
 * @param <T> 通用类型(一般为实体类型)
 */
public interface Select<T> {

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
     * 获取当前的数据库唯一别名
     * @return 
     */
    String getDBAlias();

    /**
     * 设置当前的数据库唯一别名
     * @param connectionString 
     */
    void setDBAlias(String dbAlias);

    /**
     * 获取配置对象
     * @return 
     */
    Configurationable getConfiguration();

    /**
     * 设置配置对象
     * @param config 配置对象s
     */
    void setConfiguration(Configurationable config);
    
    /**
     * 查询数据库,判断指定条件的记录是否存在。
     * @param condition 指定的条件,不需要带SQL语句的Where关键字
     * @param parameterValues SQL参数对应值的集合,如果条件字符串中含有参数标记,则必须设置该数组的值
     * @return 存在则返回true,否则为false。
     */
    Boolean isExistWithCondition(String condition, Object... parameterValues);

    /**
     * 从数据库中获取所有的实体对象集合(返回值不需判断是否为null)。
     * @return 实体对象集合
     */
    List<T> select();

    /**
     * 从数据库中获取所有的实体对象集合(返回值不需判断是否为null)。
     * @param columnNames 需要从数据库中筛选的列名
     * @return 实体对象集合
     */
    List<T> select(String... columnNames);

    /**
     * 从数据库中获取满足指定条件的实体对象集合(返回值不需判断是否为null)。
     * @param orderByColumnName
     * @param sortType
     * @param columnNames
     * @return 
     */
    List<T> select(String orderByColumnName, SortTypeEnum sortType, String... columnNames);

    /**
     * 从数据库中获取满足指定条件的实体对象集合(返回值不需判断是否为null)。
     * @param condition 指定的条件,要求带SQL语句Where关键字的条件
     * @param columnNames 需要从数据库中筛选的列名
     * @return 指定条件的表中的实体对象集合
     */
    List<T> selectWithCondition(String condition, String... columnNames);

    /**
     * 从数据库中获取满足指定条件的实体对象集合(返回值不需判断是否为null)。
     * @param condition 指定的条件,要求带SQL语句Where关键字的条件
     * @param parameterValues SQL参数对应值的集合,如果条件字符串中含有参数标记,则必须设置该数组的值
     * @param columnNames 需要从数据库中筛选的列名
     * @return 实体对象集合
     */
    List<T> selectWithCondition(String condition, Object[] parameterValues, String... columnNames);

    /**
     * 从数据库中获取满足指定条件的实体对象集合(返回值不需判断是否为null)。
     * @param condition 指定的条件,要求带SQL语句Where关键字的条件
     * @param orderByColumnName 排序字段名称，不要求带ORDER BY关键字,只要指定排序字段名称即可
     * @param sortType SQL语句排序类型
     * @param parameterValues SQL参数对应值的集合,如果条件字符串中含有参数标记,则必须设置该数组的值
     * @param columnNames 需要从数据库中筛选的列名
     * @return 实体对象集合
     */
    List<T> selectWithCondition(String condition, String orderByColumnName, SortTypeEnum sortType, Object[] parameterValues, String... columnNames);

    /**
     * 从数据库中获取满足指定条件的前N项实体对象集合(返回值不需判断是否为null)。
     * @param topN 表中的前N条记录
     * @param condition 指定的条件,要求带SQL语句Where关键字的条件
     * @param parameterValues SQL参数对应值的集合,如果条件字符串中含有参数标记,则必须设置该数组的值
     * @param columnNames 需要从数据库中筛选的列名
     * @return 实体对象集合
     */
    List<T> selectTopN(int topN, String condition, Object[] parameterValues, String... columnNames);

    /**
     * 从数据库中获取满足指定条件的前N项实体对象集合(返回值不需判断是否为null)。
     * @param topN 表中的前N条记录
     * @param condition 指定的条件,要求带SQL语句Where关键字的条件
     * @param orderByColumnName 排序字段名称，不要求带ORDER BY关键字,只要指定排序字段名称即可
     * @param sortType SQL语句排序类型
     * @param parameterValues SQL参数对应值的集合,如果条件字符串中含有参数标记,则必须设置该数组的值
     * @param columnNames 需要从数据库中筛选的列名
     * @return 实体对象集合
     */
    List<T> selectTopN(int topN, String condition, String orderByColumnName, SortTypeEnum sortType, Object[] parameterValues, String... columnNames);

    /**
     * 从数据库中获取一条满足指定条件的实体对象集合(返回值需要判断是否为null)。
     * @param condition 指定的条件,要求带SQL语句Where关键字的条件
     * @param parameterValues SQL参数对应值的集合,如果条件字符串中含有参数标记,则必须设置该数组的值
     * @param columnNames 需要从数据库中筛选的列名
     * @return 实体对象或null
     */
    T selectOne(String condition, Object[] parameterValues, String... columnNames);

    /**
     * 从数据库中按分页大小进行获取实体对象集合(返回值不需判断是否为null),默认返回按表中的字段降序排序的集合,
     * notinColumnName(指定的筛选列名称),该参数为必须指定，且为当前表中合法的列名称。如果未指定列名称，该方法将返回null。
     * @param pageSize 分页大小，即分页显示多少条记录
     * @param pageIndex 当前页码
     * @param condition 指定的条件,要求带SQL语句Where关键字的条件,如果不带Where关键字则条件无效
     * @param notinColumnName 指定的筛选列名称,该参数为必须指定，且为当前表中合法的列名称。如果未指定列名称，该方法将返回null
     * @param orderByColumnName 排序字段名称，不要求带ORDER BY关键字,只要指定排序字段名称即可
     * @param sortType SQL语句排序类型
     * @param parameterValues SQL参数对应值的集合,如果条件字符串中含有参数标记,则必须设置该数组的值
     * @param columnNames 需要从数据库中筛选的列名
     * @return 当前分页的所有取实体对象集合
     */
    PageData<T> selectWithPageSizeByNotIn(int pageSize, int pageIndex, String condition, String notinColumnName, String orderByColumnName, SortTypeEnum sortType, Object[] parameterValues, String... columnNames);

    /**
     * 利用数据库表的limit关键字属性对数据进行分页查询的方法(返回值不需判断是否为null),该方法只能支持SqlServer2005及MySQL5以上版的数据库
     * @param pageSize 分页大小，即分页显示多少条记录
     * @param pageIndex 当前页码
     * @param condition 指定的条件,要求带SQL语句Where关键字的条件,如果不带Where关键字则条件无效
     * @param orderByColumnName 排序字段名称，不要求带ORDER BY关键字,只要指定排序字段名称即可
     * @param sortType SQL语句排序类型
     * @param parameterValues SQL参数对应值的集合,如果条件字符串中含有参数标记,则必须设置该数组的值
     * @param columnNames 需要从数据库中筛选的列名
     * @return 当前分页的所有取实体对象集合
     */
    PageData<T> selectWithPageSizeByRowId(int pageSize, int pageIndex, String condition, String orderByColumnName, SortTypeEnum sortType, Object[] parameterValues, String... columnNames);

    /**
     * 利用存储过程对表中数据进行分页查询的方法(返回值不需判断是否为null)。
     * @param pageSize 分页大小，即分页显示多少条记录
     * @param pageIndex 当前页码
     * @param condition 指定的条件,要求带SQL语句Where关键字的条件,如果不带Where关键字则条件无效
     * @param orderByColumnName 排序字段名称，不要求带ORDER BY关键字,只要指定排序字段名称即可
     * @param sortType SQL语句排序类型
     * @param columnNames 需要从数据库中筛选的列名
     * @return 当前分页的所有取实体对象集合
     */
    PageData<T> selectWithPageSizeByStoredProcedure(int pageSize, int pageIndex, String condition, String orderByColumnName, SortTypeEnum sortType, String... columnNames);

    /**
     * 获取数据库中表的记录总数
     * @return 表的记录总数
     */
    int count();

    /**
     * 获取数据库表中指定条件的记录总数
     * @param condition 要求带SQL语句Where关键字的条件，如果不带Where关键字该方法将对表中所有记录执行操作
     * @param parameterValues SQL参数对应值的集合,如果条件字符串中含有参数标记,则必须设置该数组的值
     * @return 指定条件的记录总数
     */
    int count(String condition, Object... parameterValues);
}
