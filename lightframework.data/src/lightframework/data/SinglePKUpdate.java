package lightframework.data;

/**
 * 提供对数据库中单主键表一些基本修改操作的接口
 * @param <T> 通用类型(一般为实体类型)
 * @author Tom Deng
 */
public interface SinglePKUpdate<T> {

    /**
     * 更新数据库表中指定主键值的记录
     * @param entity
     * @param columnNames 指定数据库表中需要更新的列名集合
     * @return 
     */
    int update(T entity, String... columnNames);

    /**
     * 更新数据库表中指定主键值的记录
     * @param entity 实体对象
     * @param id 表中主键的值
     * @param columnNames 指定数据库表中需要更新的列名集合
     * @return 返回影响记录的行数,-1表示操作失败,大于-1表示成功
     */
    int update(T entity, int id, String... columnNames);

    /**
     * 更新数据库表中指定主键值的记录
     * @param entity 实体对象
     * @param id 表中主键的值
     * @param columnNames 指定数据库表中需要更新的列名集合
     * @return 返回影响记录的行数,-1表示操作失败,大于-1表示成功
     */
    int update(T entity, String id, String... columnNames);

    /**
     *  更新一条数据 条件为符合指定列的值 使用 IN 匹配
     *  提示：In的条件仅用于主键列
     * @param entity 实体对象
     * @param keyValues 主键ID值 以,号分割
     * @param columnNames 指定数据库表中需要更新的列名集合
     * @return 返回影响记录的行数,-1表示操作失败,大于-1表示成功
     */
    int updateIn(T entity, String keyValues, String... columnNames);
}
