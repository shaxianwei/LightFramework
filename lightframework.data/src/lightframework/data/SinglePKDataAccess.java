package lightframework.data;

/**
 * 提供对数据库中单主键表进行基本CRUD操作的接口
 * @param <T> 通用类型(一般为实体类型)
 * @author Tom Deng
 */
public interface SinglePKDataAccess<T> extends SinglePKInsert<T>, SinglePKDelete<T>, SinglePKUpdate<T>, SinglePKSelect<T> {
}
