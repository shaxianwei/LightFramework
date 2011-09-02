/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lightframework.data.Criterion.Operands;

import java.util.ArrayList;

/**
 *
 * @author Tom Deng
 */
public abstract class Operand {

    protected ArrayList<Operand> operands;

    public Operand append(Operand operand) {
        if (this.operands == null) {
            this.operands = new ArrayList<Operand>(5);
        }

        this.operands.add(operand);
        return this;
    }

    @Override
    public String toString() {
        if (this.operands == null) {
            return this.toExpression();
        }

        StringBuilder expr = new StringBuilder();
        expr.append(this.toExpression());

        for (Operand operand : this.operands) {
            expr.append(operand.toString());
        }

        return expr.toString();
    }

    protected abstract String toExpression();
}
