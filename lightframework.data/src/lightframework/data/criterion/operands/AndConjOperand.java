/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lightframework.data.Criterion.Operands;

/**
 *
 * @author Tom Deng
 */
public class AndConjOperand extends Operand {

    public AndConjOperand() {
    }

    @Override
    protected String toExpression() {
        return " AND ";
    }
}
