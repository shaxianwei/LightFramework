package lightframework.data;

import java.sql.ResultSet;
import lightframework.data.mapping.MetaDataTable;

/**
 * 提供对数据库的ResultSet对象转换为Entity对象的接口。
 * @param <T> 通用类型(一般为实体类型)
 * @author Tom Deng
 */
public interface ResultSetHandler<T> {
    
    /**
     * 把一条数据库记录集转换成对应的实体对象。
     * @param rs ResultSet 记录集对象
     * @param mdt 数据库(表)元数据映射对象
     * @param columnNames 需要过滤的列名集合
     * @return 对应的实体对象
     */
   T toEntity(ResultSet rs,MetaDataTable mdt,String... columnNames);
}
