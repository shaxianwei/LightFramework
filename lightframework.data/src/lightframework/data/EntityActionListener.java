/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lightframework.data;

import lightframework.data.mapping.MetaDataTable;
import java.sql.ResultSet;

/**
 *
 * @author Tom Deng
 */
public interface EntityActionListener<TEntity> {
   TEntity toEntity(ResultSet rs,MetaDataTable mdt,String... columnNames);
}
