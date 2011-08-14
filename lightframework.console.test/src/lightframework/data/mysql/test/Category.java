/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lightframework.data.mysql.test;

import lightframework.data.entities.CategoryDTO;
import java.sql.ResultSet;
import java.util.List;
import lightframework.data.DataFieldMapTable;
import lightframework.data.EntityActionListener;
import lightframework.data.mapping.MetaDataTable;
import lightframework.data.mapping.EntityMapper;
import lightframework.data.mysql.AbstractSinglePKDataAccess;

/**
 *
 * @author Tom Deng
 */
public class Category extends AbstractSinglePKDataAccess<CategoryDTO> {

    public Category(String connectionString) {
        super(CategoryDTO.ENTITYNAME, CategoryDTO.C_CategoryId, connectionString);
    }

    public Category(String tableName, String connectionString) {
        super(tableName, CategoryDTO.C_CategoryId, connectionString);
    }

    public Category(String tableName, String primaryKey, String connectionString) {
        super(tableName, primaryKey, connectionString);
    }

    @Override
    protected DataFieldMapTable getDataFieldMapTable(CategoryDTO entity, String... columnNames) {
        if (entity == null) {
            throw new NullPointerException("entity:未将对象引用到实例");
        }

        return EntityMapper.getMapTable(entity, columnNames);
    }

    @Override
    public CategoryDTO toEntity(ResultSet rs, MetaDataTable mdt, String... columnNames) {
        if (rs == null) {
            throw new NullPointerException("rs:未将对象引用到实例");
        }

        return EntityMapper.getEntity(rs, new CategoryDTO(), mdt);
    }

    public List<CategoryDTO> getCategoryById(int id) {
        String sqlCmd = "Select * from category where categoryid=" + id;
        return this.<CategoryDTO>getEntities(sqlCmd, null, new EntityActionListener<CategoryDTO>() {

            @Override
            public CategoryDTO toEntity(ResultSet rs, MetaDataTable mdt, String... columnNames) {
                return EntityMapper.getEntity(rs, new CategoryDTO(), mdt);
            }
        }, (String[]) null);
    }
}
