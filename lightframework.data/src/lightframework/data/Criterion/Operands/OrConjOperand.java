/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lightframework.data.Criterion.Operands;

/**
 *
 * @author Tom Deng
 */
public class OrConjOperand extends Operand {

    public OrConjOperand() {
    }

    @Override
    protected String toExpression() {
        return " OR ";
    }
}
