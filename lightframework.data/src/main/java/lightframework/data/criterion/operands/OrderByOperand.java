package lightframework.data.criterion.operands;

import lightframework.data.SortTypeEnum;
import lightframework.data.util.StringExtension;

/**
 *
 * @author Tom Deng <tom.deng@duomi.com>
 */
public class OrderByOperand extends AbstractOperand {

    private SortTypeEnum _sortType;
    private String[] _columnNames;

    public OrderByOperand(SortTypeEnum sortType, String... columnNames) {
        this._sortType = sortType;
        this._columnNames = columnNames;
    }

    @Override
    protected String toExpression() {
        if (this._columnNames == null
                || this._columnNames.length <= 0) {
            return "";
        }

        return String.format("ORDER BY %s %s ", StringExtension.join(",", this._columnNames), this._sortType.toString());
    }
}
