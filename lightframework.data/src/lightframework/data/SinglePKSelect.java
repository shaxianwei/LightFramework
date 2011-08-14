/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lightframework.data;

/**
 *
 * @author Tom Deng
 */
public interface SinglePKSelect<T> {

    boolean isExistKey(int keyValue);

    boolean isExistKey(int... keyValues);

    boolean isExistKey(String keyValue);

    boolean isExistKey(String... keyValues);

    boolean isExistKeyIn(String keyValues);

    T select(String keyValue, String... columnNames);

    T select(int keyValue, String... columnNames);

    PageData<T> selectWithPageSizeByIdentity(int pageSize, int pageIndex, String condition, String orderByColumnName, SortTypeEnum sortType, Object[] parameterValues, String... columnNames);

    int getMaxID();

    int getMaxValue(String fieldName, int fromBase, String condition, Object... parameterValues);
}
