/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lightframework.data.criterion.operands;

/**
 *
 * @author Tom Deng
 */
public class BetweenOperand extends Operand {

    private String columnName;
    private Object lowerValue;
    private Object higherValue;

    public BetweenOperand(String columnName, Object lowerValue, Object higherValue) {
        this.columnName = columnName;
        this.lowerValue = lowerValue;
        this.higherValue = higherValue;
    }

    @Override
    protected String toExpression() {
        return String.format("%1$s BETWEEN %2$s AND %3$s ",
                this.columnName, this.lowerValue, this.higherValue);
    }
}
