package lightframework.data.mapping;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;

import lightframework.data.collections.ColumnMap;

/**
 * 实体映射器类,实现把实体转换成对应的数据库字段的相关操作.
 * @author Tom Deng
 */
public final class EntityMapper {

    private EntityMapper() {
    }

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
                String columnDataTypeName = metaColumn.getField().getType().getSimpleName();
                Object value = getColumnValue(rs, columnDataTypeName, i);
                metaColumn.getField().set(entity, value);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } catch (IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }

        return entity;
    }

    public static <TEntity> ColumnMap getColumnMap(TEntity entity, String... columnNames) {
        return getColumnMap(entity, "", columnNames);
    }

    public static <TEntity> ColumnMap getColumnMap(TEntity entity, String entityName, String... columnNames) {
        MetaDataTable metaDataTable = new MetaDataTable(entity.getClass(), entityName);

        if (columnNames != null && columnNames.length > 0) {
            return getColumnMapByColumnNames(entity, metaDataTable, columnNames);
        }

        ColumnMap mapTable = new ColumnMap(metaDataTable.getMetaDataColumns().size());
        for (String colName : metaDataTable.getMetaDataColumns().keySet()) {
            MetaDataColumn metaColumn = metaDataTable.getMetaDataColumns().get(colName);
            if (metaColumn.getAnnotation().isIgnored()) {
                continue;
            }
            mapTable.put(metaColumn.getName(), metaColumn.getValue(entity));
        }

        return mapTable;
    }

    private static <TEntity> ColumnMap getColumnMapByColumnNames(TEntity entity, MetaDataTable metaDataTable, String... columnNames) {
        ColumnMap mapTable = new ColumnMap(columnNames.length);
        HashMap<String, MetaDataColumn> metaColumns = metaDataTable.getMetaDataColumns();

        for (String columnName : columnNames) {
            String colName = columnName.trim().toLowerCase();
            if (!metaColumns.containsKey(colName)
                    || metaColumns.get(colName).getAnnotation().isIgnored()) {
                continue;
            }

            MetaDataColumn metaColumn = metaColumns.get(colName);
            mapTable.put(metaColumn.getName(), metaColumn.getValue(entity));
        }

        return mapTable;
    }

    private static Object getColumnValue(ResultSet rs, String columnDataTypeName, int columnIndex) throws SQLException {
        columnDataTypeName = columnDataTypeName.toLowerCase();

        if (columnDataTypeName.equals("long")) {
            return rs.getLong(columnIndex);
        }
        if (columnDataTypeName.equals("int") || columnDataTypeName.equals("integer")) {
            return rs.getInt(columnIndex);
        }
        if (columnDataTypeName.equals("short")) {
            return rs.getShort(columnIndex);
        }
        if (columnDataTypeName.equals("byte")) {
            return rs.getByte(columnIndex);
        }
        if (columnDataTypeName.equals("string")) {
            return rs.getString(columnIndex);
        }
        if (columnDataTypeName.equals("float")) {
            return rs.getFloat(columnIndex);
        }
        if (columnDataTypeName.equals("double")) {
            return rs.getDouble(columnIndex);
        }
        if (columnDataTypeName.equals("boolean")) {
            return rs.getBoolean(columnIndex);
        }
        if (columnDataTypeName.equals("date")
                || columnDataTypeName.equals("timestamp")) {
            return rs.getTimestamp(columnIndex);
        }

        return rs.getObject(columnIndex);
    }
}
