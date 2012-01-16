package lightframework.data.criterion.operands;

import lightframework.data.util.StringExtension;

/**
 *
 * @author Tom Deng <tom.deng@duomi.com>
 */
public class GroupByOperand extends AbstractOperand {

    private String[] _columnNames;

    public GroupByOperand(String... columnNames) {
        this._columnNames = columnNames;
    }

    @Override
    protected String toExpression() {
        if (this._columnNames == null
                || this._columnNames.length <= 0) {
            return "";
        }

        return String.format("GROUP BY %s ", StringExtension.join(this._columnNames));
    }
}
