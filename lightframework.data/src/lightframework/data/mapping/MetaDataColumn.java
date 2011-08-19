/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lightframework.data.mapping;

import java.lang.reflect.Field;
import lightframework.data.annotations.Column;

/**
 *
 * @author Tom Deng
 */
public class MetaDataColumn {

    private Field field;
    private Column columnAnnotation;

    public MetaDataColumn(Field field, Column columnAnnotation) {
        this.field = field;
        this.columnAnnotation = columnAnnotation;
        this.field.setAccessible(true);
    }

    public Column getColumnAnnotation() {
        return this.columnAnnotation;
    }

    public Class<?> getDataType() {
        return this.field.getType();
    }

    public String getName() {
        return this.columnAnnotation.name();
    }

    public Field getField() {
        return this.field;
    }
}
