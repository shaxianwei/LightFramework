package lightframework.data;

/**
 * 提供对数据库中单主键表一些基本删除操作的接口
 * @param <T> 通用类型(一般为实体类型)
 * @author Tom Deng
 */
public interface SinglePKDelete<T> {

    /**
     * 根据指定记录的ID,从数据库中删除指定记录(用于整型主键)。
     * @param keyValue 指定记录的ID值
     * @return 返回影响记录的行数,-1表示操作失败,大于-1表示成功
     */
    int delete(int keyValue);

    /**
     * 根据指定记录的ID,从数据库中删除指定记录(用于整型主键)。
     * @param keyValue 指定记录的ID值
     * @return 返回影响记录的行数,-1表示操作失败,大于-1表示成功
     */
    int delete(String keyValue);

    /**
     * 从数据库中删除一个或多个指定标识的记录。
     * @param keyValues 记录标识数组
     * @return 返回影响记录的行数,-1表示操作失败,大于-1表示成功
     */
    int delete(int... keyValues);

    /**
     * 从数据库中删除一个或多个指定标识的记录。
     * @param keyValues 记录标识数组
     * @return 返回影响记录的行数,-1表示操作失败,大于-1表示成功
     */
    int delete(String... keyValues);

    /**
     * 删除多条数据 条件为符合多个指定列的值 使用 IN 匹配
     * @param keyValues 主键ID值集合 以,号分割
     * @return 返回影响记录的行数,-1表示操作失败,大于-1表示成功
     */
    int deleteIn(String keyValues);
}
