/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lightframework.data.criterion.operands;

/**
 *
 * @author Tom Deng
 */
public class AndConjOperand extends AbstractOperand {

    public AndConjOperand() {
    }

    @Override
    protected String toExpression() {
        return " AND ";
    }
}
