/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lightframework.data.mapping;

import org.hamcrest.core.*;
import org.junit.*;
import org.junit.Assert;
import java.lang.reflect.Field;
import lightframework.data.dto.*;
import lightframework.data.annotations.Column;

/**
 *
 * @author Tom Deng <xianrendzw@hotmail.com>
 */
public class MetaDataColumnTest {

    protected MetaDataColumn _metaDataColumn;

    public MetaDataColumnTest() {
    }

    @Before
    public void setup() {
        CategoryDTO category = new CategoryDTO();
        Field field = null;

        try {
            field = category.getClass().getDeclaredField("name");
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        if (field == null) {
            return;
        }
        Column column = field.getAnnotation(Column.class);
        if (column == null) {
            return;
        }

        this._metaDataColumn = new MetaDataColumn(field, column);
    }

    @After
    public void dispose() {
        this._metaDataColumn = null;
    }

    @Test
    public void is_Not_Null_Of_MemaDataColumn() {
        Assert.assertThat(this._metaDataColumn, IsNull.notNullValue());
    }

    @Test
    public void atribute_Should_Return_Correct_Result() {
        Assert.assertThat(this._metaDataColumn.getField().getName(), IsEqual.equalTo("name"));
    }

    @Test
    public void dataType_Should_Return_Correct_Result() {
        Assert.assertThat(this._metaDataColumn.getDataType().getName(),
                IsEqual.equalTo(String.class.getName()));
    }

    @Test
    public void name_Should_Return_Correct_Result() {
        Assert.assertThat(this._metaDataColumn.getName(), IsEqual.equalTo("Name"));
    }

    @Test
    public void member_Should_Return_Correct_Result() {
        Assert.assertThat(this._metaDataColumn.getField().getName(), IsEqual.equalTo("name"));
    }
}
