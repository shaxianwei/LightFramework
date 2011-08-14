/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lightframework.data;

import java.util.List;

/**
 *
 * @author Tom Deng
 */
public class PageData<T> {

    private List<T> _recordSet;
    private int _count;
    private String _currentRecordInterval;

    public List<T> getRecordSet() {
        return this._recordSet;
    }

    public void setRecordSet(List<T> value) {
        this._recordSet = value;
    }

    public int getCount() {
        return this._count;
    }

    public void setCount(int value) {
        this._count = value;
    }

    public String getCurrentRecordInterval() {
        return this._currentRecordInterval;
    }

    public void setCurrentRecordInterval(String value) {
        this._currentRecordInterval = value;
    }
}
