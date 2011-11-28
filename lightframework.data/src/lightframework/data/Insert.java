package lightframework.data;

/**
 * 提供对数据库一些基本增加操作的接口
 * @param <T> 通用类型(一般为实体类型)
 * @author Tom Deng
 */
public interface Insert<T> {

    /**
     * 向数据库中添加一条记录
     * @param entity 实体对象
     * @return 返回影响记录的行数,-1表示操作失败,大于-1表示成功
     */
    int insert(T entity);

    /**
     * 向数据库中添加一条记录，并返回插入记录的ID值。
     * @param entity 实体对象
     * @return 插入记录的数据库自增标识
     */
    int insertWithId(T entity);
}
