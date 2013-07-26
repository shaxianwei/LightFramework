package lightframework.data;

/**
 * 提供对数据库一些基本删除操作的接口
 * @param <T>  通用类型(一般为实体类型)
 * @author Tom Deng
 */
public interface Delete<T> {

    /** 
     * 根据指定条件,从数据库中删除记录 
     * @param condition 删除记录的条件语句,不需要带SQL语句的Where关键字
     * @param parameterValues SQL参数对应值的集合,如果条件字符串中含有参数标记,则必须设置该数组的值
     * @return 返回影响记录的行数,-1表示操作失败,大于-1表示成功
     */
    int deleteWithCondition(String condition, Object... parameterValues);

    /**
     * 清空表中的所有数据
     * @return 返回影响记录的行数,-1表示操作失败,大于-1表示成功
     */
    int deleteAll();

    /**
     * 清空表中的所有数据,且不记录数据库日志(调用truncate语句)。
     */
    void truncate();
}
