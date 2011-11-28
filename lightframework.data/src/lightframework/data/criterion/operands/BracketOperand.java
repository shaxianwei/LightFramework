/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lightframework.data.criterion.operands;

/**
 *
 * @author Tom Deng
 */
public class BracketOperand extends AbstractOperand {

    private Bracket bracket;

    public BracketOperand(Bracket bracket) {
        this.bracket = bracket;
    }

    @Override
    protected String toExpression() {
        switch (this.bracket) {
            case Left:
                return "(";
            case Rgiht:
                return ")";
            default:
                return "";
        }
    }
}
