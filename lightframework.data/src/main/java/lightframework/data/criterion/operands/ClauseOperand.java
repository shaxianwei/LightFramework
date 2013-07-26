/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lightframework.data.criterion.operands;

/**
 *
 * @author Tom Deng
 */
public class ClauseOperand extends AbstractOperand {

    private SqlClause _sqlClause = SqlClause.None;

    public ClauseOperand(SqlClause sqlClause) {
        this._sqlClause = sqlClause;
    }

    @Override
    public String toString(){
        if(this.operands == null)
            this._sqlClause = SqlClause.None;
        
        return super.toString();
    }
    
    @Override
    protected String toExpression() {
        String strSqlClause = this._sqlClause == SqlClause.None ? "" : this._sqlClause.toString();
        return String.format("%s ", strSqlClause);
    }
}
