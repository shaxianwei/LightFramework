/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lightframework.data.criterion.operands;

/**
 *
 * @author Tom Deng
 */
public class OrConjOperand extends AbstractOperand {

    public OrConjOperand() {
    }

    @Override
    protected String toExpression() {
        return " OR ";
    }
}
