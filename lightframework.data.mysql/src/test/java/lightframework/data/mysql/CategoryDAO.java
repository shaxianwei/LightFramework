package lightframework.data.mysql;

import lightframework.data.collections.ColumnMap;
import lightframework.data.configuration.Configurationable;
import lightframework.data.mapping.EntityMapper;
import lightframework.data.mapping.MetaDataTable;
import java.sql.ResultSet;

/**
 *
 * @author Tom Deng <tom.deng@duomi.com>
 */
public class CategoryDAO extends AbstractSinglePKDataAccess<CategoryEntity> {

    public CategoryDAO(Configurationable config) {
        super(CategoryEntity.EntityName, CategoryEntity.Id, "default", config);
    }

    public CategoryDAO(String dbAlias, Configurationable config) {
        super(CategoryEntity.EntityName, CategoryEntity.Id, dbAlias, config);
    }

    public CategoryDAO(String tableName, String dbAlias, Configurationable config) {
        super(tableName, CategoryEntity.Id, dbAlias, config);
    }

    public CategoryDAO(String tableName, String primaryKey, String dbAlias, Configurationable config) {
        super(tableName, primaryKey, dbAlias, config);
    }

    @Override
    protected ColumnMap getColumnMap(CategoryEntity entity, String... columnNames) {
        if (entity == null) {
            throw new NullPointerException("entity:未将对象引用到实例");
        }

        return EntityMapper.getColumnMap(entity, columnNames);
    }

    @Override
    public CategoryEntity toEntity(ResultSet rs, MetaDataTable mdt, String... columnNames) {
        if (rs == null) {
            throw new NullPointerException("rs:未将对象引用到实例");
        }

        return EntityMapper.getEntity(rs, new CategoryEntity(), mdt);
    }
}
