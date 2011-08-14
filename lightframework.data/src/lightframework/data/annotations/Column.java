/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lightframework.data.annotations;

import static java.lang.annotation.ElementType.FIELD;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;

/**
 *
 * @author Tom Deng
 */
@Target({FIELD})
@Retention(RUNTIME)
public @interface Column {

    boolean isIdentity() default false;

    boolean isNullable() default false;

    boolean isPrimaryKey() default false;

    boolean isForeignKey() default false;

    boolean isUnique() default false;

    boolean isOverride() default false;

    int length() default 0;

    String name() default "";

    String formula() default "";

    String ColumnType() default "";

    String uniqueKey() default "";

    String index() default "";

    String sqlType() default "";

    String check() default "";

    String value() default "";
}
