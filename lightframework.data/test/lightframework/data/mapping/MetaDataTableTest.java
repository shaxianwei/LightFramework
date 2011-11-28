/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lightframework.data.mapping;

import org.hamcrest.core.*;
import org.junit.*;
import org.junit.Assert;
import java.lang.reflect.Field;
import lightframework.data.entity.*;
import lightframework.data.annotations.Column;

/**
 *
 * @author Tom Deng <xianrendzw@hotmail.com>
 */
public class MetaDataTableTest {

    protected MetaDataTable _metaDataTable;

    public MetaDataTableTest() {
    }

    @Before
    public void setup() {
        CategoryEntity category = new CategoryEntity();
        this._metaDataTable = new MetaDataTable(category.getClass(), CategoryEntity.ENTITYNAME);
    }

    @After
    public void dispose() {
        this._metaDataTable = null;
    }

    @Test
    public void is_Not_Null_Of_MetaDataTable() {
        Assert.assertThat(this._metaDataTable, IsNull.notNullValue());
    }

    @Test
    public void tableName_Should_Return_Correct_Result() {
        Assert.assertThat(this._metaDataTable.getTableName(), IsEqual.equalTo(CategoryEntity.ENTITYNAME));
    }

    @Test
    public void entityType_Should_Return_Correct_Result() {
        String typeName = this._metaDataTable.getEntityType().getName();
        Assert.assertThat(typeName, IsEqual.equalTo(CategoryEntity.class.getName()));
    }

    @Test
    public void columns_Should_Return_Correct_Result() {
        Field[] fields = this._metaDataTable.getEntityType().getFields();
//        Assert.assertThat(this._metaDataTable.getMetaDataColumns().keySet().toArray()
//                IsEqual.equalTo(fields));
    }
}
