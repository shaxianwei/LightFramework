/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lightframework.data.mysql;

/**
 *
 * @author Tom Deng
 */
public class SqlExpression {

    private String _commandText;
    private Object[] _parameters;

    public SqlExpression(String commandText, Object[] parameters) {
        this._commandText = commandText;
        this._parameters = parameters;
    }

    /**
     * @return the _commandText
     */
    public String getCommandText() {
        return _commandText;
    }

    /**
     * @param _commandText the _commandText to set
     */
    public void setCommandText(String CommandText) {
        this._commandText = CommandText;
    }

    /**
     * @return the _parameters
     */
    public Object[] getParameters() {
        return _parameters;
    }

    /**
     * @param _parameters the _parameters to set
     */
    public void setParameters(Object[] Parameters) {
        this._parameters = Parameters;
    }
}
