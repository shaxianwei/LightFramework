package lightframework.data;

/**
 * 提供对数据库一些基本修改操作的接口
 * @param <T> 通用类型(一般为实体类型)
 * @author Tom Deng
 */
public interface Update<T> {

    /**
     * 更新数据库表中的记录
     * @param entity 实体对象
     * @param condition 不带Where的更新条件
     * @param parameterValues SQL参数对应值的集合,如果条件字符串中含有参数标记,则必须设置该数组的值
     * @param columnNames 指定数据库表中需要更新的列名集合
     * @return  返回影响记录的行数,-1表示操作失败,大于-1表示成功
     */
    int updateWithCondition(T entity, String condition, Object[] parameterValues, String... columnNames);
}
