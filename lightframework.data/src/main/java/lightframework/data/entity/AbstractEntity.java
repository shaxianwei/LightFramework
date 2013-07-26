package lightframework.data.entity;

import lightframework.data.collections.MyArrayList;
import lightframework.data.collections.Predicate;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 实体的基础类。
 * @author tom deng <tom.deng@codebook.in>
 */
public class AbstractEntity {

    /**
     * 获取指定属性名称的值。
     * @param propertyName 属性名称,区分大小写
     * @return 属性对应的值
     */
    public Object get(String propertyName) {
        try {
            Field filed = this.getClass().getDeclaredField(propertyName);
            if (filed != null) {
                filed.setAccessible(true);
                return filed.get(this);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        return null;
    }

    /**
     * 排除实体对象指定一系列列名称后的集合,如果没有任何参数则返回所有列名称
     * @param columnNames 要排除列名称的集合
     * @return 排除后该对象列名称的集合
     */
    public String[] except(String... columnNames) {

        Field[] fields = this.getClass().getFields();
        if (fields == null || fields.length == 0) {
            return null;
        }

        MyArrayList<Field> fieldList = MyArrayList.wrap(fields);

        //如果没有排除项则返回所有常量值
        if (columnNames == null || columnNames.length == 0) {
            return this.getColumnNames(fieldList, new Predicate<Field>() {

                @Override
                public boolean isMatch(Field element) {
                    return (!element.getName().equalsIgnoreCase("EntityName"));
                }
            });
        }

        final List<String> cols = Arrays.asList(columnNames);
        return this.getColumnNames(fieldList, new Predicate<Field>() {

            @Override
            public boolean isMatch(Field element) {
                return !(element.getName().equalsIgnoreCase("EntityName")
                        || cols.contains(element.getName()));
            }
        });
    }

    private String[] getColumnNames(MyArrayList<Field> fields, Predicate<Field> predicate) {
        ArrayList<String> cols = new ArrayList<String>(fields.size());

        try {
            for (Field field : fields) {
                if (predicate.isMatch(field)) {
                    cols.add(field.get(this).toString());
                }
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        return cols.toArray(new String[]{});
    }
}
