/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lightframework.data.mapping;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import lightframework.data.collections.DataFieldMapTable;

/**
 *
 * @author Tom Deng
 */
public final class EntityMapper {

    public static <TEntity> TEntity getEntity(ResultSet rs, TEntity entity) {
        return getEntity(rs, entity, "");
    }

    public static <TEntity> TEntity getEntity(ResultSet rs, TEntity entity, String entityName) {
        MetaDataTable metaTable = new MetaDataTable(entity.getClass(), entityName);
        return getEntity(rs, entity, metaTable);
    }

    public static <TEntity> TEntity getEntity(ResultSet rs, TEntity entity, MetaDataTable metaDataTable) {
        try {
            ResultSetMetaData rsMetaData = rs.getMetaData();
            for (int i = 1; i <= rsMetaData.getColumnCount(); i++) {
                String colName = rsMetaData.getColumnName(i).toLowerCase();
                if (!metaDataTable.getMetaDataColumns().containsKey(colName)) {
                    continue;
                }

                MetaDataColumn metaColumn = metaDataTable.getMetaDataColumns().get(colName);
                Object value = rs.getObject(i);
                metaColumn.getField().set(entity, value);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } catch (IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }

        return entity;
    }

    public static <TEntity> DataFieldMapTable getMapTable(TEntity entity, String... columnNames) {
        return getMapTable(entity, "", columnNames);
    }

    public static <TEntity> DataFieldMapTable getMapTable(TEntity entity, String entityName, String... columnNames) {
        MetaDataTable metaDataTable = new MetaDataTable(entity.getClass(), entityName);

        if (columnNames != null && columnNames.length > 0) {
            return getMapTableByColumnNames(entity, metaDataTable, columnNames);
        }

        DataFieldMapTable mapTable = new DataFieldMapTable(metaDataTable.getMetaDataColumns().size());
        for (String key : metaDataTable.getMetaDataColumns().keySet()) {
            MetaDataColumn metaColumn = metaDataTable.getMetaDataColumns().get(key);
            if (metaColumn.getColumnAnnotation().isIdentity()) {
                continue;
            }
            mapTable.put(metaColumn.getName(), getColumnValue(metaColumn, entity));
        }

        return mapTable;
    }

    private static <TEntity> DataFieldMapTable getMapTableByColumnNames(TEntity entity, MetaDataTable metaDataTable, String... columnNames) {
        DataFieldMapTable mapTable = new DataFieldMapTable(columnNames.length);
        HashMap<String, MetaDataColumn> metaColumns = metaDataTable.getMetaDataColumns();

        for (String columnName : columnNames) {
            String colName = columnName.trim().toLowerCase();
            if (!metaColumns.containsKey(colName) || metaColumns.get(colName).getColumnAnnotation().isIdentity()) {
                continue;
            }

            MetaDataColumn metaColumn = metaColumns.get(colName);
            mapTable.put(metaColumn.getName(), getColumnValue(metaColumn, entity));
        }

        return mapTable;
    }

    private static <TEntity> Object getColumnValue(MetaDataColumn metaColumn, TEntity entity) {
        try {
            return metaColumn.getField().get(entity);
        } catch (IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }
    }
}
