/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lightframework.data;

import java.util.HashMap;

/**
 *
 * @author Tom Deng
 */
@SuppressWarnings("serial")
public class DataFieldMapTable extends HashMap<String, Object> {

    public DataFieldMapTable() {
    }

    public DataFieldMapTable(int initialCapacity) {
        super(initialCapacity);
    }

    public DataFieldMapTable(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }
}
