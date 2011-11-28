package lightframework.data;

/**
 * 提供对数据库中单主键表一些基本查询操作的接口
 * @param <T> 通用类型(一般为实体类型)
 * @author Tom Deng
 */
public interface SinglePKSelect<T> {

    /**
     * 查询数据库,判断指定标识的记录是否存在。
     * @param keyValue 主键ID值
     * @return 存在则返回true,否则还false
     */
    boolean isExistKey(int keyValue);

    /**
     * 查询数据库,判断指定标识的记录是否存在。
     * @param keyValues 主键ID值集合
     * @return 存在则返回true,否则还false
     */
    boolean isExistKey(int... keyValues);

    /**
     * 查询数据库,判断指定标识的记录是否存在。
     * @param keyValue 主键ID值
     * @return 存在则返回true,否则还false
     */
    boolean isExistKey(String keyValue);

    /**
     * 查询数据库,判断指定标识的记录是否存在。
     * @param keyValues 主键ID值集合
     * @return 存在则返回true,否则还false
     */
    boolean isExistKey(String... keyValues);

    /**
     * 查询数据库,判断指定标识的记录是否存在，使用 IN 匹配
     * @param keyValues 主键ID值 以,号分割
     * @return 存在则返回true,否则还false
     */
    boolean isExistKeyIn(String keyValues);

    /**
     * 从数据库中获取指定主键值的实体对象(返回值需要判断是否为null)。
     * @param keyValue 主键ID值
     * @param columnNames 需要从数据库中筛选的列名
     * @return 实体对象
     */
    T select(String keyValue, String... columnNames);

    /**
     * 从数据库中获取指定主键值的实体对象(返回值需要判断是否为null)。
     * @param keyValue 主键ID值
     * @param columnNames 需要从数据库中筛选的列名
     * @return 实体对象
     */
    T select(int keyValue, String... columnNames);

    /**
     * 从数据库中按表的ID列值分页大小进行获取实体对象集合(返回值不需要判断是否为null),默认返回按表中的标识字段降序排序的集合。
     * @param pageSize 分页大小，即分页显示多少条记录
     * @param pageIndex 当前页码
     * @param condition 指定的条件,要求带SQL语句Where关键字的条件,如果不带Where关键字则条件无效
     * @param orderByColumnName 排序字段名称，不要求带ORDER BY关键字,只要指定排序字段名称即可
     * @param sortType SQL语句排序类型
     * @param parameterValues SQL参数对应值的集合,如果条件字符串中含有参数标记,则必须设置该数组的值
     * @param columnNames 需要从数据库中筛选的列名
     * @return 当前分页的所有取实体对象集合
     */
    PageData<T> selectWithPageSizeByIdentity(int pageSize, int pageIndex, String condition, String orderByColumnName, SortTypeEnum sortType, Object[] parameterValues, String... columnNames);

    /**
     * 获取数据库中表的最大ID值(没有记录的时候返回0)。
     * @return 最大ID值
     */
    int getMaxID();

    /**
     *  获取数据库中该对象指定属性的最大值(没有记录的时候返回0)。
     * @param fieldName 表中的字段(列)名称,字段的值必需是整型数据
     * @param fromBase 从(2,8,10,16)进制的整型转换成10进制
     * @param condition 要求带SQL语句Where关键字的条件
     * @param parameterValues SQL参数对应值的集合,如果条件字符串中含有参数标记,则必须设置该数组的值
     * @return 指定属性的最大值
     */
    int getMaxValue(String fieldName, int fromBase, String condition, Object... parameterValues);
}
