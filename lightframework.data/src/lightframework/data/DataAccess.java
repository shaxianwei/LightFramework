package lightframework.data;

/**
 * 提供对数据库进行基本CRUD操作的接口。
 * @param <T> 通用类型(一般为实体类型)
 * @author Tom Deng
 */
public interface DataAccess<T> extends Insert<T>, Delete<T>, Update<T>, Select<T> {
}
