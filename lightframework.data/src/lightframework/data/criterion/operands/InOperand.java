/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lightframework.data.criterion.operands;

/**
 *
 * @author Tom Deng
 */
public class InOperand extends Operand {

    private String columnName;
    private Object columnValue;

    public InOperand(String columnName, Object columnValue) {
        this.columnName = columnName;
        this.columnValue = columnValue;
    }

    @Override
    protected String toExpression() {
        return String.format("%1$s IN (%2$s) ", this.columnName, this.columnValue);
    }
}
