/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lightframework.data.criterion.operands;

/**
 *
 * @author Tom Deng
 */
public class NotEqualOperand extends Operand {

    private String columnName;
    private Object columnValue;

    public NotEqualOperand(String columnName, Object columnValue) {
        this.columnName = columnName;
        this.columnValue = columnValue;
    }

    @Override
    protected String toExpression() {
        return String.format("%1$s <> %1$s ", this.columnName, this.columnValue);
    }
}
