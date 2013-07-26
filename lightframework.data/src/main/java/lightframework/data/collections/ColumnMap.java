package lightframework.data.collections;

import java.util.HashMap;

/**
 *
 * @author Tom Deng
 */
@SuppressWarnings("serial")
public class ColumnMap extends HashMap<String, Object> {

    public ColumnMap() {
    }

    public ColumnMap(int initialCapacity) {
        super(initialCapacity);
    }

    public ColumnMap(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }
}
