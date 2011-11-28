package lightframework.data;

import java.util.List;

/**
 * 分页数据集合类。
 * @param <T> 通用类型(一般为实体类型)
 * @author Tom Deng
 */
public class PageData<T> {

    private List<T> _recordSet;
    private int _count;
    private String _currentRecordInterval;

    /**
     * 
     * @return 
     */
    public List<T> getRecordSet() {
        return this._recordSet;
    }

    /**
     * 
     * @param value 
     */
    public void setRecordSet(List<T> value) {
        this._recordSet = value;
    }

    /**
     * 
     * @return 
     */
    public int getCount() {
        return this._count;
    }

    /**
     * 
     * @param value 
     */
    public void setCount(int value) {
        this._count = value;
    }

    /**
     * 
     * @return 
     */
    public String getCurrentRecordInterval() {
        return this._currentRecordInterval;
    }

    /**
     * 
     * @param value 
     */
    public void setCurrentRecordInterval(String value) {
        this._currentRecordInterval = value;
    }
}
