package lightframework.data;

/**
 * 提供对数据库视图进行数据访问的一些基本操作接口
 * @param <T> 通用类型(一般为实体类型)
 * @author Tom Deng
 */
public interface ViewDataAccess<T> extends Select<T> {
}
