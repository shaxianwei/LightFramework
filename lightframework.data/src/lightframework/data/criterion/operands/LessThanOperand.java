/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lightframework.data.Criterion.Operands;

/**
 *
 * @author Tom Deng
 */
public class LessThanOperand extends Operand {

    private String columnName;
    private Object columnValue;

    public LessThanOperand(String columnName, Object columnValue) {
        this.columnName = columnName;
        this.columnValue = columnValue;
    }

    @Override
    protected String toExpression() {
        return String.format("%1$s < %2$s ", this.columnName, this.columnValue);
    }
}
